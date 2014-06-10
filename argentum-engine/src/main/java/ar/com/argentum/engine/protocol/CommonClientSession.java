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

import ar.com.argentum.api.Platform;
import ar.com.argentum.api.protocol.MessageLookupService;
import io.netty.channel.Channel;

/**
 * Define {@link CommonSession} for client platform
 */
public class CommonClientSession extends CommonSession {
    /**
     * Default constructor for {@link CommonClientSession}
     *
     * @param channel the channel attached to this session
     * @param service the service of the session
     */
    public CommonClientSession(Channel channel, MessageLookupService service) {
        super(Platform.CLIENT, service, channel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean disconnect(String reason) {
        return false;
    }
}
