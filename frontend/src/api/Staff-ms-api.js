import axios from "axios";

const staffmsBaseUrl = 'https://fullstack24-doctorstaff.app.cloud.cbh.kth.se';

const api = axios.create({
    baseURL: staffmsBaseUrl,
});


export const addStaff = async (staffDto, token) => {
    try {
        const response = await api.post('/addStaff', staffDto, {
            headers: {
                'Authorization': `Bearer ${token}`,  // Include the Keycloak token for authentication
                'Content-Type': 'application/json',
            }
        });
        return response.data;  // Return the response data (e.g., success message, patient details)
    } catch (error) {
        console.error('Error adding staff:', error);
        throw error;  // Rethrow the error for further handling
    }
};
