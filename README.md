# Microservices Demo

This Demo represents a microservices POC by building an application with 3 services and showing the interoperability between them.

**WARNING:** Only maven build has been used. Gradle build can be added too.

Clone it and either load into your favorite IDE or use maven directly.

## Versions

Current version corresponds to

* Spring Boot 2.3.2.
* Java 8


If running with Java 11 or later, you need to upgrade the build to include additional dependencies.

## Using an IDE

This application is built from scratch with Intellij Idea 2020.2 Ultimate. You can run the system in your IDE by running the three server classes in order: _UserService_, _ProductService_ and _ShopService_.  Each is a Spring Boot application using embedded Tomcat.  In Spring Tool Suite use `Run As ... Spring Boot App` otherwise just run each as a Java application - each has a static `main()` entry point.



## Hierarchy and business

In the top level directory of the project you find the main repository _microservices-demo-master_. This directory contain the main maven project called _back_ which contains 2 modules : 

 1. model: contains the model classes representing the business entities of the project.
 2. services: contains the 3 services built for the application
    1. user-service: contains user-related services
    2. product-service: contains product-related services
    3. shop-service: represents the back-end of a fictive web application that consumes the first 2 services

## Procedure

To run the microservices system from the command-line, open three CMD windows (Windows) or  Terminal windows (MacOS, Linux) and arrange so you can view them conveniently.

  1. In the first window, build the application using either `./mvnw clean intsall` for the main project called _back_. This build will generate the all the services as `.jar` files which can be found in their `target/` directories.
  2. For each service open a new terminal window and go the `target` directory and run the `java -jar` command followed by the name of the `jar` file.
 
 ## Run the services in your browser
  
Open your browser (Firefox for example) and access to each of the services :

 1. UserService is launched on : http://localhost:8082/
 2. ProductService is launched on : http://localhost:8081/
 3. ShopService is launched on : http://localhost:8080/
 
  ### Example of using the demo :

 1. Access to the list of all users : http://localhost:8080/users/allUsers 
 2. Access to a specific user by his ID : http://localhost:8082/users/findByUserId?id=1
 3. Access to the list of all products : http://localhost:8081/products/allProducts
 4. Use Shop Service to add new product by specifying user ID and product attributes : http://localhost:8080/shop/createProduct?userid=2&name=IPhone11&description=test&price=1000.00


## Using Docker

TODO