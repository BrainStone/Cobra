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
import de.jackwhite20.cobra.server.impl.CobraServerImpl;

/**
 * Created by JackWhite20 on 30.01.2016.
 */
public final class CobraServerFactory {

    private CobraServerFactory() {
        // Do not allow instance creation
    }

    public static CobraServer create(CobraConfig cobraConfig) {

        if (cobraConfig == null) {
            throw new IllegalArgumentException("cobraConfig cannot be null");
        }

        return new CobraServerImpl(cobraConfig);
    }

    public static CobraServer create() {

        return new CobraServerImpl();
    }
}
