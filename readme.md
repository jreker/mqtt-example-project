# mqtt Example Project

# getting started
To use this example please follow the steps below. You need to execute all commands from unix or windows cmd with the project as root directory.

## Prerequisites
- Java 8
- Docker (Tested with Engine 20.10.14 / Docker Desktop 4.8.2)
- Maven (Tested with 3.8.5)


### 1. Start MQTT-Broker HiveMQ
Just start the Broker with default configuration with docker. 
It exposes two ports:
-   1883 -> MQTT Default Port
-   8080 -> HiveMQ Webinterface (Management and Metrics)
```
docker run -p 8080:8080 -p 1883:1883 --name hivemq hivemq/hivemq4
```

### 2. Compile Java Projects
Run the steps below to compile both Java projects with Maven.
```
mvn clean package -f publisher/pom.xml
mvn clean package -f subscriber/pom.xml
```

### 3. Start Publisher
Start the Publisher from console. The publisher will send 500 messages per second to the broker.
```
java -jar publisher/target/coupling-0.0.1-SNAPSHOT.jar
```

### 4. Start Subscriber
Start the Subscriber from console. The subscriber recieves the messages of the topic and shows every 500st message in console.
```
java -jar subscriber/target/subscribe-0.0.1-SNAPSHOT.jar
```
