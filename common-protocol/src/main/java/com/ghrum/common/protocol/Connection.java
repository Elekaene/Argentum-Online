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

import java.net.InetSocketAddress;


/**
 * Define a single connection of the protocol
 */
public interface Connection {
    /**
     * Gets a unique id for this connection
     *
     * @return a string represented as unique
     */
    public String getUniqueId();

    /**
     * Gets the {@link InetSocketAddress} of the connection
     *
     * @return the remote address of this connection
     */
    public InetSocketAddress getAddress();

    /**
     * Gets the current {@link State} of the connection
     *
     * @return the current state of this connection
     */
    public State getState();

    /**
     * Sets the current {@link State} of the connection
     *
     * @param state the new state of the connection
     */
    public void setState(State state);

    /**
     * Gets the attached object of the connection
     *
     * @return the attached object or null
     */
    public Object getAttachment();

    /**
     * Attach an object to the connection
     *
     * @param attachment the object to attach to the connection
     */
    public void setAttachment(Object attachment);

    /**
     * Sends a message across the network
     *
     * @param message the message to send to the network
     */
    public void send(Message message);

    /**
     * Sends a message across the network
     *
     * @param isUrgent true if the message is urgent
     * @param message  the message to send to the network
     */
    public void send(boolean isUrgent, Message message);

    /**
     * Sends any amount of messages across the network
     *
     * @param messages the messages to send to the network
     */
    public void sendAll(Message... messages);

    /**
     * Sends any amount of messages across the network
     *
     * @param isUrgent true if the messages are urgent
     * @param messages the messages to send to the network
     */
    public void sendAll(boolean isUrgent, Message... messages);

    /**
     * Disconnect the connection as a Kick
     *
     * @param reason the reason for disconnection
     * @return whether the connection was disconnected
     */
    public boolean disconnect(String reason);

    /**
     * Gets if the connection is open and connected
     *
     * @return true if the connection is active
     */
    public boolean isActive();

    /**
     * Gets if the connection was open, and now disconnected
     *
     * @return true if the connection was disconnected
     */
    public boolean isDisconnected();

    /**
     * Gets the uncaught exception handler.
     *
     * @return exception handler
     */
    public UncaughtExceptionHandler getUncaughtExceptionHandler();

    /**
     * Sets the uncaught exception handler to be used for this connection. Null values are not permitted.
     *
     * @param handler the new handler of the connection
     */
    public void setUncaughtExceptionHandler(UncaughtExceptionHandler handler);

    /**
     * Passes a message to a session for processing
     *
     * @param message message to be processed
     */
    public <T extends Message> void messageReceived(T message);

    /**
     * Define all possible states of {@link Connection}
     */
    public enum State {
        /**
         * In the exchange handshake state, the server is waiting for the client to send its initial handshake packet.
         */
        EXCHANGE_HANDSHAKE,

        /**
         * In the exchange identification state, the server is waiting for the client to send its identification packet.
         */
        EXCHANGE_IDENTIFICATION,

        /**
         * In the game state the session has an associated player.
         */
        GAME
    }

    /**
     * Define an exception handler for any exception that occurs handling a message
     */
    public interface UncaughtExceptionHandler {
        /**
         * Called when an exception occurs during message handling
         *
         * @param message   the message handler threw an exception on
         * @param exception the exception triggered
         */
        public void uncaughtException(Message message, Exception exception);
    }
}
