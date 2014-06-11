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
package ar.com.argentum.engine.protocol;

import ar.com.argentum.api.protocol.Message;
import ar.com.argentum.api.protocol.MessageCodec;
import ar.com.argentum.api.protocol.MessageLookupService;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.io.IOException;
import java.util.List;

/**
 * Define the {@link MessageToMessageEncoder} for turning {@link ar.com.argentum.api.protocol.Message} into bytes
 */
public class CommonMessageToMessageEncoder extends MessageToMessageEncoder {
    private final MessageLookupService service;


    /**
     * Default constructor for {@link CommonMessageToMessageEncoder}
     *
     * @param service the service of the decoder
     */
    public CommonMessageToMessageEncoder(MessageLookupService service) throws IOException {
        this.service = service;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, List out) throws Exception {
        if (msg instanceof Message) {
            encodeMessage((Message) msg, out);
        }
    }

    /**
     * Encodes a {@link Message} into a stream
     *
     * @param message the message to encode to the buffer
     * @param out     the storage of the buffer
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    protected void encodeMessage(Message message, List out) throws IOException {
        final MessageCodec<Message> codec = (MessageCodec<Message>) service.getCodec(message.getClass());
        if (codec == null) {
            throw new IOException("Unknown operation class: " + message.getClass());
        }
        final ByteBuf body = codec.encode(message);
        final ByteBuf header = Unpooled.buffer(3).writeByte(codec.getOpcode()).writeShort(body.capacity());
        out.add(Unpooled.wrappedBuffer(header, body));
    }
}
