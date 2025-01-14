import axios from "axios";

// Create an Axios instance for the image API
const imageApi = axios.create({
    baseURL: "http://localhost:3002/api/image",
    headers: {
        "Content-Type": "application/json",
    },
    credentials: true,
});

// Add a request interceptor to attach the token
imageApi.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem("token"); // Retrieve the token from localStorage or another storage mechanism
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    (error) => {
        console.error("Error in request interceptor:", error);
        return Promise.reject(error);
    }
);

// Define API calls using the `imageApi` instance
export const uploadImage = async (formData) => {
    try {
        const response = await imageApi.post("/upload", formData, {
            headers: { "Content-Type": "multipart/form-data" },
        });
        return response.data.path; // Return the image path
    } catch (error) {
        console.error("Error uploading image:", error);
        throw new Error("Error uploading image.");
    }
};

export const retrieveImagesByUserId = async (userId) => {
    try {
        const response = await imageApi.get(`/user/${userId}`);
        return response.data; // Return the list of images
    } catch (error) {
        console.error("Error retrieving images:", error);
        throw new Error("Error retrieving images.");
    }
};

export const updateImage = async (formData) => {
    try {
        const response = await imageApi.post("/edit", formData, {
            headers: { "Content-Type": "multipart/form-data" },
        });
        return response.data.path; // Return the updated image path
    } catch (error) {
        console.error("Error editing image:", error);
        throw new Error("Error editing image.");
    }
};
