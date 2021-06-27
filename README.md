# Poker Hand Sorter

Java8 Lambda implementation.

## How to build Jar

```
mvn package

```

## How to run from project root

```
java -jar ./target/PokerHandSorter-1.0.0-jar-with-dependencies.jar ./src/test/resources/poker-hands.txt  

```

## How to run with log4j enabled

```
java -jar -Dlog4j.configuration=FILE:./src/main/resources/log4j.properties ./target/PokerHandSorter-1.0.0-jar-with-dependencies.jar ./src/test/resources/poker-hands.txt
  
```