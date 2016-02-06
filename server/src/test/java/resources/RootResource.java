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

package resources;

import de.jackwhite20.cobra.server.http.Request;
import de.jackwhite20.cobra.server.http.Response;
import de.jackwhite20.cobra.server.http.annotation.method.GET;
import de.jackwhite20.cobra.server.http.annotation.Path;

/**
 * Created by JackWhite20 on 04.02.2016.
 */
@Path("*")
public class RootResource {

    @GET
    @Path("")
    public Response root(Request httpRequest) {

        return Response.ok().content("Root path!").build();
    }
}
