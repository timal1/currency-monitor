ARG BUILD_HOME=/gradle-docker-example
ARG BUILD_HOME
ENV APP_HOME=$BUILD_HOME
WORKDIR $APP_HOME
COPY --chown=gradle:gradle build.gradle settings.gradle $APP_HOME/
COPY --chown=gradle:gradle src $APP_HOME/src
COPY --chown=gradle:gradle config $APP_HOME/config
RUN gradle --no-daemon build
FROM adoptopenjdk/openjdk11:alpine-jre
ARG BUILD_HOME
ENV APP_HOME=$BUILD_HOME
COPY --from=build-image $APP_HOME/build/libs/gradle-docker-example.jar app.jar
ENTRYPOINT java -jar app.jar


