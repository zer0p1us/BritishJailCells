#!/bin/bash

echo Updating poetry lock...
poetry lock
echo Installing dependencies...
poetry install
echo Installing pre-commit hooks
poetry run pre-commit install