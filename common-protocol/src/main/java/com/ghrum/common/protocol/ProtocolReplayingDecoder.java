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
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * Define the {@link ReplayingDecoder} for handling header
 * <p>
 * It will convert raw frames to {@link Message} frames
 */
public class ProtocolReplayingDecoder extends ReplayingDecoder<ProtocolReplayingDecoder.DecoderState> {
    private final MessageLookupService service;
    private DecoderState state;
    private int id;
    private int length;

    /**
     * Default constructor for {@link ProtocolReplayingDecoder}
     *
     * @param service the service of the decoder
     */
    protected ProtocolReplayingDecoder(MessageLookupService service) {
        super(DecoderState.READ_ID);
        this.service = service;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        switch (state) {
            case READ_ID:
                id = in.readUnsignedByte();
                checkpoint(DecoderState.READ_LENGTH);
                break;
            case READ_LENGTH:
                length = in.readUnsignedShort();
                checkpoint(DecoderState.READ_CONTENT);
                break;
            case READ_CONTENT:
                out.add(service.decode(id, in.readBytes(length)));
                checkpoint(DecoderState.READ_ID);
        }
    }

    /**
     * All possible states of {@link ProtocolReplayingDecoder}
     */
    protected enum DecoderState {
        READ_ID,
        READ_LENGTH,
        READ_CONTENT
    }
}
