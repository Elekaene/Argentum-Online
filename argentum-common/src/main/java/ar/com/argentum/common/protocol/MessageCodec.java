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

import io.netty.buffer.ByteBuf;

import java.io.IOException;

/**
 * Represent the codec for a type of {@link Message}
 */
public abstract class MessageCodec<T extends Message> {
    private final Class<T> clazz;
    private final int opcode;

    /**
     * Default constructor for {@link MessageCodec}
     *
     * @param clazz  the class type for the message
     * @param opcode the unique identifier of the message
     */
    public MessageCodec(Class<T> clazz, int opcode) {
        this.clazz = clazz;
        this.opcode = opcode;
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
     * Encode the message
     *
     * @param isClient true if the request is from the client
     * @param message  the message to encode
     * @return a buffer containing the message encoded
     * @throws IOException
     */
    public ByteBuf encode(boolean isClient, T message) throws IOException {
        return isClient ? encodeToServer(message) : encodeToClient(message);
    }

    /**
     * Encodes the message for the client
     *
     * @param message the message to encode
     * @return a buffer containing the message encoded
     * @throws IOException
     */
    public ByteBuf encodeToClient(T message) throws IOException {
        return null;
    }

    /**
     * Encodes the message for the server
     *
     * @param message the message to encode
     * @return a buffer containing the message encoded
     * @throws IOException
     */
    public ByteBuf encodeToServer(T message) throws IOException {
        return null;
    }

    /**
     * Decodes the message
     *
     * @param isClient true if the request is from the client
     * @param buffer   the buffer where the message's bytes are stored
     * @return the message decoded from the buffer
     * @throws IOException
     */
    public T decode(boolean isClient, ByteBuf buffer) throws IOException {
        return isClient ? decodeToClient(buffer) : decodeServer(buffer);
    }

    /**
     * Decodes the message for the client
     *
     * @param buffer the buffer where the message's bytes are stored
     * @return the message decoded from the buffer
     * @throws IOException
     */
    public T decodeToClient(ByteBuf buffer) throws IOException {
        return null;
    }

    /**
     * Decodes the message for the server
     *
     * @param buffer the buffer where the message's bytes are stored
     * @return the message decoded from the buffer
     * @throws IOException
     */
    public T decodeServer(ByteBuf buffer) throws IOException {
        return null;
    }
}
