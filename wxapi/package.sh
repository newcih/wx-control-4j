cd ..
mvn clean install -Dmaven.test.skip=true
cd wxapi
mvn clean package -Dmaven.test.skip=true -P $1
