# Simple Chat Server

Design and implementation of a chat server for SMART Challenge

These frameworks have been used for developing the backend service:

  1. Spring Boot : framework used to create microservices.
  2. JAX-RS : framework to create RestFull services.
  3. Hibernate Validator : as bean validator.
  4. Spring Websocket : as subscription management framework.
  5. Spring Data JPA with Hibernate: as persistency framework.
  6. Spring Security: as authentication framework.
  7. Lombok: as automatic code generator.
  8. PostgreSQL: as DDBB.
  9. H2 DataBase: as testing DDBB.


These resources have been created:

## User

REST resource in charge of managing the users. Exposes these methods:


1. `GET /api/v1/login`: The authentication method is *Basic Auth*, which includes the next header:

`Authorization: Basic dXNlcjpwYXNzd29yZA==`

This keyword is obtained as this example:
```java
String plainCredentials = "user:password";
String base64Credentials = Base64.getEncoder().encodeToString(plainCredentials.getBytes());
```

This call returns a cookie, which we will use in further actions:

```
HTTP/1.1 200 
Set-Cookie: JSESSIONID=667B4CDA7E90611C2D54FFCA22D1E52D; Path=/; HttpOnly
```

2. `GET /api/v1/logout`: Finishe the session related to the cookie
3. `POST /api/v1/user`: Create a new user. The body must include this structure:

```json
{
    "userName": "user",
    "userPassword": "password"
}
```


## Chat

REST resource in charge of managing the chatrooms.Expose these methods:

1. `GET /api/v1/rooms`: List all existing chatrooms.
2. `POST /api/v1/rooms`: Create a new chatroom. The body must include this structure:



```json
{
    "chatRoomName": "chatroom"
}
```

All these requests must include the ***cookie JSESSIONID***.



## Message

REST resource in charge of managing some messages. Expose these methods:

1. `GET /api/v1/room/{roomId}/messages`: List all existing messages into the chatroom identified by `roomId`.
2. `GET /api/v1/messages`: List all private messages sent by everyoneone to the user authenticated in the current session.
3. `POST /api/v1/room/{roomId}/messages`: Create a new message into chatroom identified by `roomId`. The body must include this structure:

```json
{
    "message": "message to be sent to chatroom roomId"
}
```


4. `POST /api/v1/user/{userId}/messages`: Create a new message addressed to the user identified by `userId `. The body must include this structure:

```json
{
    "message": "private message to be sent to user userId"
}
```


## Subscription

The Simple Chat Server offers a real-time service that delivers just-created messages. Authenticated users can receive real-time messages using **Web Sockets** technology. In order to do that, these steps must be followed:

1. Open a websocket to the next address: ```ws://localhost:8080/rooms```
2. Subscribe to one of these channels:
	1. ```/queue/rooms/{roomId}/subscribe```: Receive real-time messages sent to the chatroom identified by ```roomId```.
	2. ```/user/queue/private-message```: Receive real-time messages sent to the user in the current session.


Execute the Simple Chat Server 
=======

## Data Base

The only requirement to execute the server, is having a ```Postgres``` database running and listening in the port 5432. The username is ```postgres``` and the password ```postgrespw```.

## Execution


The server can be executed with this command:

```mvn spring-boot:run```

Tests can be executed with this command:

```mvn test```

Finally, load test can be executed with this command:

```mvn jmeter:jmeter```

In order to test the subscription, an html page has been created in the next path:

```SimpleChatServer/src/test/resources/index.html```

There are two initial valid users:

User 1:
```
login: admin
password: password
```

User 2:
```
login: user
password: password
```

When it's opened, this is the webpage:

![Captura de pantalla 2022-06-15 a las 5 36 10](https://user-images.githubusercontent.com/1610027/173835987-d79ffb72-af92-4a63-9e4f-486f837aca6e.png)


The dropdown menu has 2 options:

![Captura de pantalla 2022-06-15 a las 15 21 36](https://user-images.githubusercontent.com/1610027/173837242-4b6e8ba1-cfae-4513-9c77-7dcedb9457a2.png)


Selecting one of this options, and clicking the ```Connect``` button, real-time messages wil be displayed.

License
=======

The MIT License (MIT)
Copyright (c) 2022 Fidel Besada Juárez

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
