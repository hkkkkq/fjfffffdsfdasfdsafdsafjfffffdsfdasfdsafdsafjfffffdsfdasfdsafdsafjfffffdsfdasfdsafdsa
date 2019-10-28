FROM 172.16.51.166:5000/nginx:1.17.4-alpine

USER root
ENV TZ=Asia/Seoul
COPY dist /usr/share/nginx/html
COPY default.conf /etc/nginx/conf.d
RUN chmod -R 755 /usr/share/nginx/html
