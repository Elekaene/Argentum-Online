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

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.io.IOException;
import java.util.List;

/**
 * Define the {@link MessageToMessageEncoder} for turning {@link Message} into bytes
 */
public class ProtocolMessageToMessageEncoder extends MessageToMessageEncoder {
    private final MessageLookupService service;

    /**
     * Default constructor for {@link ProtocolMessageToMessageEncoder}
     *
     * @param service the service of the decoder
     */
    protected ProtocolMessageToMessageEncoder(MessageLookupService service) throws IOException {
        this.service = service;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, List out) throws Exception {
        out.add(service.encode((Message) msg));
    }
}
