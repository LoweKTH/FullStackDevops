import axios from "axios";
import keycloak from "../keycloak";

const patientmsBaseURL = 'https://fullstack24-patient.app.cloud.cbh.kth.se/api/patients';

const api = axios.create({
    baseURL: patientmsBaseURL,
    headers: {
        'Content-Type': 'application/json',
    },
});


api.interceptors.request.use(
    (config) => {
        const token = keycloak.token;
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

export const fetchPatients = async () => {
    try {
        const response = await api.get('/');
        return response;
    } catch (error) {
        console.error('Error fetching patients:', error);
        throw error;
    }
};

export const createNote = async (userId, noteData) => {
    try {
        const response = await api.post(`/${userId}/notes`, noteData);
        return response;
    } catch (error) {
        console.error('Error creating note:', error);
        throw error;
    }
};

export const addDiagnosis = async (patientId, diagnosisData) => {
    try {
        const response = await api.post(`/${patientId}/diagnosis`, diagnosisData);
        return response;
    } catch (error) {
        console.error('Error adding diagnosis:', error);
        throw error;
    }
};

export const fetchNotesForPatient = async (patientId) => {
    try {
        const response = await api.get(`/${patientId}/getnotes`);
        return response;
    } catch (error) {
        console.error('Error fetching notes:', error);
        throw error;
    }
};

export const fetchDiagnosesForPatient = async (patientId) => {
    try {
        const response = await api.get(`/${patientId}/getdiagnoses`);
        return response;
    } catch (error) {
        console.error('Error fetching diagnoses:', error);
        throw error;
    }
};

export const fetchAssignedDoctorStaff = async (patientId) => {
    try {
        const response = await api.get(`/${patientId}/doctorstaff`);
        return response;
    } catch (error) {
        console.error('Error fetching assigned doctor/staff:', error);
        throw error;
    }
};

export const fetchUserProfile = async (userId) => {
    try {
        const response = await api.get(`/${userId}`);
        return response;
    } catch (error) {
        console.error('Error fetching user profile:', error);
        throw error;
    }
};

export const addPatient = async (patientDto) => {
    try {
        const response = await api.post('/addPatient', patientDto);
        return response;
    } catch (error) {
        console.error('Error adding patient:', error);
        throw error;
    }
};
