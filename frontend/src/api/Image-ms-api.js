import axios from "axios";

const api = axios.create({
    baseURL: "http://localhost:3002/api/image",
});

export const uploadImage = async (formData) => {

    return api.post("/upload", formData, {
        headers: { "Content-Type": "multipart/form-data" },
    }).then(response => response.data.path)
        .catch(() => { throw new Error("Error uploading image."); });
};

export const retrieveImagesByUserId = async (userId) => {
    try {
        const response = await api.get(`/user/${userId}`);
        return response.data;
    } catch (error) {
        console.error("Error retrieving images:", error);
        throw new Error("Error retrieving images.");
    }
};

export const updateImage = async (formData) => {
    return api.post("/edit", formData, {
        headers: { "Content-Type": "multipart/form-data" },
    }).then(response => response.data.path)
        .catch(() => { throw new Error("Error uploading image."); });
};
