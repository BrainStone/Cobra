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

import de.jackwhite20.cobra.client.CobraClient;
import de.jackwhite20.cobra.client.CobraClientFactory;
import de.jackwhite20.cobra.shared.http.Headers;
import de.jackwhite20.cobra.shared.http.Response;

/**
 * Created by JackWhite20 on 04.02.2016.
 */
public class CobraClientTest {

    public static void main(String[] args) throws Exception {
        /*CobraClient client = CobraClientFactory.create();
        Response response = client.post(new URL("http://localhost:8080/master/info"), Body.form("name", "Name").form("password", "Password").build(), Headers.empty());
        System.out.println("Status: " + response.status());
        System.out.println("Body: " + new String(response.body().bytes()));
        for (Map.Entry<String, String> entry : response.headersMap().entrySet()) {
            System.out.println("Header: " + entry.getKey() + ": " + entry.getValue());
        }*/
        CobraClient client = CobraClientFactory.create();
        //Response response = client.post(new URL("http://localhost:8080/master/upload"), Body.of(Files.readAllBytes(Paths.get("pom.xml"))), Headers.from("Content-Type", ContentType.APPLICATION_OCTET_STREAM.name()));
        for (int i = 0; i < 5; i++) {
            Response response = client.get("https://paste.jackwhite20.net/load.php?p=WND0EZ", Headers.empty());
            System.out.println("Status: " + response.status());
        }
    }
}
