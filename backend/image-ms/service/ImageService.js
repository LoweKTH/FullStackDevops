const sharp = require('sharp');
const path = require('path');
const fs = require('fs');
const Image  = require('../model/Image')
console.log('Image Model:', Image);

const uploadImage = async (uploadedFile, userId, title, description) => {
    const fileNameWithoutExtension = path.parse(uploadedFile.originalname).name;
    const filePath = path.join(__dirname, '../uploads', `${fileNameWithoutExtension}.png`);
    const relativePath = `/uploads/${fileNameWithoutExtension}.png`;

    console.log('Uploaded file:', uploadedFile);
    console.log('User ID:', userId);


    try {

        await sharp(uploadedFile.path)
            .toFormat('png')
            .toFile(filePath);


        fs.unlinkSync(uploadedFile.path);

        console.log('Creating image...');
        const image = await Image.create({
            userId,
            title,
            description,
            imagePath: relativePath,
        });

        console.log('Image metadata saved:', image.toJSON());


        return `/uploads/${fileNameWithoutExtension}.png`;
    } catch (error) {
        console.error('Error during image processing:', error);
        throw new Error('Error processing image');
    }
};

const retrieveImagesByUserId = async (userId) => {
    try {
        const images = await Image.findAll({
            where: { userId },
            attributes: ['id', 'title', 'description', 'imagePath', 'uploadedAt'],
        });


        if (!images || images.length === 0) {
            return [];
        }

        return images.map((image) => ({
            id: image.id,
            title: image.title,
            description: image.description,
            imagePath: image.imagePath,
            uploadedAt: image.uploadedAt,
        }));
    } catch (error) {
        console.error('Error retrieving images by userId:', error);
        throw new Error('Error retrieving images');
    }
};

module.exports = { uploadImage, retrieveImagesByUserId };