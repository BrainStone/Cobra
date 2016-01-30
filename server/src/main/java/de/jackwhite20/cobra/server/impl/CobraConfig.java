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

package de.jackwhite20.cobra.server.impl;

import de.jackwhite20.cobra.server.http.annotation.Path;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JackWhite20 on 30.01.2016.
 */
public abstract class CobraConfig {

    protected String host;

    protected int port;

    protected int backLog;

    private List<Class<?>> classes = new ArrayList<>();

    public void host(String host) {

        this.host = host;
    }

    public void port(int port) {

        this.port = port;
    }

    public void backLog(int backLog) {

        this.backLog = backLog;
    }

    public void register(Class<?> clazz) {

        if(!clazz.isAnnotationPresent(Path.class))
            throw new IllegalArgumentException("class " + clazz.getName() + " needs a Path annotation");

        classes.add(clazz);
    }
}
