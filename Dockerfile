from tomcat:8.0

add ./webapp/target/*.war /usr/local/tomcat/webapps/

expose 8080

cmd["catalina.sh", "run"]