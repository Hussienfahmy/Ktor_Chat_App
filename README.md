# Ktor_Chat_App
Example Android app uses ktor client and connect to ktor server

For the backend ktor server visit this [repo](https://github.com/Hussienfahmy/ktor-chat-server)  

# Technologies used
- Compose
- Ktor client
- Hilt

# Gradle Properties
before building this app add these properties in any `.properties` file for example `local.properties` file:
``` properties
http_protocol=http
server_ip= // the ip / host of the ktor server application
server_port= // the port of the ktor server application
```

