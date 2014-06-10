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
package ar.com.argentum.common.protocol;

/**
 * Define all possible states of {@link Session}
 */
public enum SessionState {
    /**
     * In the exchange handshake state, the server is waiting for the client to send its initial handshake packet.
     */
    EXCHANGE_HANDSHAKE,

    /**
     * In the exchange identification state, the server is waiting for the client to send its identification packet.
     */
    EXCHANGE_IDENTIFICATION,

    /**
     * This state is when a critical message has been sent that must be waited for.
     */
    WAITING,

    /**
     * In the game state the session has an associated player.
     */
    GAME
}
