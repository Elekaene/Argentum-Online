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
package ar.com.argentum.api.protocol;

import ar.com.argentum.api.plugin.Platform;

/**
 * Define the executor handler for a single {@link Message} type
 */
public abstract class MessageHandler<T extends Message> {
    /**
     * Handles a message
     *
     * @param platform the current platform
     * @param session  the session that delivers the message
     * @param message  the message that was received
     */
    public void handle(Platform platform, Session session, T message) {
        switch (platform) {
            case CLIENT:
                handleClient(session, message);
                break;
            case SERVER:
                handleServer(session, message);
                break;
            default:
                throw new UnsupportedOperationException("Trying to handle a message with an invalid handler");
        }
    }

    /**
     * Handles a message as the client
     *
     * @param session the session that delivers the message
     * @param message the message that was received
     */
    public void handleClient(Session session, T message) {
        throw new UnsupportedOperationException("Trying to handle a message with an invalid handler");
    }

    /**
     * Handles a message as the server
     *
     * @param session the session that delivers the message
     * @param message the message that was received
     */
    public void handleServer(Session session, T message) {
        throw new UnsupportedOperationException("Trying to handle a message with an invalid handler");
    }
}
