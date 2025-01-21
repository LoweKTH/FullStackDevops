const sharp = require('sharp');
const path = require('path');
const fs = require('fs');
const Image  = require('../model/Image')
console.log('Image Model:', Image);

const uploadImage = async (uploadedFile, userId, title, description) => {
    try {
        console.log('Uploaded file:', uploadedFile);
        console.log('User ID:', userId);

        // Process image to buffer using sharp
        const imageBuffer = await sharp(uploadedFile.path)
            .toFormat('png')
            .toBuffer();

        // Remove the temporary file
        fs.unlinkSync(uploadedFile.path);

        console.log('Creating image...');
        const image = await Image.create({
            userId,
            title,
            description,
            imageBlob: imageBuffer, // Store the buffer in the database
        });

        console.log('Image metadata saved:', image.toJSON());

        return 'Image uploaded successfully';
    } catch (error) {
        console.error('Error during image processing:', error);
        throw new Error('Error processing image');
    }
};


const editImage = async (uploadedFile, imageId) => {
    try {
        // Process the new image to a buffer
        const imageBuffer = await sharp(uploadedFile.path)
            .toFormat('png')
            .toBuffer();

        // Remove the temporary file
        fs.unlinkSync(uploadedFile.path);

        // Update the image record in the database
        const updatedImage = await Image.update(
            { imageBlob: imageBuffer },
            { where: { id: imageId } }
        );

        console.log('Image updated successfully:', updatedImage);
        return 'Image updated successfully';
    } catch (error) {
        console.error('Error during image editing:', error);
        throw new Error('Error editing image');
    }
};


const retrieveImagesByUserId = async (userId) => {
    try {
        const images = await Image.findAll({
            where: { userId },
            attributes: ['id', 'title', 'description', 'imageBlob', 'uploadedAt'],
        });

        if (!images || images.length === 0) {
            return [];
        }

        return images.map((image) => ({
            id: image.id,
            title: image.title,
            description: image.description,
            imageBlob: image.imageBlob.toString('base64'), // Convert to base64 for easier transmission
            uploadedAt: image.uploadedAt,
        }));
    } catch (error) {
        console.error('Error retrieving images by userId:', error);
        throw new Error('Error retrieving images');
    }
};


module.exports = { uploadImage, retrieveImagesByUserId, editImage };