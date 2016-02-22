/*
 * Copyright (c) 2016 "JackWhite20"
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

package de.jackwhite20.cobra.client;

import com.google.common.base.Preconditions;
import de.jackwhite20.cobra.client.impl.CobraClientImpl;

/**
 * Created by JackWhite20 on 17.02.2016.
 */
public final class CobraClientFactory {

    private CobraClientFactory() {
        // Do not allow instance creation
    }

    public static CobraClient create() {

        return new CobraClientImpl();
    }

    public static CobraClient create(int connectTimeout) {

        Preconditions.checkArgument(connectTimeout < 0, "connectTimeout cannot be negative");

        return new CobraClientImpl(connectTimeout);
    }
}
