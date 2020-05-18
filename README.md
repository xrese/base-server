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

```
$ gradle build
```

## Tests

To execute the project's tests run the following command from the project root director:

```
$ gradle test
```

## License Info

This project requires that all source files have the license header at the top of the file. To ensure that all files have the current license please run the following command from the project root directory:

```
$ gradle licenseFormat
```

## Generating the Site

To generate and publish the github pages you simply need to run the following command from the project root directory:

```
$ gradle gitPublishPush
```

## Generating Javadoc

TODO

## Working with ActiveJDBC

After modifying either the database or the model classes you need to run the following commands from the project root:

```
$ gradle instrumentGroovyModels
$ gradle instrumentModels
```

## Static Analysis

This project utilizes several static analysis tools.

To run checkstyle, spotbugs, pmd, and JaCoCo use the following command:

```
$ gradle check
```

This will generate reports in the `target/reports` directory under each of the tools particular subdirectories