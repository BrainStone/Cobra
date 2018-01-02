/*
 * Copyright (c) 2018 "JackWhite20"
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
import de.jackwhite20.cobra.server.http.annotation.*;
import de.jackwhite20.cobra.server.http.annotation.method.GET;
import de.jackwhite20.cobra.server.http.annotation.method.POST;
import de.jackwhite20.cobra.shared.ContentType;
import de.jackwhite20.cobra.shared.http.Response;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by JackWhite20 on 30.01.2016.
 */
@Path("/master")
public class TestResource {

    private AtomicInteger atomicInteger = new AtomicInteger();

    @POST
    @Path("/info")
    @Produces(ContentType.TEXT_HTML)
    public Response info(Request httpRequest, @FormParam("name") String name, @FormParam("password") String password) {
        System.out.println("Name: " + name);
        System.out.println("Password: " + password);

        return Response.ok().content("<H1>info " + atomicInteger.getAndIncrement() + "</H1>").header("X-Test", "Hello :D").build();
    }

    @GET
    @Path("/test")
    public Response test(Request httpRequest) {
        return Response.ok().content("<H1>test</H1>").build();
    }

    @GET
    @Path("/hello/{name}")
    public Response hello(Request httpRequest, @PathParam String name) {
        return Response.ok().content("<H1>Hello " + name + "!</H1>").build();
    }

    @GET
    @Path("/bye/{name}/{name2}")
    public Response bye(Request httpRequest, @PathParam String name, @PathParam String name2) {
        return Response.ok().content("<H1>Bye " + name + " " + name2 + "!</H1>").build();
    }

    @GET
    @Path("/download")
    @Produces(ContentType.APPLICATION_OCTET_STREAM)
    public Response download(Request httpRequest) {
        return Response.file("E:\\GitHub\\Cobra\\server\\src\\test\\java\\text.txt");
        //return Response.file(new File("E:\\GitHub\\Cobra\\server\\src\\test\\java\\text.txt"));
    }

    @POST
    @Path("/upload")
    @Consumes(ContentType.APPLICATION_OCTET_STREAM)
    public Response upload(Request httpRequest) {
        // Raw post data as bytes
        byte[] bytes = httpRequest.body().bytes();

        return Response.ok().build();
    }
}
