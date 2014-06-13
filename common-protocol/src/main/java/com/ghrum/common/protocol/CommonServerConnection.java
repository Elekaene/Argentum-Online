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

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Define the server implementation for {@link Connection}
 */
public final class CommonServerConnection extends CommonConnection {
    /**
     * A list of the sessions.
     */
    private final ConcurrentMap<CommonConnection, Boolean> registry = new ConcurrentHashMap<>();

    /**
     * Default constructor for {@link CommonClientConnection}
     *
     * @param service the service of the session
     * @param channel the channel attached to this session
     */
    public CommonServerConnection(MessageLookupService service, Channel channel) {
        super(service, channel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean disconnect(String reason) {
        return close(false, reason);
    }

    /**
     * Close the primary channel or all connected channels
     *
     * @param closePrimary true if we should close primary channel as-well
     * @param reason       the reason for disconnection
     * @return whether the connection was disconnected
     */
    public boolean close(boolean closePrimary, String reason) {
        return false; // TODO
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void pulse() {
        super.pulse();
        registry.keySet().forEach(CommonConnection::pulse);
    }

    /**
     * Adds a new connection to the pool
     *
     * @param connection the connection to add to the pool
     */
    public void add(CommonConnection connection) {
        registry.put(connection, true);
    }

    /**
     * Removes a connection from the pool
     *
     * @param connection the connection to remove from the pool
     */
    public void remove(CommonConnection connection) {
        registry.remove(connection);
    }
}
