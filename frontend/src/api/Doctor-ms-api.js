import axios from "axios";

const doctormsBaseURL = 'http://localhost:30005/api/doctors';

const api = axios.create({
    baseURL: doctormsBaseURL,
});

export const fetchAssignedPatients = async (doctorId) => {
    return api.get(`/${doctorId}/patients`);


};


