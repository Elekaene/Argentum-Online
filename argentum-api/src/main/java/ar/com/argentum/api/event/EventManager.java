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

import ar.com.argentum.api.plugin.Plugin;

import java.util.function.Consumer;

/**
 * Define the manager of {@link Event}
 */
public interface EventManager {
    /**
     * Executes an event synchronously
     *
     * @param event the event to execute
     * @param <T>   the type of the event
     */
    public <T extends Event> void callEvent(T event);

    /**
     * Executes an event asynchronously
     *
     * @param event the event to execute
     * @param <T>   the type of the event
     */
    public <T extends Event> void callEventAsync(T event);

    /**
     * Executes an event asynchronously using a completion callback
     *
     * @param event    the event to execute
     * @param callback the completion handler after the event is fired
     * @param <T>      the type of the event
     */
    public <T extends Event> void callEventAsync(T event, Consumer<T> callback);

    /**
     * Register an event
     *
     * @param owner            the owner of the event
     * @param handler          the handler of the event
     * @param <T>              the type of the event
     */
    public <T extends Event> void registerEvent(Plugin owner, Consumer<T> handler);

    /**
     * Register all events on the given object
     *
     * @param owner    the owner of the events
     * @param listener the storage of the events
     */
    public void registerEvents(Plugin owner, Object listener);

    /**
     * Unregister all events of the given plug-in
     *
     * @param owner the plug-in to unregister
     */
    public void unregisterEvents(Plugin owner);

    /**
     * Unregister all events
     */
    public void unregisterAll();
}
