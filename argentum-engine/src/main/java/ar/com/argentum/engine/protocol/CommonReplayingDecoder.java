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
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.io.IOException;
import java.util.List;

/**
 * Define the {@link ReplayingDecoder} for handling header
 * <p>
 * It will convert raw frames to {@link ar.com.argentum.api.protocol.Message} frames
 */
public class CommonReplayingDecoder extends ReplayingDecoder<CommonReplayingDecoder.DecoderState> {
    private final MessageLookupService service;
    private DecoderState state;
    private int id;
    private int length;

    /**
     * Default constructor for {@link CommonReplayingDecoder}
     *
     * @param service the service of the decoder
     */
    public CommonReplayingDecoder(MessageLookupService service) {
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
                id = in.readShort();
                checkpoint(DecoderState.READ_LENGTH);
                break;
            case READ_LENGTH:
                length = in.readShort();
                checkpoint(DecoderState.READ_CONTENT);
                break;
            case READ_CONTENT:
                decodeMessage(id, in.readBytes(length), out);
                checkpoint(DecoderState.READ_ID);
        }
    }

    /**
     * Decodes a {@link ar.com.argentum.api.protocol.Message} given its byte representation
     *
     * @param id    the unique identifier of the message
     * @param input the stream that stores the message's bytes
     * @param out   the storage of the messages
     * @throws IOException
     */
    protected void decodeMessage(int id, ByteBuf input, List<Object> out) throws IOException {
        MessageCodec<? extends Message> codec = service.getCodec(id);

        if (codec == null) {
            throw new IOException("Unknown operation code: " + id);
        }
        out.add(codec.decode(input));
    }

    /**
     * All possible states of {@link CommonReplayingDecoder}
     */
    protected enum DecoderState {
        READ_ID,
        READ_LENGTH,
        READ_CONTENT
    }
}
