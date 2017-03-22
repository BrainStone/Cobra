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

import de.jackwhite20.cobra.server.CobraServer;
import de.jackwhite20.cobra.server.CobraServerFactory;
import de.jackwhite20.cobra.server.filter.FilteredRequest;
import de.jackwhite20.cobra.server.filter.RequestFilter;
import de.jackwhite20.cobra.server.http.Request;
import de.jackwhite20.cobra.server.http.annotation.FormParam;
import de.jackwhite20.cobra.server.http.annotation.Path;
import de.jackwhite20.cobra.server.http.annotation.PathParam;
import de.jackwhite20.cobra.server.http.annotation.Produces;
import de.jackwhite20.cobra.server.http.annotation.method.GET;
import de.jackwhite20.cobra.server.http.annotation.method.POST;
import de.jackwhite20.cobra.server.impl.CobraConfig;
import de.jackwhite20.cobra.shared.ContentType;
import de.jackwhite20.cobra.shared.Status;
import de.jackwhite20.cobra.shared.http.Response;

/**
 * Created by JackWhite20 on 17.02.2016.
 */
public class Test {

    public static void main(String[] args) {

        CobraServer cobraServer = CobraServerFactory.create(new ExampleConfig());
        cobraServer.start();
    }

    public static class ExampleConfig extends CobraConfig {

        public ExampleConfig() {

            host("0.0.0.0");
            port(8080);

            register(ExampleResource.class);

            filter(ExampleFilter.class);
        }
    }

    @Path("/rest/v1")
    public static class ExampleResource {

        @GET
        @Path("/hello")
        @Produces(ContentType.TEXT_HTML)
        public Response hello(Request httpRequest) {

            return Response.ok().content("<H1>Hello</H1>").build();
        }

        @POST
        @Path("/world")
        @Produces(ContentType.TEXT_HTML)
        public Response world(Request httpRequest, @FormParam("name") String name) {

            return Response.ok()
                    .content("<H1>World " + name + "!</H1>")
                    .header("X-Test", "Hello xD")
                    .build();
        }

        @GET
        @Path("/say/{text}")
        @Produces(ContentType.TEXT_HTML)
        public Response hello(Request httpRequest, @PathParam String text) {

            return Response.ok().content("<H1>" + text + "</H1>").build();
        }
    }

    public static class ExampleFilter implements RequestFilter {

        @Override
        public void filter(FilteredRequest request) {

            // Here you can filter for headers or anything the request object has
            if (request.header("X-Special-Header") == null) {
                // Abort it
                request.abortWith(Response.status(Status.FORBIDDEN).build());
            }
        }
    }
}
