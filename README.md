# Tic-Tac-Toe

Yet another Tic-Tac-Toe application in Java. Java 17 and Maven are required to build, run and test.

## Requirements
- Java 17
- Maven

## How to build

You can build the application and create the jar file with following command:

```
mvn clean package
```

jar file will be created in the target folder.

## How to test

To run all the tests you can use with following command:

```
mvn test
```

## How to run the application

+ Using Java

```
  mvn clean package
  cd target && java -cp "*" apprenticeship.TicTacToe
```

- Using Maven

```
mvn compile exec:java -Dexec.mainClass="apprenticeship.TicTacToe"
```

## How to run pi test (https://pitest.org/)

```
mvn test-compile org.pitest:pitest-maven:mutationCoverage
```