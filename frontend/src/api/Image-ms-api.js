import axios from "axios";
import keycloak from "../keycloak";


const imageApi = axios.create({
    baseURL: "https://fullstack24-imagenew.app.cloud.cbh.kth.se/api/image",
    headers: {
        "Content-Type": "application/json",
    },
    credentials: true,
});


imageApi.interceptors.request.use(
    (config) => {
        const token =  keycloak.token;
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


export const uploadImage = async (formData) => {
    try {
        const response = await imageApi.post("/upload", formData, {
            headers: { "Content-Type": "multipart/form-data" },
        });
        return response.data.path;
    } catch (error) {
        console.error("Error uploading image:", error);
        throw new Error("Error uploading image.");
    }
};

export const retrieveImagesByUserId = async (userId) => {
    try {
        const response = await imageApi.get(`/user/${userId}`);
        return response.data;
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
        return response.data.path;
    } catch (error) {
        console.error("Error editing image:", error);
        throw new Error("Error editing image.");
    }
};
