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

import com.sun.xml.internal.ws.api.handler.MessageHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * Define the service for {@link MessageCodec} and {@link Message}
 */
public final class MessageLookupService {
    private final Map<Class<? extends Message>, BiConsumer<Connection, ?>> handlerTable;
    private final Map<Class<? extends Message>, MessageCodec<?>> classTable;
    private final MessageCodec<?>[] opcodeTable;

    /**
     * Default constructor for {@link MessageLookupService}
     *
     * @param size the max number of codec allowed to register
     */
    public MessageLookupService(int size) {
        this.classTable = new HashMap<>(size);
        this.handlerTable = new HashMap<>(size);
        this.opcodeTable = new MessageCodec<?>[size];
    }

    /**
     * Bind a {@link MessageCodec} to the service table
     *
     * @param clazz the class that represent a codec
     * @param <T>   the class type of the message
     * @param <J>   the class type of the codec
     * @return the {@link MessageCodec} constructed and attached to the service
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    protected <T extends Message, J extends MessageCodec<T>> J bind(Class<J> clazz) throws InstantiationException,
            IllegalAccessException, InvocationTargetException {
        Constructor<J> constructor;
        try {
            constructor = clazz.getConstructor();
        } catch (NoSuchMethodException e) {
            throw (InstantiationException) new InstantiationException().initCause(e);
        }
        J codec = constructor.newInstance();

        if (opcodeTable[codec.getOpcode()] != null) {
            throw new IllegalStateException("Trying to bind a codec in a position where is already taken");
        }
        register(codec);
        return codec;
    }

    /**
     * Bind a {@link MessageCodec} to the service table
     *
     * @param codec the instated codec
     * @param <T>   the class type of the message
     * @param <J>   the class type of the codec
     */
    protected <T extends Message, J extends MessageCodec<T>> void register(J codec) {
        opcodeTable[codec.getOpcode()] = codec;
        classTable.put(codec.getType(), codec);
    }

    /**
     * Bind a {@link MessageHandler} to the service table
     *
     * @param clazz   the class type of the message
     * @param handler the handler for the given message
     */
    protected <T extends Message> void register(Class<T> clazz, BiConsumer<Connection, T> handler) {
        handlerTable.put(clazz, handler);
    }

    /**
     * Gets all {@link MessageCodec} of the service
     *
     * @return a collection containing all codec
     */
    public Collection<MessageCodec<?>> getCodecs() {
        return Collections.unmodifiableCollection(classTable.values());
    }

    /**
     * Gets a {@link MessageHandler} from the service given the {@link Message}'s class
     *
     * @param clazz the message class
     * @param <T>   the class type of the message
     * @return the handler if the class is valid, null otherwise
     */
    @SuppressWarnings("unchecked")
    public <T extends Message> BiConsumer<Connection, T> getHandler(Class<T> clazz) {
        return (BiConsumer<Connection, T>) handlerTable.get(clazz);
    }

    /**
     * Encodes a {@link Message} into a stream
     *
     * @param message the message to encode to the buffer
     * @return a wrapped buffer that contains the header and the body of the message
     * @throws java.io.IOException
     */
    @SuppressWarnings("unchecked")
    protected <T extends Message> ByteBuf encode(T message) throws IOException {
        final MessageCodec<Message> codec = (MessageCodec<Message>) getCodec(message.getClass());
        if (codec == null) {
            throw new IOException("Unknown operation class: " + message.getClass());
        }
        final ByteBuf body = codec.encode(message);
        final ByteBuf header = Unpooled.buffer(3).writeByte(codec.getOpcode()).writeShort(body.capacity());
        return Unpooled.wrappedBuffer(header, body);
    }

    /**
     * Gets a {@link MessageCodec} from the service given the {@link Message}'s class
     *
     * @param clazz the message class
     * @param <T>   the class type of the message
     * @return the codec if the class is valid, null otherwise
     */
    @SuppressWarnings("unchecked")
    public <T extends Message> MessageCodec<T> getCodec(Class<T> clazz) {
        return (MessageCodec<T>) classTable.get(clazz);
    }

    /**
     * Decodes a {@link Message} given its byte representation
     *
     * @param id    the unique identifier of the message
     * @param input the stream that stores the message's bytes
     * @return the object instated
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    protected <T extends Message> T decode(int id, ByteBuf input) throws IOException {
        MessageCodec<? extends Message> codec = getCodec(id);
        if (codec == null) {
            throw new IOException("Unknown operation code: " + id);
        }
        return (T) codec.decode(input);
    }

    /**
     * Gets a {@link MessageCodec} from the service given its opcode
     *
     * @param opcode the unique opcode of the message
     * @return the codec if the opcode is valid, null otherwise
     */
    public MessageCodec<?> getCodec(int opcode) {
        if (opcode < 0 || opcode > opcodeTable.length) {
            throw new IllegalArgumentException("Opcode " + opcode + " is out of bounds");
        }
        return opcodeTable[opcode];
    }
}
