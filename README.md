
# Table of Contents 
- [Problem Description](#problem-description)
  * [Short Introduction](#short-introduction)
  * [Assignment problem](#assignment-problem)
  * [Generalized assignment problem](#generalized-assignment-problem)
- [Algorithms](#algorithms)
  * [Genetic Algorithm](#genetic-algorithm)
  * [Simulated Annealing](#simulated-annealing)
- [Application Demo](#application-demo)
  * [Generating and visualizing input data](#generating-and-visualizing-input-data)
  * [Solving model I and presenting results](#solving-model-i-and-presenting-results)
  * [Solving model II and III and presenting results](#solving-model-ii-and-iii-and-presenting-results)
- [Project Structure](#project-structure)
- [Technologies](#technologies)
  * [Architecture](#architecture)
  * [Libraries](#libraries)
  * [Other resources](#other-resources)
- [How to run](#how-to-run)




<!-- toc -->


## Problem Description

### Short Introduction

+ The purpose of this project is implementation of three mathematical models representing **_specific, NP-hard task allocation 
problems_** in production environment. The project was designed by applying **_operational research_** methodology, including all 
of its stages.

![drawing](https://user-images.githubusercontent.com/37666186/52162660-9d46d580-26d7-11e9-9f38-ec242f105284.JPG)

+ __Model I__ - One task is to be completed by one machine, one machine can be assigned multiple tasks.
+ __Model II__ - One task may require more than one machine to be completed, one machine can be assigned multiple tasks.
+ __Model III__ - Includes additional constraints narrowing the space of possible solutions. 

### Assignment Problem
https://en.wikipedia.org/wiki/Assignment_problem

### Generalized assignment problem
https://en.wikipedia.org/wiki/Generalized_assignment_problem


## Algorithms

### Genetic Algorithm
A multi-variant implementation of the algorithm includes various ways of generating initial population, crossover, 
selection, mutation.


Sample crossover           |  Recreating population
:-------------------------:|:-------------------------:
![d1](https://user-images.githubusercontent.com/37666186/52163019-1a744980-26dc-11e9-84d4-1442e2566cea.JPG) |  ![d3](https://user-images.githubusercontent.com/37666186/52163022-23fdb180-26dc-11e9-8281-0854bbaed138.JPG)

https://en.wikipedia.org/wiki/Genetic_algorithm

### Simulated Annealing
A multi-variant implementation of the algorithm includes various cooling functions and ways of generating new solutions.

https://en.wikipedia.org/wiki/Simulated_annealing


## Application Demo
### Generating and visualizing input data
![input](https://user-images.githubusercontent.com/37666186/52163276-164a2b00-26e0-11e9-9b08-e3e67f9e1816.gif)
### Solving model I and presenting results
![model1](https://user-images.githubusercontent.com/37666186/52163323-a25c5280-26e0-11e9-80af-0667ff93f975.gif)
### Solving model II and III and presenting results

## Project Structure

* spring-boot-web-application
  * src/main/java
    + com.first.springboot.web.springbootwebapplication
      * SpringBootWebApplication.java - main 
    + com.first.springboot.web.springbootwebapplication.controller
      * Controllers
    + com.first.springboot.web.springbootwebapplication.model
      * Todo.java - Entity class for database 
    + com.first.springboot.web.springbootwebapplication.security
      * Security Configuration
    + com.first.springboot.web.springbootwebapplication.service
      * Database repository and service
  * scr/main/resources
    + application.properties
    + data.sql - initializing database
    + test-server.ldif - users database ( LDAP )
    + /static/css
      * CSS styles 
    + /static/img
      * Images
    + /static/js
      * JS scripts
  * scr/main/webapp/WEB-INF/jsp
    + jsp files
    + /common
      * shared jsp files
  * pom.xml

## Technologies
* Java 8
* Maven
* Spring 
  * Boot 
  * WEB
  * H2 / JPA 
  * DevTools
  * Security
  * LDAP
* HTML/CSS/JavaScript
* JSTL

### Libraries
* Bootstrap
* JQuery

### Architecture

* Typical Java EE architecture
* MVC 

### Other resources

* Clouds created basing on this online tutorial: 
https://www.clicktorelease.com/blog/how-to-make-clouds-with-css-3d/


## How to run

* Eclipse IDE Photon 
  * File -> Import -> Existing Maven Project -> Choose Project Directory ( spring-boot-web-application ) -> Right-click SpringBootWebApplication.java -> Run as Java application -> Go to your browser -> Type address localhost:8999 

* Servers 
  * Apache Tomcat - Test server for building application
  * Embedded Apache Directory Server  - Test server for LDAP







