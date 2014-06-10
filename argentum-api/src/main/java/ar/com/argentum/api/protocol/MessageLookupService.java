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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Define the service for {@link MessageCodec} and {@link Message}
 */
public final class MessageLookupService {
    private final Map<Class<? extends Message>, MessageHandler<?>> handlerTable;
    private final Map<Class<? extends Message>, MessageCodec<?>> classTable;
    private final MessageCodec<?>[] opcodeTable;

    /**
     * Default constructor for {@link MessageLookupService}
     *
     * @param size the max number of codec allowed to register
     */
    protected MessageLookupService(int size) {
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
     * Bind a {@link MessageHandler} to the service table
     *
     * @param clazz        the class that represent a message
     * @param handlerClass the class that represent a handler
     * @param <T>          the class type of the message
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    protected <T extends Message> void bind(Class<T> clazz, Class<? extends MessageHandler<T>> handlerClass) throws
            InstantiationException, IllegalAccessException {
        MessageHandler<T> handler = handlerClass.newInstance();
        handlerTable.put(clazz, handler);
    }

    /**
     * Bind a {@link MessageHandler} to the service table
     *
     * @param clazz   the class type of the message
     * @param handler the handler for the given message
     */
    protected <T extends Message> void register(Class<T> clazz, MessageHandler<T> handler) {
        handlerTable.put(clazz, handler);
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
    public <T extends Message> MessageHandler<T> getHandler(Class<T> clazz) {
        return (MessageHandler<T>) handlerTable.get(clazz);
    }
}
