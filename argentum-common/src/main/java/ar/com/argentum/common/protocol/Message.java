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

/**
 * Represent the data of a packet to be transported though the networking system.
 * <p/>
 * There are a few rules that messages should follow:
 * <ul>
 * <li>All message fields should be immutable</li>
 * <li>All fields in a message should be protocol-primitive</li>
 * </ul>
 */
public abstract class Message {
    private final boolean isPlayerRequired;
    private final boolean isAsync;

    /**
     * Default constructor for {@link Message}
     *
     * @param isPlayerRequired true if the message needs an entity to work
     * @param isAsync          true if the message doesn't run on the main thread
     */
    public Message(boolean isPlayerRequired, boolean isAsync) {
        this.isPlayerRequired = isPlayerRequired;
        this.isAsync = isAsync;
    }

    /**
     * Gets if the message is handled asynchronized
     *
     * @return true if the message is not handled on the main thread
     */
    public final boolean isAsync() {
        return isAsync;
    }

    /**
     * Gets if the message need a player to handle
     *
     * @return true if the message depends from an entity
     */
    public final boolean isPlayerRequired() {
        return isPlayerRequired;
    }
}
