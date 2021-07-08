FROM openjdk

RUN mkdir /app

COPY out/production/DockerApiTestAutomation /app
#COPY lib /app/lib
ADD lib /app/lib

WORKDIR /app

#CMD "javac -d bin -sourcepath src -cp .:lib/junit-4.12.jar src/TestRunner.java"

#CMD java -cp .:bin/:lib/junit-4.13.2.jar:lib/hamcrest-core-1.3.jar Main
CMD java -cp .:bin/:lib/* Main