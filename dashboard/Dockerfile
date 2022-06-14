FROM node:18 as build
WORKDIR /app
COPY . /app
ARG API_HOSTNAME=172.20.200.10
RUN sed -i "s/localhost/${API_HOSTNAME}/g" /app/src/App.js /app/src/management.js /app/src/signin.js /app/src/signup.js
RUN npm install
RUN npm run build