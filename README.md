# ChâTop

![chatop](https://user.oc-static.com/upload/2022/10/25/1666686016025_P3_Banner_V2.png)

We are creating a portal to connect future tenants and owners for seasonal rentals on the Basque coast initially and, later, throughout France.

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 14.1.0 and Java version 17.


## MySQL - Create database

SQL script for creating the schema is available `ressources/sql/script.sql`

> mysql -u root -p

> CREATE DATABASE nomdelabase;

> exit;

> mysql -u root -p nomdelabase < mon/chemin/nom_du_fichier.sql

## Start the project

Git clone:

> git clone https://github.com/ookaamii/Developpez-le-back-end-en-utilisant-Java-et-Spring

Create environnement file:

Create .env with with the mysql variables (with the database's name created previously) and jwt key

Go inside folder:

> cd Developpez-le-back-end-en-utilisant-Java-et-Spring/location

Install maven target:

> mvn clean install

Go inside folder:

> cd Developpez-le-back-end-en-utilisant-Java-et-Spring

Install dependencies:

> npm install

Launch Front-end:

> npm run start;

Launch Back-end with your IDE


## URL Swagger
http://localhost:3001/api/swagger-ui/index.html  
http://localhost:3001/api/v3/api-docs

