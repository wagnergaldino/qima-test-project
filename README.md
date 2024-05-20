# qima-test-project

Notes:

- Schema created using JPA and H2 Database based on Entity classes
- Some categories are inserted in the database at the application start up. There are no pages for managing categories.
- Spring Core, JPA/ORM and Spring MVC were used as Requested.
- Thymeleaf was used as a template engine in the Front End.
- Sortering, Filtering and Paging is fully implemented
- Spring Security was used to create the requested Super User with a role that authorizes full access to the application.
- Username, encriptyed password and roles are stored in the H2 database using Basic Authentication
- There are 2 registered users: Regular (user: regular – password: regular) and Super (user: super – password: super)
- The resource Hello ( http://localhost:8080/hello ) is available for everyone
- The resources H2 ( http://localhost:8080/h2 ) and Products ( http://localhost:8080/products ) are only available for the Super User
- In the front end, forms are validated using “required” attribute from Bootstrap in Thymeleaf templates
- In the back end, entity classes are configured using Bean Validation Annotations
