# Orchestrator

Third year Server-Centric Cloud computing coursework.

This will be a orchestrator to manage all services.

## Tooling

- **NetBeans 23**: for Orchestrator development
- **Tomcat 9.0.96**: for Orchestrator deployment
- **JDK 21**: Java development kit
- **Javax EE 8**: Orchestrator environment
- **MSSQL & TSQL**: for database

## Running

**Create the database**: Create a MSSQL database and run following tsql in the given order to create table, populate them and add the necessary views and stored procedures:
- `sql/transact-sql/create.sql`
- `sql/transact-sql/populate-db.sql`
- `sql/transact-sql/views/vw_room_details.sql`
- `sql/transact-sql/procedures/sp_search_room_details.sql`
- `sql/transact-sql/procedures/sp_apply_for_room.sql`
- `sql/transact-sql/procedures/sp_get_room_history.sql`
- `sql/transact-sql/procedures/sp_update_application_status.sql`


in that order to create the required table and fill them with some data

**Config**: Create a new config file like the `mock-config.properties` file called `config.properties`, fill in all the fields to point to the database

        eg. db.user={username}@{sql_server} -> db.user=username123@server123

**Build Orchestrator**: build the Orchestrator in `netbeans` to produce a `dist/BritishJailCells.war` file for the tomcat server

**Run Orchestrator**: upload the `BritishJailCells.war` to the tomcat server, if you've added tomcat to `netbeans` it will automatically do this for you
