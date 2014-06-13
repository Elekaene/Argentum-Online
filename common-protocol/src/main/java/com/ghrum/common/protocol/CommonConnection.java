/**
 * This file is part of Argentum Online.
 *
 * Copyright (c) 2014 Argentum Online <https://github.com/orgs/Argentum-Online/members>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ghrum.common.protocol;

import io.netty.channel.Channel;

import java.net.InetSocketAddress;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.BiConsumer;

/**
 * Define the common implementation for {@link Connection}
 */
public abstract class CommonConnection implements Connection {
    /**
     * The unique identifier of the session
     */
    protected final String id;
    /**
     * The channel associated to this session
     */
    protected final Channel channel;
    /**
     * The service for the session
     */
    protected final MessageLookupService service;
    /**
     * A queue of incoming and unprocessed messages
     */
    protected final Queue<Message> messageQueue = new ArrayDeque<Message>();
    /**
     * A queue of outgoing messages that will be sent after the client finishes identification
     */
    protected final Queue<Message> sendQueue = new ConcurrentLinkedQueue<Message>();
    /**
     * The current state.
     */
    protected State state = State.EXCHANGE_HANDSHAKE;
    /**
     * Stores if this Session has had disconnect called
     */
    protected boolean isDisconnected = false;
    /**
     * The object attached to the session
     */
    protected Object attachment = null;
    /**
     * The Uncaught exception handler of this connection
     */
    protected UncaughtExceptionHandler uncaughtExceptionHandler;

    /**
     * Default constructor for {@link CommonConnection}
     *
     * @param service the service of the session
     * @param channel the channel attached to this session
     */
    public CommonConnection(MessageLookupService service, Channel channel) {
        this.id = Long.toString(new Random().nextLong(), 16).trim();
        this.channel = channel;
        this.service = service;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUniqueId() {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InetSocketAddress getAddress() {
        return (InetSocketAddress) channel.remoteAddress();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public State getState() {
        return state;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setState(State state) {
        this.state = state;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getAttachment() {
        return attachment;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAttachment(Object attachment) {
        if (this.attachment != null) {
            throw new IllegalStateException("Cannot set the attachment more than once");
        }
        this.attachment = attachment;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void send(Message message) {
        send(false, message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void send(boolean isUrgent, Message message) {
        if (message == null) {
            throw new IllegalArgumentException("Trying to send a null message");
        }
        try {
            if (isUrgent && channel.isActive()) {
                channel.writeAndFlush(message);
            } else {
                sendQueue.add(message);
            }
        } catch (Exception ex) {
            uncaughtExceptionHandler.uncaughtException(message, ex);
            disconnect("An exception has raised: " + ex.getLocalizedMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendAll(Message... messages) {
        sendAll(false, messages);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendAll(boolean isUrgent, Message... messages) {
        for (Message message : messages) {
            send(isUrgent, message);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isActive() {
        return channel.isActive();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDisconnected() {
        return isDisconnected;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UncaughtExceptionHandler getUncaughtExceptionHandler() {
        return uncaughtExceptionHandler;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setUncaughtExceptionHandler(UncaughtExceptionHandler handler) {
        uncaughtExceptionHandler = handler;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Message> void messageReceived(T message) {
        messageQueue.add(message);
    }

    /**
     * Pulse the session
     */
    public void pulse() {
        Message message = null;

        // Pulse every delivered message and cache them
        // on the buffer. After every message was serialized to the
        // buffer, flush it
        sendQueue.forEach(channel::write);
        sendQueue.clear();
        channel.flush();

        // Pulse every received message and handle to its
        // handler
        messageQueue.forEach(this::handleMessage);
        messageQueue.clear();
    }

    /**
     * Handle a message
     *
     * @param message the message to handle
     * @param <T>     the type of the message
     */
    @SuppressWarnings("unchecked")
    public <T extends Message> void handleMessage(T message) {
        BiConsumer<Connection, T> handler = (BiConsumer<Connection, T>) service.getHandler(message.getClass());
        if (handler != null) {
            try {
                handler.accept(this, message);
            } catch (Exception ex) {
                uncaughtExceptionHandler.uncaughtException(message, ex);
                disconnect("An exception has raised: " + ex.getLocalizedMessage());
            }
        }
    }
}
