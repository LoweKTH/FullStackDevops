const express = require('express');
const multer = require('multer');
const { uploadImage, retrieveImagesByUserId, editImage } = require('../service/ImageService');


const upload = multer({
    dest: 'uploads/',
    limits: {
        fileSize: 50 * 1024 * 1024, // 50mb
    },
});

const router = express.Router();

// Upload a new image
router.post('/upload', upload.single('image'), async (req, res) => {
    const { userId, title, description } = req.body;
    const uploadedFile = req.file;

    if (!uploadedFile) {
        return res.status(400).json({ message: 'No image file uploaded.' });
    }

    try {
        // Call the uploadImage function to process and store the image as a blob
        await uploadImage(uploadedFile, userId, title, description);

        res.status(200).json({ message: 'Image uploaded successfully' });
    } catch (error) {
        console.error('Error uploading image:', error);
        res.status(500).json({ message: 'Error uploading image.' });
    }
});

// Edit an existing image
router.post('/edit', upload.single('image'), async (req, res) => {
    const { imageId } = req.body;
    const uploadedFile = req.file;

    if (!uploadedFile || !imageId) {
        return res.status(400).json({ message: 'No image file or image ID provided.' });
    }

    try {
        // Call the editImage function to update the blob in the database
        await editImage(uploadedFile, imageId);

        res.status(200).json({ message: 'Image updated successfully' });
    } catch (error) {
        console.error('Error editing image:', error);
        res.status(500).json({ message: 'Error editing image.' });
    }
});

// Retrieve all images for a user
router.get('/user/:userId', async (req, res) => {
    const { userId } = req.params;

    try {
        const images = await retrieveImagesByUserId(userId);

        if (images.length === 0) {
            return res.status(404).json({ message: 'No images found for this user.' });
        }

        // Respond with the retrieved images
        res.status(200).json(images);
    } catch (error) {
        console.error('Error retrieving images:', error);
        res.status(500).json({ message: error.message || 'Error retrieving images.' });
    }
});

module.exports = router;
