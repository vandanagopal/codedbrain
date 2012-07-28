#!/bin/sh
mvn clean install
rm -rf /home/vandanag/ProgramFiles/apache-tomcat-7.0.25/webapps/maze.war /home/vandanag/ProgramFiles/apache-tomcat-7.0.25/webapps/maze
cp target/maze.war /home/vandanag/ProgramFiles/apache-tomcat-7.0.25/webapps/maze.war
