cd /data/src/hospital-parent/
git pull
mvn clean package
/data/tomcat/bin/shutdown.sh
sleep 1
rm -rf /data/tomcat/webapps/hospital-oa
\cp -rf /data/src/hospital-parent/hospital-oa/target/hospital-oa.war /data/tomcat/webapps/
/data/tomcat/bin/startup.sh