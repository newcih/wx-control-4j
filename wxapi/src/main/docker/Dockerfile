FROM java:8
MAINTAINER newcih newcih@foxmail.com
EXPOSE 80
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo 'Asia/Shanghai' >/etc/timezone
ADD wxapi-0.0.1.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]

# 创建容器命令，挂载日志目录
# docker run -d --name wechat_control_springboot -v /var/logs/program/wechat_control:/var/log/program/wechat_control -p 80:80 wechat_control_springboot/wxapi
