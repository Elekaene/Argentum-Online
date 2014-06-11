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

import java.io.File;
import java.util.List;

/**
 * Define the manager of {@link Plugin}
 */
public interface PluginManager {
    /**
     * Disables all plug-ins
     */
    public void disableAll();

    /**
     * Disables the specified plugin
     * <p>
     * Attempting to disable a plugin that is already disabled will have no
     * effect
     *
     * @param plugin the plug-in to disable
     */
    public void disablePlugin(Plugin plugin);

    /**
     * Enables the specified plugin
     * <p>
     * Attempting to enable a plugin that is already enabled will have no effect
     *
     * @param plugin the plug-in to enable
     */
    public void enablePlugin(Plugin plugin);

    /**
     * Checks if the given plug-in is loaded and returns it when applicable
     * <p>
     * Please note that the name of the plug-in is case-sensitive
     *
     * @param name name of the plug-in to check
     * @return plug-in if it exists, otherwise null
     */
    public Plugin getPlugin(String name);

    /**
     * Gets a list of all currently loaded plug-ins
     *
     * @return list of plug-ins
     */
    public List<Plugin> getPlugins();

    /**
     * Gets if the plug-in with the given name is loaded
     *
     * @param name the name of the plug-in
     * @return true if the plug-in is available
     */
    public boolean isAvailable(String name);

    /**
     * Loads the plug-in in the specified file
     * <p>
     * File must point to a valid descriptor file
     *
     * @param file file containing the plug-in to load
     * @return the plug-in loaded, or null if was invalid
     */
    public Plugin loadPlugin(File file);

    /**
     * Loads all plug-ins in the specified directory
     *
     * @param directory directory containing the plug-ins to load
     * @return a list of valid plug-ins loaded
     */
    public Plugin[] loadPlugins(File directory);

    /**
     * Disables and unload all plug-ins
     */
    public void unloadAll();
}