import axios from "axios";

const patientmsBaseURL = 'http://localhost:8082/api/patients';

const api = axios.create({
    baseURL: patientmsBaseURL,
});

export const fetchPatients = async () => {
    return api.get('/');
};
