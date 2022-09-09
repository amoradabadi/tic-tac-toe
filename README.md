# Tic-Tac-Toe

---
Yet another Tic-Tac-Toe application in Java. Maven is required to build, run and test.

## How to build

---
You can build the application and create the jar file with following command:

```
mvn clean package
```

jar file will be created in the target folder.

## How to test

---
To run all the tests you can use with following command:

```
mvn test
```

## How to run the application

+ Using Java

```
  mvn clean package
  cd target
  java -cp "*" TicTacToe
  ```

- Using Maven

```
mvn compile exec:java -Dexec.mainClass="TicTacToe"
```