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

import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;

/**
 * Define a common {@link ChannelInitializer}
 */
public class ProtocolChannelInitializer extends ChannelInitializer<SocketChannel> {
    private final MessageLookupService service;
    private final SimpleChannelInboundHandler<Message> handler;

    /**
     * Default constructor for {@link ProtocolChannelInitializer}
     *
     * @param service the service of the channel
     * @param handler the handler of the channel
     */
    public ProtocolChannelInitializer(MessageLookupService service, SimpleChannelInboundHandler<Message> handler) {
        this.service = service;
        this.handler = handler;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ProtocolMessageToMessageEncoder encoder = new ProtocolMessageToMessageEncoder(service);
        ProtocolReplayingDecoder decoder = new ProtocolReplayingDecoder(service);
        ch.pipeline().addLast(decoder, encoder, handler);
    }
}
