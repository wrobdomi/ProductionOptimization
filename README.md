
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
  * [Tools](#tools)
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
![model1](https://user-images.githubusercontent.com/37666186/52163365-30d0d400-26e1-11e9-8033-9269a3d24160.gif)
### Solving model II and III and presenting results
![model2](https://user-images.githubusercontent.com/37666186/52163376-7d1c1400-26e1-11e9-9d59-a33b2476531b.gif)


## Project Structure

* production
  * src/main/java
    + com.engineering.config - __configuration files__
      * EngineeringAppConfig.java 
      * MyDatabaseConfig.java
      * MyDispatcherServletInitializer.java
    + com.engineering.controller - __controllers with algorithms__
      * AlgorithmsControllerModelOne.java
      * AnnealingControllerModelTwo.java
      * HomeController.java
      * MachineTaskController.java
    + com.engineering.dao - __Data Access Objects__
      * MachineDAO.java
      * MachineDAOImpl.java
      * TaskDAO.java
      * TaskDAOImpl.java
    + com.engineering.entity - __DB entities__
      * Machine.java
      * Task.java
    + com.engineering.json - __using JSON to communicate backend with frontend__ 
      * JsonReceive.java
      * JsonReceiveGeneticOne.java
      * JsonResponse.java
      * JsonResponseGeneticOne.java
      * JsonResponseOne.java
    + com.engineering.service - __other services__
      * MachineService.java
      * MachineServiceImpl.java
      * TaskService.java
      * TaskServiceImpl.java
    + com.engineering.servicemodelone - __algorithms services__
      * AnnealingOneService.java
      * GeneticOneService.java
      * ModelOneService.java
    + com.engineering.servicemodeltwo - __algorithms services__
      * AnnealingTwoService.java
    + com.engineering.twotasks
      * DoubleTask.java    
  * src/main/resources
    + application.properties
    + my_sql.properties
  * src/main/webapp/resources
    + css
      * CSS styles 
    + images
      * Images
    + js
      * JS scripts
  * src/main/webapp/WEB-INF/view
    + common
      * shared .jsp files
    + fragments
      * .jsp fragments
    + other .jsp
  * pom.xml

## Technologies
* Java 8
* Maven
* Spring 
* Hibernate
* HTML/CSS/JavaScript
* JSTL
* JSP

### Libraries
* Bootstrap
* JQuery
* Chart.js
* Cytoscape.js

### Architecture
* MVC 
* Database connection

### Tools
  * Eclipse IDE Photon
  * MySQL Workbench
  * Apache Tomcat - Test server for building application



## How to run

* Eclipse IDE Photon 
  * File -> Import -> Existing Maven Project -> Choose Project Directory ( production ) -> Run as Java application -> Go to your browser -> Type address localhost:8082/EngineeringDegreeApp/











