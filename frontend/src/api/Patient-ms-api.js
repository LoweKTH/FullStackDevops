import axios from "axios";

const patientmsBaseURL = 'http://localhost:8082/api/patients';

const api = axios.create({
    baseURL: patientmsBaseURL,
});

export const fetchPatients = async () => {
    return api.get('/');
};

export const createNote = async (userId, noteData) =>{
    return await api.post(`/${userId}/notes`, noteData);
};

export const addDiagnosis = async (patientId, diagnosisData) => {
    return await api.post(`/${patientId}/diagnosis`, diagnosisData);
};

export const fetchNotesForPatient = async (patientId) => {
    return await api.get(`/${patientId}/getnotes`);
};

export const fetchDiagnosesForPatient = async (patientId) => {
    return await api.get(`/${patientId}/getdiagnoses`);
};

export const fetchAssignedDoctorStaff = async (patientId) => {
    return await api.get(`/${patientId}/doctorstaff`);
};

export const fetchUserProfile = async (userId) => {
    return await api.get(`/${userId}`);
}

export const addPatient = async (patientDto, token) => {
    try {
        const response = await api.post('/addPatient', patientDto, {
            headers: {
                'Authorization': `Bearer ${token}`,  // Include the Keycloak token for authentication
                'Content-Type': 'application/json',
            }
        });
        return response.data;  // Return the response data (e.g., success message, patient details)
    } catch (error) {
        console.error('Error adding patient:', error);
        throw error;  // Rethrow the error for further handling
    }
};



