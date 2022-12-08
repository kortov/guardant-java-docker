FROM openjdk:17-jdk-alpine
#FROM bellsoft/liberica-openjdk-debian:17.0.5

RUN apk add --no-cache bash coreutils grep sed gcompat
#RUN ln -s /lib/libc.musl-x86_64.so.1 /lib/ld-linux-x86-64.so.2

ARG JAR_FILE

COPY target/${JAR_FILE} /opt/app/app.jar

WORKDIR /opt/app

ENTRYPOINT ["java","-server", "-XX:+CreateCoredumpOnCrash", "-XX:ErrorFile=/opt/error/err.log", "-jar", "app.jar"]