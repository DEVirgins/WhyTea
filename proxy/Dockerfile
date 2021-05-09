FROM nginx:1.14.0
RUN rm /etc/nginx/conf.d/default.conf
COPY ./reverse-proxy.conf /etc/nginx/conf.d/default.conf
EXPOSE 80