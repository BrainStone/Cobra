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

    private List<Class<?>> classes = new ArrayList<>();

    public void host(String host) {

        this.host = host;
    }

    public void port(int port) {

        this.port = port;
    }

    public void register(Class<?> clazz) {

        if(!clazz.isAnnotationPresent(Path.class))
            throw new IllegalArgumentException("class " + clazz.getName() + " needs a Path annotation");

        classes.add(clazz);
    }
}
