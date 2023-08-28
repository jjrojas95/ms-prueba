FROM node
WORKDIR /usr/src/app
COPY package*.json ./
RUN npm install
ENV NODE_TLS_REJECT_UNAUTHORIZED 0
COPY . .
EXPOSE 3000
CMD ["node", "index.js"]
