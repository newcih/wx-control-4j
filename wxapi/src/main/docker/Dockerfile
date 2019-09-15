FROM java:8
MAINTAINER newcih newcih@foxmail.com
EXPOSE 80
VOLUME /var/logs/wechat_control /var/log/program/wechat_control
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo 'Asia/Shanghai' >/etc/timezone
ADD wxapi-0.0.1.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
