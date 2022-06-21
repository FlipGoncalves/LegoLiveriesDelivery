FROM node:18 as build
WORKDIR /app
COPY . /app
ARG API_HOSTNAME=192.168.160.227
RUN sed -i "s/localhost/${API_HOSTNAME}/g" /app/src/App.js /app/src/component/login.js /app/src/component/Register.js /app/src/component/ResetPassword.js /app/src/component/all_orders.js
RUN npm install
RUN npm run build
ENTRYPOINT [ "npm", "start" ]