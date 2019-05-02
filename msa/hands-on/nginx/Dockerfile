FROM ktis-bastion01.container.ipc.kt.com:5000/admin/nginx:latest

LABEL owner=mmp-project-team
ENV TZ Asia/Seoul

COPY src/main/resources/static /usr/share/nginx/html
#RUN echo "daemon off;" >> /etc/nginx/nginx.conf
