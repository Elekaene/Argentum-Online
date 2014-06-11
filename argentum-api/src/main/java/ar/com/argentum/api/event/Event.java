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
 * Define an abstract event
 */
public abstract class Event {
    private boolean isCancelled = false;

    /**
     * Gets if the event is cancelled
     *
     * @return true if the event is cancelled
     */
    public final boolean isCancelled() {
        return isCancelled;
    }

    /**
     * Sets the cancellation state of the event
     *
     * @param isCancelled true if the should be cancelled
     */
    public final void setCancelled(boolean isCancelled) {
        this.isCancelled = isCancelled;
    }
}
