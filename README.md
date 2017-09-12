# gow-bot
Discord bot in Java based on spring-boot-docker and Discord4J.

## Requirements:
Maven and Docker.

## TL;DR
### Build and deploy:
```
mvn clean package docker:build
docker run -d -p 8080:8080 -e GOW_TOKEN="YOUR_CLIENT_TOKEN" darkagma/gow-bot
```

### Invite bot:
```
https://discordapp.com/oauth2/authorize?client_id=YOUR_CLIENT_ID&scope=bot&permissions=0
```

### List containers:
```
docker container ls
```

### Stop container:
```
docker stop CONTAINER_ID
```