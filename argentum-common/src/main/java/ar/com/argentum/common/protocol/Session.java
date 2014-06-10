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
package ar.com.argentum.common.protocol;

import java.net.InetSocketAddress;

/**
 * Define a single session of the network platform
 */
public interface Session {
    /**
     * Gets a unique id for this session
     *
     * @return a string represented as unique
     */
    public String getUniqueId();

    /**
     * Gets the {@link InetSocketAddress} of the session
     *
     * @return the remote address of this session
     */
    public InetSocketAddress getAddress();

    /**
     * Gets the current {@link SessionState} of the session
     *
     * @return the current state of this session
     */
    public SessionState getState();

    /**
     * Sets the current {@link SessionState} of the session
     *
     * @param state the new state of the session
     */
    public void setState(SessionState state);

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
     * Disconnect the session as a Kick
     *
     * @param reason the reason for disconnection
     * @return whether th player was disconnected
     */
    public boolean disconnect(String reason);

    /**
     * Gets if the session is open and connected
     *
     * @return true if the session is active
     */
    public boolean isActive();

    /**
     * Gets if the session was open, and now disconnected
     *
     * @return true if the session was disconnected
     */
    public boolean isDisconnected();
}
