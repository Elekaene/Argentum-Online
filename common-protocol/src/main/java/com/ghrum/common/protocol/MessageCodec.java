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

import io.netty.buffer.ByteBuf;

import java.io.IOException;

/**
 * Represent the codec for a type of {@link Message}
 */
public abstract class MessageCodec<T extends Message> {
    private final Class<T> clazz;
    private final int opcode;
    private final Connection.State state;

    /**
     * Default constructor for {@link MessageCodec}
     *
     * @param clazz  the class type for the message
     * @param opcode the unique identifier of the message
     * @param state  the state of the message
     */
    public MessageCodec(Class<T> clazz, int opcode, Connection.State state) {
        this.clazz = clazz;
        this.opcode = opcode;
        this.state = state;
    }

    /**
     * Gets the class type of the message
     *
     * @return the class type of the message
     */
    public final Class<T> getType() {
        return clazz;
    }

    /**
     * Gets the unique identifier of the message
     *
     * @return the unique identifier of the message
     */
    public final int getOpcode() {
        return opcode;
    }

    /**
     * Gets the required state of the message
     *
     * @return the required state of the message
     */
    public final Connection.State getState() {
        return state;
    }

    /**
     * Encode the message
     *
     * @param message the message to encode
     * @return a buffer containing the message encoded
     * @throws IOException
     */
    public ByteBuf encode(T message) throws IOException {
        return null;
    }

    /**
     * Decodes the message
     *
     * @param buffer the buffer where the message's bytes are stored
     * @return the message decoded from the buffer
     * @throws IOException
     */
    public T decode(ByteBuf buffer) throws IOException {
        return null;
    }
}
