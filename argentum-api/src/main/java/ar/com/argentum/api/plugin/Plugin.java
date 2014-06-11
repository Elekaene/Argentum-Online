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
 * Define the base class for a plug-in
 */
public class Plugin {
    private final Descriptor descriptor;
    private final String folder;
    private boolean isEnabled;

    /**
     * Default constructor of {@link Plugin}
     *
     * @param descriptor the descriptor that contains the information of the plug-in
     * @param folder     the folder where the plug-in store all configuration
     */
    public Plugin(Descriptor descriptor, String folder) {
        this.descriptor = descriptor;
        this.folder = folder;
        this.isEnabled = false;
    }

    /**
     * Gets the descriptor of this {@link Plugin}
     *
     * @return the descriptor of the plug-in
     */
    public Descriptor getDescriptor() {
        return descriptor;
    }

    /**
     * Gets the folder of this {@link Plugin}
     *
     * @return the folder of the plug-in
     */
    public String getFolder() {
        return folder;
    }

    /**
     * Gets the name of this {@link Plugin}
     *
     * @return the name of the plug-in
     */
    public String getName() {
        return descriptor.getName();
    }

    /**
     * Gets if this {@link Plugin} is disabled
     *
     * @return true if the plug-in is disabled
     */
    public boolean isDisabled() {
        return !isEnabled;
    }

    /**
     * Gets if this {@link Plugin} is enabled
     *
     * @return true if the plug-in is enabled
     */
    public boolean isEnabled() {
        return isEnabled;
    }

    ;

}
