FROM node:latest as build-stage
WORKDIR /app

COPY package.json ./
COPY package-lock.json ./

RUN npm install
COPY . .
RUN npm run build

EXPOSE 3000
ENV HOST=0.0.0.0

CMD ["npm", "run", "dev"]