import axios from "axios";

const searchMsBaseURL = "http://localhost:8085/api/search";

const api = axios.create({
    baseURL: searchMsBaseURL,
});

    export const searchDoctorsForPatient = async (patientId, doctorName) => {
    const response = await api.get(`/${patientId}`, {
        params: { name: doctorName },
    });
    return response.data;
    };

    export const fetchPatientsByDiagnosis = async (diagnosis) => {
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