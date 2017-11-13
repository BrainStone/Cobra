/*
 * Copyright (c) 2017 "JackWhite20"
 *
 * This file is part of Cobra.
 *
 * Cobra is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.jackwhite20.cobra.server;

import de.jackwhite20.cobra.server.impl.CobraConfig;

/**
 * Created by JackWhite20 on 30.01.2016.
 */
public interface CobraServer {

    /**
     * Starts the server.
     */
    void start();

    /**
     * Stops the server.
     */
    void stop();

    /**
     * Sets the config to use.
     *
     * Needs to be called before start, if no config was passed to the factory.
     *
     * @param cobraConfig The config to use.
     */
    void config(CobraConfig cobraConfig);
}
