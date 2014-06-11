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
import java.util.logging.Logger;

/**
 * Define the base class for a plug-in
 */
public interface Plugin {
    /**
     * Gets the descriptor of this plug-in
     *
     * @return the descriptor of the plug-in
     */
    public Descriptor getDescriptor();

    /**
     * Gets the folder of this plug-in
     *
     * @return the folder of the plug-in
     */
    public File getFolder();

    /**
     * Gets the name of this plug-in
     *
     * @return the name of the plug-in
     */
    public String getName();

    /**
     * Gets if this plug-in is disabled
     *
     * @return true if the plug-in is disabled
     */
    public boolean isDisabled();

    /**
     * Gets if this plug-in is enabled
     *
     * @return true if the plug-in is enabled
     */
    public boolean isEnabled();

    /**
     * Gets the {@link Logger} of the plug-in
     *
     * @return the logger instance of the plug-in
     */
    public Logger getLogger();
}
