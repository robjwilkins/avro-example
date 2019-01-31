# avro-example
This project contains an example java application demonstrating the use of 
spring to publish and subscribe to messages posted on kafka topics.
The project uses apache avro to provide a schema definition for these
objects, and uses a schema registry to hold these schemas

The project consists of three sub-projects:

- **schema-registry**: runs an avro schema-registry
- **avro-producer**: small REST service which can be called to publish a simple message to a kafka topic
- **avro-consumer**: service which listens for messages posted to a kafka topic

## Building the project
at the command line run:

`./gradlew clean build`

this will build all three projects

## Running the projects
A prerequesite is that you must have a running kafka instance available.
**The config in the projects assumes that kafka is available on localhost:9092**

At the commandline:

`./gradlew :schema-registry:bootRun`

`./gradlew :avro-producer:bootRun`

`./gradlew :avro-consumer:bootRun`

Test it:

`curl localhost:8101/produce`