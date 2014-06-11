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

import java.util.List;

/**
 * Define the information container of {@link Plugin}
 */
public final class Descriptor {
    private final List<String> authors;
    private final List<String> dependencies;
    private final String name;
    private final PluginOrder order;
    private final Platform platform;
    private final int version;
    private final String website;

    /**
     * Default constructor of {@link Descriptor}
     *
     * @param name         the name of the plugin
     * @param website      the website of plugins author
     * @param version      the version of the plugin
     * @param platform     the platform of the plugin
     * @param order        the order of the plugin
     * @param authors      the author list of the plugin
     * @param dependencies the dependency list of the plugin
     */
    public Descriptor(String name, String website, int version, Platform platform, PluginOrder order,
                      List<String> authors,
                      List<String> dependencies) {
        this.name = name;
        this.website = website;
        this.version = version;
        this.platform = platform;
        this.order = order;
        this.authors = authors;
        this.dependencies = dependencies;
    }

    /**
     * Return the author list of the {@link Plugin}
     *
     * @return a list that contains each author's name
     */
    public List<String> getAuthors() {
        return authors;
    }

    /**
     * Return the dependency list of the {@link Plugin}
     *
     * @return a list that contains each dependency's name
     */
    public List<String> getDependencies() {
        return dependencies;
    }

    /**
     * Return the name of the {@link Plugin}
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Return the initialise order of the {@link Plugin}
     *
     * @return the initialise order enumeration
     */
    public PluginOrder getOrder() {
        return order;
    }

    /**
     * Return the platform of the {@link Plugin}
     *
     * @return the platform enumeration
     */
    public Platform getPlatform() {
        return platform;
    }

    /**
     * Return the version of the {@link Plugin}
     *
     * @return the version
     */
    public int getVersion() {
        return version;
    }

    /**
     * Return the author's website of the {@link Plugin}
     *
     * @return the author's website
     */
    public String getWebsite() {
        return website;
    }
}
