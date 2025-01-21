const sharp = require('sharp');
const path = require('path');
const fs = require('fs');
const Image  = require('../model/Image')
console.log('Image Model:', Image);
const db = require('../utils/db');

const uploadImage = async (file, userId, title, description) => {
    try {
        const imageBuffer = fs.readFileSync(file.path);

        const image = await Image.create({
            userId,
            title,
            description,
            imageBlob: imageBuffer,
        });

        fs.unlinkSync(file.path); // Clean up the uploaded file from the server

        return image;
    } catch (error) {
        console.error("Error saving image:", error);
        throw new Error("Failed to upload image.");
    }
};

const editImage = async (uploadedFile, imageId, title, description) => {
    try {
        // Process the new image to a buffer (ensure it's a PNG)
        const imageBuffer = await sharp(uploadedFile.path)
            .toFormat('png')
            .toBuffer();

        // Remove the temporary uploaded file
        fs.unlinkSync(uploadedFile.path);

        // Find the image record to check if it exists
        const existingImage = await Image.findByPk(imageId);
        if (!existingImage) {
            throw new Error('Image not found.');
        }

        // Update the image metadata and/or the image blob in the database
        const updatedImage = await existingImage.update({
            imageBlob: imageBuffer, // Update the image blob
            title: title || existingImage.title, // Update title if provided
            description: description || existingImage.description, // Update description if provided
        });

        console.log('Image updated successfully:', updatedImage);

        // Return the updated image metadata (including image ID, title, description, and path)
        return updatedImage;
    } catch (error) {
        console.error('Error during image editing:', error);
        throw new Error('Error editing image');
    }
};



const retrieveImagesByUserId = async (userId) => {
    try {
        const images = await Image.findAll({
            where: { userId },
            attributes: ['id', 'title', 'description', 'uploadedAt', 'imageBlob'],
        });

        return images.map((image) => ({
            id: image.id,
            title: image.title,
            description: image.description,
            uploadedAt: image.uploadedAt,
            imageBlob: image.imageBlob.toString('base64'), // Convert BLOB to base64
        }));
    } catch (error) {
        console.error("Error retrieving images:", error);
        throw new Error("Failed to fetch images.");
    }
};




module.exports = { uploadImage, retrieveImagesByUserId, editImage };