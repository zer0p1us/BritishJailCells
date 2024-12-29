# Client

Third year Server-Centric Cloud computing coursework.

This will be a client to interact with the BritishJailCells orchestrator

## Tooling

- **Python 3.12+** for language
- **Flask** for routing
- **Bootstrap** for UI
- **Pydantic** for json data serialisation & deserialisation
- **Requests** for api calls
- **Poetry** for dependency management
- **Jinja2** for templating

## Running

**Install Python**: Install any version of python 3.12+

**Install Poetry**: Follow the latest [instructions](https://python-poetry.org/docs/#installing)

Linux (including WSL) and MacOS bash run the following:
```bash
curl -sSL https://install.python-poetry.org | python3 -
```

Windows Powershell run the following:
```powershell
(Invoke-WebRequest -Uri https://install.python-poetry.org -UseBasicParsing).Content | py -
   ```

**Install dependencies**: with `Poetry` installed, run the following to get install required dependencies

On windows run following:
```powershell
.\setup.bat
```
On Linux/MacOS run the following:
```bash
./setup.sh
```
**Create Config**: Create a new config file like the `mock-config.properties` file called `config.properties`, fill in all the fields to point to the `Orchestrator`

        eg. ip={ip-only} -> ip=localhost

**Run the client**: to run the client run the following:

```bash
poetry run python src/App.py
```