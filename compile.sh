#!/bin/bash
docker run -it --rm --name my-maven-project -v "$PWD/csv-upload":/usr/src/app -w /usr/src/app maven mvn clean install
