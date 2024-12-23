# British Jail Cells

Third year Server-Centric Cloud computing coursework.

British Jail Cells or BJC as it will referred to is a room booking service.

## Tooling

- **NetBeans 23**: for Orchestrator development
- **Tomcat 90**: for Orchestrator deployment
- **JDK 23**: Java development kit
- **Javax EE 8**: Orchestrator environment
<!-- **DB Browser for SQLite Version 3.13+**: Database tooling, creation, debugging -->

## Running

**Create the database**: Create a MSSQL database and run `transact-sql/create.sql` and `transact-sql/populate-db.sql` in that order to create the required table and fill them with some data

**Config**: Create a new config file like the `mock-config.properties` file, fill in all the fields

**Build Orchestrator**: build the Orchestrator in `netbeans` to produce a war file for the tomcat server
