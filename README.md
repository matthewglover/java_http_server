# HttpServer

## What

An HTTP Server built to meet the 8th Light Cob Spec HTTP specifications (see https://github.com/8thlight/cob_spec).

## To run:

### Pre-requisites

- Java 8 and JDK 1.6
- Maven

### Installation instructions

1. From the project root `cd` into the `cob_spec` directory.
2. Run the command `./run_spec` (n.b. this may take some time on first run)
3. Open http://localhost:9090/HttpTestSuite in a browser and click the `Suite` button to run the
full Cob Spec suite.

### Notes

Understanding this section is not required in order to run the spec against the server, and is
included for background information only.

The `./run_spec` command takes the following steps:

1. Runs `./gradlew build` in the project root to build the HttpServer and copies it into the `cob_spec` directory
2. Runs `mvn package` in the `cob_spec` directory to build the Cob Spec
3. Runs `java -jar fitnesse.jar -p 9090` in the `cob_spec` directory to start the Cob Spec server.

