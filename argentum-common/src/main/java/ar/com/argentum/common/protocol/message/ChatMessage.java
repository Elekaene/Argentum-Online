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
package ar.com.argentum.common.protocol.message;

import ar.com.argentum.common.protocol.Message;

/**
 * Define the common chat {@link Message}
 */
public final class ChatMessage extends Message {
    /**
     * The unique identifier of the message
     */
    public final static int ID = 1;

    /**
     * The data of the message (immutable)
     */
    private final String message;

    /**
     * Default constructor for {@link ChatMessage}
     */
    public ChatMessage(String message) {
        super(true, true);
        this.message = message;
    }

    /**
     * Retrieve the message of the chat
     *
     * @return the message of a player
     */
    public String getMessage() {
        return message;
    }
}
