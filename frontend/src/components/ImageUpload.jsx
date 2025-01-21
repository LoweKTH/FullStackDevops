import React, { useState } from 'react';
import { uploadImage } from "../api/Image-ms-api";
import "../styles/ImageUpload.css";
import { useLocation, useNavigate } from "react-router-dom";

const ImageUpload = () => {
    const [image, setImage] = useState(null);
    const navigate = useNavigate();
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [imagePreview, setImagePreview] = useState(null);
    const location = useLocation();
    const { userId } = location.state;
    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');

    const handleImageChange = (event) => {
        const selectedImage = event.target.files[0];
        setImage(selectedImage);

        if (selectedImage) {
            const reader = new FileReader();
            reader.onload = () => setImagePreview(reader.result); // Show preview as base64
            reader.readAsDataURL(selectedImage);
        }

    };

    const handleTitleChange = (event) => {
        setTitle(event.target.value);
    };

    const handleDescriptionChange = (event) => {
        setDescription(event.target.value);
    };

    const handleSubmit = async (event) => {
        event.preventDefault();

        if (!image) {
            alert("Please select an image first.");
            return;
        }
        
        if (!title || !description) {
            alert("Please provide a title and description.");
            return;
        }

        const formData = new FormData();
        formData.append("image", image);
        formData.append("userId", userId);
        formData.append("title", title);
        formData.append("description", description);

        try {
            setLoading(true);
            setError(null);

            await uploadImage(formData);

        } catch (err) {
            setError("Error uploading image.");
            console.error(err);
        } finally {
            setLoading(false);
            navigate(-1);
        }
    };

    return (
        <div className="image-upload-container">
            <h2>Upload an Image</h2>
            <form onSubmit={handleSubmit}>

                <div className="form-row">
                    <label htmlFor="title">Title:</label>
                    <input
                        id="title"
                        type="text"
                        placeholder="Enter title"
                        value={title}
                        onChange={handleTitleChange}
                    />
                </div>

                <div className="form-row">
                    <label htmlFor="description">Description:</label>
                    <textarea
                        id="description"
                        placeholder="Enter description"
                        value={description}
                        onChange={handleDescriptionChange}
                    />
                </div>


                <div className="form-row">
                    <label htmlFor="image">Choose File:</label>
                    <input
                        id="image"
                        type="file"
                        onChange={handleImageChange}
                    />
                </div>

                <button type="submit" disabled={loading}>
                    {loading ? "Uploading..." : "Upload Image"}
                </button>
            </form>

            {error && <p className="error-message">{error}</p>}


            {imagePreview && (
                <div className="image-preview">
                    <h3>Image Preview</h3>
                    <img
                        src={imagePreview}
                        alt="Preview"
                        className="image-preview-img"
                    />
                </div>
            )}
        </div>
    );
};

export default ImageUpload;
