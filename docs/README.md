# Independent rating system for sellers of in-game items.
This is the backend for an independent rating system for in-game item sellers.
## Description
Spring-based web application designed to provide an independent rating 
system for sellers of in-game items, including anonymous commenting and 
fair rating system.
## Features
* Seller registration and authentication
* Rating profiles based on comments
* Fully anonymous comment system
* Information about in-game objects
* Account and comment verification by admins
## User scenarios
* Seller Registration: A Seller visits the site and fills out a form to create their profile. 
Also verifies email by confirmation code. Administrator reviews the information and approves or declines the request.
* Submitting a Comment: An Anonymous User views a Seller's profile and leaves a comment. 
Administrator verifies the comment and approves or declines it.
* Comment + Registration: If an Anonymous User doesn't find the seller they want to Comment, 
they can provide additional information to create the Seller's profile. Administrator reviews the submission and decides to approve or decline it.
### Technology stack
* Java
* Spring MVC, Spring Boot
* PostgreSQL
* Redis
* Lombok
* JJWT