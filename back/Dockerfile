FROM openjdk:8-jdk
EXPOSE 8080
RUN mkdir /app
COPY ./build/install/backend/ /app/
WORKDIR /app/bin
ARG VK_SECRET
ARG GOOGLE_SECRET
ENV VK_SECRET=$VK_SECRET
ENV GOOGLE_SECRET=$GOOGLE_SECRET
CMD ["./backend"]