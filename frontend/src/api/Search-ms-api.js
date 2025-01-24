import axios from "axios";

const searchMsBaseURL = 'https://fullstack24-search.app.cloud.cbh.kth.se/api/search';

// Create an Axios instance
const api = axios.create({
    baseURL: searchMsBaseURL,
    headers: {
        'Content-Type': 'application/json',
    },
});

// Add a request interceptor to attach the token
api.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem("token"); // Replace with your token storage mechanism
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

// Define API calls using the `api` instance
export const searchDoctorsWithPatients = async (name) => {
    try {
        const response = await api.get(`/searchDoctors`, {
            params: { name },
        });
        return response.data;
    } catch (err) {
        console.error("Error searching doctors with patients:", err);
        throw err;
    }
};

export const searchPatientsByDiagnosis = async (diagnosis) => {
    try {
        const response = await api.get(`/patients`, {
            params: { diagnosis },
        });
        return response.data;
    } catch (err) {
        console.error("Error searching patients by diagnosis:", err);
        throw err;
    }
};
