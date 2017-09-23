FROM ubuntu:15.04

RUN apt-get update && \
    apt-get upgrade -y && \
    apt-get install -y  software-properties-common && \
    add-apt-repository ppa:webupd8team/java -y && \
    apt-get update && \
    echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | /usr/bin/debconf-set-selections && \
    apt-get install -y oracle-java8-installer && \
    apt-get clean


RUN mkdir /my-datomic
ADD datomic /my-datomic
ADD datomic-backupper.jar  /root/.datomic-backupper/datomic-backupper.jar

# Define commonly used JAVA_HOME variable
ENV JAVA_HOME /usr/lib/jvm/java-8-oracle

ENV AWS_ACCESS_KEY_ID XXXXXXXXXXXXX
ENV AWS_SECRET_ACCESS_KEY XXXXXXXXXXXXX

EXPOSE 3000

# Define default command.
CMD exec java -server \
              -XX:+UseConcMarkSweepGC \
              -XX:+CMSParallelRemarkEnabled \
              -XX:+UseCMSInitiatingOccupancyOnly \
              -XX:CMSInitiatingOccupancyFraction=70 \
              -XX:+ScavengeBeforeFullGC \
              -XX:+CMSScavengeBeforeRemark \
              -jar /root/.datomic-backupper/datomic-backupper.jar