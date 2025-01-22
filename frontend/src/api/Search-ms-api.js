import axios from "axios";

const searchMsBaseURL = 'https://fullstack24-search.app.cloud.cbh.kth.se/api/search';

const api = axios.create({
    baseURL: searchMsBaseURL,
});


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