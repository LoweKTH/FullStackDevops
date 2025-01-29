import axios from "axios";
import keycloak from "../keycloak";

const doctormsBaseURL = 'https://fullstack24-doctorstaff.app.cloud.cbh.kth.se/api/doctors';


const api = axios.create({
    baseURL: doctormsBaseURL,
    headers: {
        'Content-Type': 'application/json',
    },
});


api.interceptors.request.use(
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


export const fetchAssignedPatients = async (doctorId) => {
    try {
        const response = await api.get(`/${doctorId}/patients`);
        return response;
    } catch (error) {
        console.error('Error fetching assigned patients:', error);
        throw error;
    }
};

export const addDoctor = async (doctorDto) => {
    try {
        const response = await api.post('/addDoctor', doctorDto);
        return response;
    } catch (error) {
        console.error('Error adding doctor:', error);
        throw error;
    }
};
