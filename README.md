# Cobra
Cobra is a simple RESTful framework to create lightweight RESTful services with Java.

# Supported HTTP methods
- POST
- GET
- PUT
- DELETE

# Installation

- Install [Maven 3](http://maven.apache.org/download.cgi)
- Clone/Download this repo
- Install it with: ```mvn clean install```

**Maven dependencies**

_Client:_
```xml
<dependency>
    <groupId>de.jackwhite20</groupId>
    <artifactId>cobra-client</artifactId>
    <version>0.1-SNAPSHOT</version>
</dependency>
```
_Server:_
```xml
<dependency>
    <groupId>de.jackwhite20</groupId>
    <artifactId>cobra-server</artifactId>
    <version>0.1-SNAPSHOT</version>
</dependency>
```

# Quick start

### Client
_Simple POST request:_
```java
try {
	CobraClient client = CobraClientFactory.create();
	Response response = client.post(new URL("http://somesite.net:8080/some/path"), Body.form("name", "SomeName").form("data", "SomeData").build(), Headers.empty());
	System.out.println("Status: " + response.status());
	System.out.println("Body: " + response.body().content());
	for (Map.Entry<String, String> entry : response.headersMap().entrySet()) {
		System.out.println("Header: " + entry.getKey() + ": " + entry.getValue());
	}
} catch (Exception e) {
	e.printStackTrace();
}
```
_It is also possible to get the raw bytes from a response:_
```java
byte[] raw = response.body().bytes();
System.out.println("Body from bytes: " + new String(raw));
```

_Simple GET request:_
```java
try {
	CobraClient client = CobraClientFactory.create();
	Response response = client.get(new URL("http://somesite.net:8080/some/path"), Headers.empty());
	System.out.println("Status: " + response.status());
	System.out.println("Body: " + new String(response.body().bytes()));
	for (Map.Entry<String, String> entry : response.headersMap().entrySet()) {
		System.out.println("Header: " + entry.getKey() + ": " + entry.getValue());
	}   
} catch (Exception e) {
	e.printStackTrace();
}
```

_Simple file download:_
```java
CobraClient client = CobraClientFactory.create();
Response response = client.download(new URL("http://somesite.net:8080/some/download/path"), Headers.empty(), "C:\\Some\\Path\\To\\A\\Folder");
System.out.println("Status: " + response.status());
```

### Server
_Example RESTful server:_
```java
import de.jackwhite20.cobra.server.CobraServer;
import de.jackwhite20.cobra.server.CobraServerFactory;
import de.jackwhite20.cobra.server.http.Request;
import de.jackwhite20.cobra.server.http.annotation.FormParam;
import de.jackwhite20.cobra.server.http.annotation.Path;
import de.jackwhite20.cobra.server.http.annotation.Produces;
import de.jackwhite20.cobra.server.http.annotation.method.GET;
import de.jackwhite20.cobra.server.http.annotation.method.POST;
import de.jackwhite20.cobra.server.impl.CobraConfig;
import de.jackwhite20.cobra.shared.ContentType;
import de.jackwhite20.cobra.shared.http.Response;

/**
 * Created by JackWhite20 on 17.02.2016.
 */
public class ExampleCobraServer {

    public static void main(String[] args) {

		// Create a new server instance with the cobra config below
        CobraServer cobraServer = CobraServerFactory.create(new ExampleConfig());
        // Start the server
        cobraServer.start();
    }

    public static class ExampleConfig extends CobraConfig {

        public ExampleConfig() {

			// Set the listening host ip
            host("0.0.0.0");
            // Set the port to listen on
            port(8080);

			// Register our example resource
            register(ExampleResource.class);
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
    }
}
```

# License

Licensed under the GNU General Public License, Version 3.0.
