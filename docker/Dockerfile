FROM adoptopenjdk/maven-openjdk11-openj9:latest
WORKDIR /service/
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
ENV LANG C.UTF-8
ARG JAR_FILE
ADD ${JAR_FILE} /service/app.jar
ENTRYPOINT   java -jar /service/app.jar