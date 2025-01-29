const request = require('supertest');
const express = require('express');
const multer = require('multer');
const { uploadImage } = require('../service/ImageService');
const imageController = require('../controller/ImageController');

const app = express();
app.use(express.json());
app.use('/api/image', imageController);

// Mocking the authentication and authorization middlewares
jest.mock('../utils/authentication', () => ({
    verifyJWT: (req, res, next) => next(), // Skip actual JWT verification
    requireRole: (role) => (req, res, next) => next(), // Skip role-based authorization
}));

jest.mock('../service/ImageService', () => ({
    uploadImage: jest.fn(),
}));

describe('POST /api/image/upload', () => {
    it('should upload an image successfully', async () => {
        const mockImagePath = '/uploads/mock-image.png';
        uploadImage.mockResolvedValue(mockImagePath);

        const formData = {
            userId: 1,
            title: 'Test Image',
            description: 'A test image for testing.',
        };

        const imageBuffer = Buffer.from('mock-image-buffer');

        const response = await request(app)
            .post('/api/image/upload')
            .field('userId', formData.userId)
            .field('title', formData.title)
            .field('description', formData.description)
            .attach('image', imageBuffer, 'test-image.png');

        expect(response.status).toBe(200);
        expect(response.body.message).toBe('Image uploaded successfully');
        expect(response.body.path).toBe(mockImagePath);
        expect(uploadImage).toHaveBeenCalledTimes(1);
    });

    it('should return an error if no image file is uploaded', async () => {
        const formData = {
            userId: 1,
            title: 'Test Image',
            description: 'A test image for testing.',
        };

        const response = await request(app)
            .post('/api/image/upload')
            .field('userId', formData.userId)
            .field('title', formData.title)
            .field('description', formData.description);

        expect(response.status).toBe(400);
        expect(response.body.message).toBe('No image file uploaded.');
    });
});
