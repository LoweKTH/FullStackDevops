const express = require('express');
const multer = require('multer');
const { uploadImage } = require('../service/ImageService');
const { retrieveImagesByUserId } = require('../service/ImageService');
const { editImage } = require('../service/ImageService');
const { verifyJWT, requireRole } = require('../utils/authentication');

const upload = multer({
    dest: 'uploads/',
    limits: {
        fileSize: 50 * 1024 * 1024 // 50mb
    }
});

const router = express.Router();


router.post('/upload', verifyJWT, requireRole('DOCTOR'), upload.single('image'), async (req, res) => {
    const {userId,title,description} = req.body;
    const uploadedFile = req.file;

    if (!uploadedFile) {
        return res.status(400).json({ message: 'No image file uploaded.' });
    }

    try {
        const imagePath = await uploadImage(uploadedFile, userId, title, description);
        res.status(200).json({ message: 'Image uploaded successfully', path: imagePath });
    } catch (error) {
        console.error(error);
        res.status(500).json({ message: 'Error uploading image.' });
    }
});

router.post('/edit', verifyJWT, requireRole('DOCTOR'), upload.single('image'),async (req, res) => {
    const { imageId } = req.body;
    const uploadedFile = req.file;
    console.log("Uploaded file field name:", req.file);
    if (!uploadedFile || !imageId) {
        return res.status(400).json({ message: 'No image data or image ID provided.' });
    }

    //const imagePath = await editImage(imageData,imageId);

    try {
        const imagePath = await editImage(uploadedFile,imageId);
        res.status(200).json({ message: 'Image uploaded successfully', path: imagePath });
    } catch (error) {
        console.error(error);
        res.status(500).json({ message: 'Error uploading image.' });
    }
});

router.get('/user/:userId',verifyJWT, async (req, res) => {
    const { userId } = req.params;

    try {
        const images = await retrieveImagesByUserId(userId);

        if (images.length === 0) {
            return res.status(404).json({ message: 'No images found for this user.' });
        }

        res.status(200).json(images);
    } catch (error) {
        console.error('Error retrieving images:', error);
        res.status(500).json({ message: error.message || 'Error retrieving images.' });
    }
});

module.exports = router;
