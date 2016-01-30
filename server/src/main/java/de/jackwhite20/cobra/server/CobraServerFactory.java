package de.jackwhite20.cobra.server;

import de.jackwhite20.cobra.server.impl.CobraConfig;
import de.jackwhite20.cobra.server.impl.CobraServerImpl;

/**
 * Created by JackWhite20 on 30.01.2016.
 */
public class CobraServerFactory {

    public static CobraServer create(CobraConfig cobraConfig) {

        return new CobraServerImpl(cobraConfig);
    }
}
