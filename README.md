# Aplatanao Cockpit

WIP prototype status: JavaFX based GraphQL endpoint explorer

## Requirements

Java 8 for JavaFX 2.*

Unfortunately a SNAPSHOT version of the upcoming GraphStream JavaFX wrapper, see https://github.com/dgf/gs-ui-javafx
You have to build and install the 0.0.1-SNAPSHOT locally.

    git clone https://github.com/dgf/gs-ui-javafx.git
    cd gs-ui-javafx
    mvn clean install

## TODOs

- [ ] implement Log TableView with level filter
- [ ] test graph layout algorithms