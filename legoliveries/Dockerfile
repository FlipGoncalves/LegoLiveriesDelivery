FROM node:18 as build
WORKDIR /app
COPY . /app
ARG API_HOSTNAME=172.20.202.10
RUN sed -i "s/localhost/${API_HOSTNAME}/g" /app/src/App.js
RUN npm install
RUN npm run build
ENTRYPOINT [ "npm", "start" ]