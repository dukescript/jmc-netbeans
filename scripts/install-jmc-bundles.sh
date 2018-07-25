#!/bin/bash
# This script will require you to substitute the exact versions for your build 
# for now.
if [ ! -v JMC_HOME ]
then
    echo "Need to set JMC_HOME to the mercurial repo"
elif [ -z "$JMC_HOME" ]
then
    echo "Warning running with an empty JMC_HOME"
else
    echo "Installing the JMC jars" 
    if [ ! -v JMC_BUILD_DATE ]
    then
        echo "Need to set JMC_BUILD_DATE to the last part of the bundle names, for example 201807251342"
    else 
        echo "Using build date $JMC_BUILD_DATE"
        mvn install:install-file -Dfile=$JMC_HOME/target/repository/plugins/org.openjdk.jmc.common_7.0.0.$JMC_BUILD_DATE.jar -DgroupId=org.openjdk.jmc -DartifactId=common -Dversion=7.0.0-SNAPSHOT -Dpackaging=jar
        mvn install:install-file -Dfile=$JMC_HOME/target/repository/plugins/org.openjdk.jmc.flightrecorder_7.0.0.$JMC_BUILD_DATE.jar -DgroupId=org.openjdk.jmc -DartifactId=flightrecorder -Dversion=7.0.0-SNAPSHOT -Dpackaging=jar
        mvn install:install-file -Dfile=$JMC_HOME/target/repository/plugins/org.openjdk.jmc.flightrecorder.rules_7.0.0.$JMC_BUILD_DATE.jar -DgroupId=org.openjdk.jmc -DartifactId=flightrecorder.rules -Dversion=7.0.0-SNAPSHOT -Dpackaging=jar
        mvn install:install-file -Dfile=$JMC_HOME/target/repository/plugins/org.openjdk.jmc.flightrecorder.rules.jdk_7.0.0.$JMC_BUILD_DATE.jar -DgroupId=org.openjdk.jmc -DartifactId=flightrecorder.jdk -Dversion=7.0.0-SNAPSHOT -Dpackaging=jar
    fi
fi
