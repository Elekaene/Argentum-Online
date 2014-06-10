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
package ar.com.argentum.common.protocol.codec;

import ar.com.argentum.common.protocol.MessageCodec;
import ar.com.argentum.common.protocol.message.ChatMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

import java.io.IOException;

/**
 * Define the {@link MessageCodec} for {@link ChatMessage}
 */
public class ChatMessageCodec extends MessageCodec<ChatMessage> {
    /**
     * Default constructor for {@link ChatMessageCodec}
     */
    public ChatMessageCodec() {
        super(ChatMessage.class, ChatMessage.ID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ByteBuf encode(ChatMessage message) throws IOException {
        return Unpooled.copiedBuffer(message.getMessage(), CharsetUtil.UTF_8);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChatMessage decode(ByteBuf buffer) throws IOException {
        return new ChatMessage(buffer.toString(CharsetUtil.UTF_8));
    }
}
