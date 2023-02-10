FROM node:14.21.2-slim

WORKDIR /src

EXPOSE 3000

CMD ["npm", "run", "serve"]

COPY package.json /src
RUN npm install

COPY . /src