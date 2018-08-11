# Aplatanao Cockpit

WIP prototype status: JavaFX based GraphQL endpoint explorer

## Requirements

Java 8 for JavaFX 2.*

Unfortunately a SNAPSHOT version of the upcoming GraphStream JavaFX wrapper, see https://github.com/dgf/gs-ui-javafx
You have to build and install the 0.0.1-SNAPSHOT locally.

    git clone https://github.com/dgf/gs-algo.git
    cd gs-algo
    git checkout 2.0-alpha
    mvn clean install

    cd ..

    git clone https://github.com/dgf/gs-ui-javafx.git
    cd gs-ui-javafx
    mvn clean install

## TODOs

- [x] implement Log TableView
- [ ] implement TableView level filter
- [ ] test graph layout algorithms