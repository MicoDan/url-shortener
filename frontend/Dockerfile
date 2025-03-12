# Build stage
FROM node:18-alpine AS build

# Set working directory
WORKDIR /app

# Install dependencies
COPY package.json package-lock.json ./
RUN npm install

# Build application
COPY . .
RUN npm run build

# Runtime stage with Nginx
FROM nginx:stable-alpine

# Copy built files to Nginx
COPY --from=build /app/build /usr/share/nginx/html

# Expose web server port
EXPOSE 80

# Start Nginx server
CMD ["nginx", "-g", "daemon off;"]
