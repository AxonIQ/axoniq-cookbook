Testing the Axon Application
============================

- Cooks in - 30 minutes
- Difficulty - easy

This recipe will give an overview of how to test your Axon Application. This recipe is based on the application that is created in the link:16_basic-axon-framework-application-with-spring-boot.adoc[Simple application using Axon Framework and Spring Boot] recipe. Axon Framework provides a Fixture test setup to see whether a Command is handled and the Event(s) expected applied.

Next to the test of an Aggregate, this recipe will give examples of testing the `AccountController` and the `AccountProjector`.

Ingredients
-----------

|Dependency                             |Version         |
|---------------------------------------|----------------|
|Axon Framework                         |3.1.X           |
|Spring Boot starter for Axon Framework |3.1.X           |
|Axon Test                              |3.1.X           |
|Spring Boot                            |1.5.X.RELEASE   |
|Kotlin                                 |1.1.X           |
|Mockito Kotlin                         |1.5.X           |

|Axon Framework components |
|--------------------------|
|Fixture Configuration     |
|Aggregate                 |
|Command handlers          |
|Event handlers            |
|Event Sourcing handlers   |

Method
------

### 1 Add dependencies

For testing the Axon Application we need two extra dependencies.

The first one is the Kotlin version of [Mockito](http://site.mockito.org/):

```xml
<dependency>
    <groupId>com.nhaarman</groupId>
    <artifactId>mockito-kotlin</artifactId>
    <version>1.5.X</version>
</dependency>
```

The second one is the test module of the Axon Framework. The module provides configuration for testing of Aggregates and Sagas.

```xml
<dependency>
    <groupId>org.axonframework</groupId>
    <artifactId>axon-test</artifactId>
    <version>3.1</version>
</dependency>
```

### 2 Add AccountController test

### 3 Add Account Aggregate test

### 4 Add AccountProjector test

Other readings and recipes
--------------------------

Contact
-------
For any question about the recipe or Axon Framework in general, please contact us via

- [Axon user group](https://groups.google.com/forum/#!forum/axonframework)
- [AxonIQ support](http://www.axoniq.io)
