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
package ar.com.argentum.server.protocol;

import ar.com.argentum.common.protocol.Message;
import ar.com.argentum.common.protocol.Session;
import ar.com.argentum.common.protocol.SessionState;

import java.net.InetSocketAddress;

/**
 * Define the server implementation for {@link Session}
 */
public class ServerSession implements Session {
    /**
     * {@inheritDoc}
     */
    @Override
    public String getUniqueId() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InetSocketAddress getAddress() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionState getState() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setState(SessionState state) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void send(Message message) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void send(boolean isUrgent, Message message) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendAll(Message... messages) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendAll(boolean isUrgent, Message... messages) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean disconnect(String reason) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isActive() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDisconnected() {
        return false;
    }
}
