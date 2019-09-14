FROM java:8
MAINTAINER newcih newcih@foxmail.com
EXPOSE 8080
VOLUME /tmp
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo 'Asia/Shanghai' >/etc/timezone
ADD wxapi-0.0.1.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
