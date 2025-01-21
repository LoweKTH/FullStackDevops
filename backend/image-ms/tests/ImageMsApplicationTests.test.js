const request = require('supertest');
const express = require('express');
const multer = require('multer');
const path = require('path');
const { uploadImage, editImage } = require('../service/ImageService');
const imageController = require('../controller/ImageController');

const app = express();
app.use(express.json());
app.use('/api/image', imageController);  // Import your image controller

// Mocking the uploadImage and editImage functions
jest.mock('../service/ImageService', () => ({
    uploadImage: jest.fn(),
    editImage: jest.fn(),
}));

describe('POST /api/image/upload', () => {
    it('should upload an image successfully', async () => {
        // Setup mock response
        const mockImagePath = '/uploads/mock-image.png';
        uploadImage.mockResolvedValue(mockImagePath);  // Mock successful image upload

        const formData = {
            userId: 1,
            title: 'Test Image',
            description: 'A test image for testing.',
        };

        const imageBuffer = Buffer.from('mock-image-buffer');  // Mock image buffer

        // Send POST request with a mock image
        const response = await request(app)
            .post('/api/image/upload')
            .field('userId', formData.userId)
            .field('title', formData.title)
            .field('description', formData.description)
            .attach('image', imageBuffer, 'test-image.png');

        // Assert success response
        expect(response.status).toBe(200);
        expect(response.body.message).toBe('Image uploaded successfully');
        expect(response.body.path).toBe(mockImagePath);
        expect(uploadImage).toHaveBeenCalledTimes(1);  // Ensure the uploadImage function was called
    });

    it('should return an error if no image file is uploaded', async () => {
        const formData = {
            userId: 1,
            title: 'Test Image',
            description: 'A test image for testing.',
        };

        // Send POST request without an image
        const response = await request(app)
            .post('/api/image/upload')
            .field('userId', formData.userId)
            .field('title', formData.title)
            .field('description', formData.description);

        // Assert error response
        expect(response.status).toBe(400);
        expect(response.body.message).toBe('No image file uploaded.');
    });
});

describe('POST /api/image/edit', () => {
    it('should update an image successfully', async () => {
        // Setup mock response
        const mockImageId = 1;
        const mockUpdatedImage = {
            id: mockImageId,
            title: 'Updated Test Image',
            description: 'Updated test image for editing.',
            imageBlob: 'mocked-buffer-data',  // Simulate updated image buffer
        };

        editImage.mockResolvedValue(mockUpdatedImage);  // Mock successful image edit

        const formData = {
            imageId: mockImageId,
            title: 'Updated Test Image',
            description: 'Updated test image for editing.',
        };

        const imageBuffer = Buffer.from('mock-image-buffer');  // Mock image buffer

        // Send POST request to edit the image
        const response = await request(app)
            .post('/api/image/edit')
            .field('imageId', formData.imageId)
            .field('title', formData.title)
            .field('description', formData.description)
            .attach('image', imageBuffer, 'updated-test-image.png');

        // Assert success response
        expect(response.status).toBe(200);
        expect(response.body.message).toBe('Image updated successfully');
        expect(response.body.image.id).toBe(mockUpdatedImage.id);
        expect(response.body.image.title).toBe(mockUpdatedImage.title);
        expect(response.body.image.description).toBe(mockUpdatedImage.description);
        expect(response.body.image.imageBlob).toBe(mockUpdatedImage.imageBlob);
        expect(editImage).toHaveBeenCalledTimes(1);  // Ensure the editImage function was called
    });

    it('should return an error if no image file is uploaded for editing', async () => {
        const formData = {
            imageId: 1,
            title: 'Updated Test Image',
            description: 'Updated test image for editing.',
        };

        // Send POST request to edit the image without an image
        const response = await request(app)
            .post('/api/image/edit')
            .field('imageId', formData.imageId)
            .field('title', formData.title)
            .field('description', formData.description);

        // Assert error response
        expect(response.status).toBe(400);
        expect(response.body.message).toBe('No image file uploaded.');
    });
});
