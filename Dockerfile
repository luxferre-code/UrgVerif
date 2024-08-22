FROM debian:latest AS build

RUN apt-get update && \
    apt-get install -y --no-install-recommends openjdk-17-jdk findutils && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY ../../Documents/UrgVerif /app

RUN javac -encoding UTF-8 -cp "libs/*" -d /out $(find WEB-INF/src -name "*.java")

FROM tomcat:latest

RUN rm -rf /usr/local/tomcat/webapps/*

COPY --from=build /app/WEB-INF/views /usr/local/tomcat/webapps/ROOT/WEB-INF/views
COPY --from=build /app/css /usr/local/tomcat/webapps/ROOT/css
COPY --from=build /app/js /usr/local/tomcat/webapps/ROOT/js
COPY --from=build /app/index.jsp /usr/local/tomcat/webapps/ROOT/index.jsp
COPY --from=build /app/install.sql /usr/local/tomcat/webapps/ROOT/install.sql
COPY --from=build /out /usr/local/tomcat/webapps/ROOT/WEB-INF/classes
COPY --from=build /app/META-INF /usr/local/tomcat/webapps/ROOT/META-INF
COPY --from=build /app/libs /usr/local/tomcat/lib

EXPOSE 8080

# Démarrer Tomcat
CMD ["catalina.sh", "run"]