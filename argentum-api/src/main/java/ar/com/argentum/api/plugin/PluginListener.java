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
package ar.com.argentum.api.plugin;

/**
 * Define the base listener for any plug-in file for listening to the current {@link Plugin}
 */
public interface PluginListener {
    /**
     * Notify the registrant the {@link Plugin} was disabled
     *
     * @param plugin the plug-in to notify
     */
    public void onPluginDisable(Plugin plugin);

    /**
     * Notify the registrant the {@link Plugin} was enabled
     *
     * @param plugin the plug-in to notify
     */
    public void onPluginEnable(Plugin plugin);

    /**
     * Notify the registrant the {@link Plugin} was loaded
     *
     * @param plugin the plug-in to notify
     */
    public void onPluginLoad(Plugin plugin);

    /**
     * Notify the registrant the {@link Plugin} was unloaded
     *
     * @param plugin the plug-in to notify
     */
    public void onPluginUnload(Plugin plugin);
}

