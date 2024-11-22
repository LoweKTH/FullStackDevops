# Use the official Node.js image
FROM node:16-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the package files first to install dependencies
COPY package.json package-lock.json ./

# Install dependencies
RUN npm install

# Copy the rest of the application code
COPY . .

# Expose the port React uses in development mode
EXPOSE 3000

# Start the application in development mode
CMD ["npm", "start"]
