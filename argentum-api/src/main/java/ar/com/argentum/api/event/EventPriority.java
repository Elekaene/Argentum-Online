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
package ar.com.argentum.api.event;

/**
 * Define all possible priorities of {@link Event}
 */
public enum EventPriority {
    /**
     * LOWEST is executed before all
     */
    LOWEST,
    /**
     * LOW is executed after {@link #LOWEST}
     */
    LOW,
    /**
     * NORMAL is executed after {@link #LOW}
     */
    NORMAL,
    /**
     * HIGH is executed after {@link #NORMAL}
     */
    HIGH,
    /**
     * HIGHEST is executed after {@link #HIGH}
     */
    HIGHEST,
    /**
     * MONITOR is executed after all
     */
    MONITOR
}
