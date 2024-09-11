FROM openjdk:17
ENV PORT 6000
EXPOSE 6000
ENTRYPOINT ["java","-jar","springboot-roundrobin.jar"]
WORKDIR /usrapp/bin
COPY /target/classes /usrapp/bin/classes
COPY /target/dependency /usrapp/bin/dependency
ADD target/springboot-roundrobin.jar /usrapp/bin/springboot-roundrobin.jar

CMD ["java","-cp","./classes:./dependency/*","co.edu.escuelaing.app.roundrobin.RoundrobinApplication"]
