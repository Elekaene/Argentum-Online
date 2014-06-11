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
package ar.com.argentum.engine.protocol;

import ar.com.argentum.api.plugin.Platform;
import ar.com.argentum.api.protocol.*;
import ar.com.argentum.api.world.Player;
import io.netty.channel.Channel;

import java.net.InetSocketAddress;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Define the common implementation for {@link Session}
 */
public abstract class CommonSession implements Session {
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
     * The current platform of this session
     */
    protected final Platform platform;
    /**
     * The current state.
     */
    protected SessionState state = SessionState.EXCHANGE_HANDSHAKE;
    /**
     * Stores if this Session has had disconnect called
     */
    protected boolean isDisconnected = false;
    /**
     * The player attached to the session
     */
    protected Player player = null;

    /**
     * Default constructor for {@link CommonSession}
     *
     * @param platform the platform of this session
     * @param service  the service of the session
     * @param channel  the channel attached to this session
     */
    public CommonSession(Platform platform, MessageLookupService service, Channel channel) {
        this.id = Long.toString(new Random().nextLong(), 16).trim();
        this.platform = platform;
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
    public SessionState getState() {
        return state;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setState(SessionState state) {
        this.state = state;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player getPlayer() {
        return player;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPlayer(Player player) {
        this.player = player;
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
        } catch (Throwable ex) {
            disconnect("Internal error: " + ex.getMessage());
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
     * Pulse the session
     */
    public void pulse() {
        Message message = null;

        // Pulse every delivered message and cache them
        // on the buffer. After every message was serialized to the
        // buffer, flush it
        while ((message = sendQueue.poll()) != null) {
            channel.write(message);
        }
        channel.flush();

        // Pulse every received message
        messageQueue.forEach(this::handleMessage);
    }

    /**
     * Handle command {@link MessageHandler}
     *
     * @param message the message to handle
     * @param <T>     the type of the message
     */
    @SuppressWarnings("unchecked")
    public <T extends Message> void handleMessage(T message) {
        MessageHandler<Message> handler = (MessageHandler<Message>) service.getHandler(message.getClass());
        if (handler != null) {
            try {
                handler.handle(platform, this, message);
            } catch (Throwable ex) {
                disconnect("Internal error: " + ex.getMessage());
            }
        }
    }

    /**
     * Called when the session receives a message
     *
     * @param message the message received
     * @param <T>     the type of the message
     */
    public <T extends Message> void handleMessageReceive(T message) {
        if (message.isAsync()) {
            handleMessage(message);
        } else {
            messageQueue.add(message);
        }
    }
}
