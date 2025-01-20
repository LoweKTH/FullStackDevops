import axios from "axios";

const doctormsBaseURL = 'https://fullstack24-doctorstaff.app.cloud.cbh.kth.se';

// Create an Axios instance
const api = axios.create({
    baseURL: doctormsBaseURL,
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
export const fetchAssignedPatients = async (doctorId) => {
    try {
        const response = await api.get(`/${doctorId}/patients`);
        return response; // Return the response data (e.g., assigned patients)
    } catch (error) {
        console.error('Error fetching assigned patients:', error);
        throw error; // Rethrow the error for further handling
    }
};

export const addDoctor = async (doctorDto) => {
    try {
        const response = await api.post('/addDoctor', doctorDto);
        return response;  // Return the response data (e.g., success message, doctor details)
    } catch (error) {
        console.error('Error adding doctor:', error);
        throw error;  // Rethrow the error for further handling
    }
};
