# guardant-java-docker

Minimal example to show jvm crash with guardant. Guardant Ticket#1079556

# requirements

java 17
maven
docker
docker-compose

# build

`mvn clean package`

# run

`docker-compose up --build`

error file will be in .docker/error
core dump will be in .docker/dump

if dump didn't create uncomment entrypoint,stdin_open,tty in docker-compose and comment 'command' section. Then after
container start connect to bash and run `sysctl -w kernel.core_pattern=/opt/app/core/core-%e.%p.%h.%t.dmp` and run app
as `java -server -XX:+CreateCoredumpOnCrash -XX:ErrorFile=/opt/error/err.log -jar app.jar "publicVendorCode" "privateReadCode" "featureNumber" "publicFeatureKey"` 