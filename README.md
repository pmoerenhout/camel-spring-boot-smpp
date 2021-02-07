# Camel Spring Boot SMPP
Camel Spring Boot SMPP example and throughput tester

### Introduction
This example sends Camel messages to an SMSC from a Spring Boot app.

### Build
You will need to compile this example first:

	mvn verify

### Run
To run the example type

	java -jar target/camel-spring-boot-smpp.jar

You will see 2 test, one using the Camel send() executed by a taskExecutor and one using the Camel asyncSend().

### Configuration
Specify the SMPP remote address in the property `camel.component.smpp.configuration.host`.
Spring Boot auto-configures the Camel SMPP component. 

The jSMPP project has an SMPPServerSimulator to act as a SMSC.