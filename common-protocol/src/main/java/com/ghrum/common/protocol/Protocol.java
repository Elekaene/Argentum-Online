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

/**
 * Define the configuration of the protocol
 */
public interface Protocol {
    /**
     * Gets the {@link MessageLookupService} of the protocol
     *
     * @return the message service of the protocol
     */
    public MessageLookupService getMessageService();

    /**
     * Gets the {@link Message} for kicking a player or connection
     *
     * @param message the message of the kick
     * @return the message constructed by the protocol
     */
    public Message getKickMessage(String message);
}
