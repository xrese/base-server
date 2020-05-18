# XRESE Base Server

This project provides an easily configured base server component for quickly standing up Microservices base-on ActiveJDBC and Javalin.

## Configuration

Server configuration can be provided by modifying the file `server.conf`, which has the following format:

```
# Database configuration
# type is the type of dbms and can be one of:
#    mysql   - mysql
#    mariadb - maria db
#    postgresql - postgres
#    mssql - Microsoft SQL Server
#    sqlite - SQLite 3
#    derby - apache derby
#    h2 - H2
# user is the username for accessing the database (if required)
# pass is the password for accessing the database (if required)
# url is the url for the database connection
db {
     type = ""
     user = ""
     pass = ""
     url = ""
}

# Server configuration, which can be empty
# port is the server port connection, if missing will default to 7000
server {
     port = "" // port number of the server
}
```

## Build

To build the project run the following command from the project root directory:

```bash
$ gradle build
```

## Tests

To execute the project's tests run the following command from the project root director:

```bash
$ gradle test
```

## License Info

This project requires that all source files have the license header at the top of the file. To ensure that all files have the current license please run the following command from the project root directory:

```bash
$ gradle licenseFormat
```

## Generating the Site

The github pages site is generated from the `docs/` directory in the master branch. As changes are merged into master, that documentation should be updated.

## Generating Groovydoc

To generate the gradledoc into the `docs/javadoc` directory, simply execute the following command:

```bash
$ gradle groovydoc
``` 

## Working with ActiveJDBC

After modifying either the database or the model classes you need to run the following commands from the project root:

```bash
$ gradle instrumentGroovyModels
$ gradle instrumentModels
```

## Static Analysis

This project utilizes several static analysis tools.

To run checkstyle, spotbugs, pmd, and JaCoCo use the following command:

```bash
$ gradle check
```

This will generate reports in the `target/reports` directory under each of the tools particular subdirectories

## Publishing the package

This package can be published using the following instructions for the commandline.

First, you need to create a `gradle.properties` file with the following two properties

```properties
gpr.user = your github username
gpr.key = github personal access token setup for package publishing
```

**Note:** this file will not be added to git, as it is currently being ignored.

Next, you will need to execute the following command:

```bash
$ gradle publish
```

## Using the Package

You can use the published package from gradle as follows:

```groovy
repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/xrese/base-server")
        }
    }
}

dependencies {
    implementation 'edu.isu.xrese:base-server:1.0.1'
}
```

You can use the published package from maven as follows:

First setup the repository, as follows:

```xml
<profiles>
    <profile>
      <id>github</id>
      <repositories>
        <repository>
          <id>central</id>
          <url>https://repo1.maven.org/maven2</url>
          <releases><enabled>true</enabled></releases>
          <snapshots><enabled>true</enabled></snapshots>
        </repository>
        <repository>
          <id>github</id>
          <name>GitHub OWNER Apache Maven Packages</name>
          <url>https://maven.pkg.github.com/xrese/base-server</url>
        </repository>
      </repositories>
    </profile>
</profiles>
```

Then add the following dependency:

```xml
<dependency>
  <groupId>edu.isu.xrese</groupId>
  <artifactId>base-server</artifactId>
  <version>1.0.1</version>
</dependency>
```

Then execute the following command:

```bash
$ mvn install
```