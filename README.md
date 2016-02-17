# Cobra
Cobra is a simple RESTful framework to create lightweight RESTful services with Java.

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

### License

Licensed under the GNU General Public License, Version 3.0.
