import axios from "axios";
import keycloak from "../keycloak";

const searchMsBaseURL = 'https://fullstack24-search.app.cloud.cbh.kth.se/api/search';

const api = axios.create({
    baseURL: searchMsBaseURL,
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


export const searchPatientsByName = async (name) => {
    try {
        const response = await api.get(`/patients/name`, {
            params: { name },
        });
        return response.data;
    } catch (err) {
        console.error("Error searching patients by name:", err);
        throw err;
    }
};
