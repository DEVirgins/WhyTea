FROM node:14-alpine
WORKDIR /src
ADD package.json /src
RUN npm i --silent
ADD . /src
RUN npm run build
CMD npm run start
EXPOSE 3000
#COPY ./build/ /usr/share/nginx/html
#EXPOSE 80
#CMD ["nginx", "-g", "daemon off;"]