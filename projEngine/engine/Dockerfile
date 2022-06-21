FROM openjdk:11 as build
WORKDIR /app
COPY mvnw .
RUN chmod +x mvnw
COPY .mvn .mvn
COPY pom.xml .
RUN ./mvnw dependency:go-offline
COPY src src
RUN ./mvnw package -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM openjdk:11
COPY wait.sh ./wait.sh
RUN "chmod" "u+x" "./wait.sh"
ARG DEPENDENCY=/app/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
#ENTRYPOINT [ "java", "-cp" , "app:app/lib/*", "Project.TwitterBot.TwitterBotApp", "cats"]
ENTRYPOINT [ "./wait.sh" ]
