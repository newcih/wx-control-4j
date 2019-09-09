FROM java:8
MAINTAINER newcih newcih@foxmail.com
EXPOSE 8080
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo 'Asia/Shanghai' >/etc/timezone
ADD target/wxapi-0.0.1.jar /app/wxapi-0.0.1.jar
ENTRYPOINT ["/usr/bin/java", "-jar", "/app/wxapi-0.0.1.jar"]
