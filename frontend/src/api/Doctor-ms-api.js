import axios from "axios";

const doctormsBaseURL = 'http://localhost:8083/api/doctors';

const api = axios.create({
    baseURL: doctormsBaseURL,
});

export const fetchAssignedPatients = async (doctorId) => {
    return api.get(`/${doctorId}/patients`);


};


