import axios from "axios";
import keycloak from "../keycloak";

const realmId = "PatientSystem";
const keycloakBaseURL = 'http://localhost:8085';

const api = axios.create({
    baseURL: keycloakBaseURL,
});

// Fetch assigned patients
export const fetchUserRole = async () => {
    try {
        const adminToken = await getAdminToken();  // Get the admin token

        const userId = keycloak.tokenParsed?.sub;  // Get the logged-in user ID (doctor ID)
        const clientId = "user-ms";  // Replace with your Keycloak client ID

        // Use the admin token to make an authenticated request to the admin API
        const response = await api.get(`/auth/admin/realms/${realmId}/users/${userId}/role-mappings/clients/${clientId}`, {
            headers: {
                Authorization: `Bearer ${adminToken}`  // Pass the admin token in the Authorization header
            }
        });

        return response.data;  // Return the user roles

    } catch (error) {
        console.error("Failed to fetch user role", error);
        throw error;
    }
};

const getAdminToken = async () => {
    try {
        const response = await axios.post(
            `http://localhost:8085/realms/${realmId}/protocol/openid-connect/token`,
            new URLSearchParams({
                client_id: 'user-ms', // Your client ID
                client_secret: 'rUBEOSf1G7rgCflAIaXVypNUA53zkhnX', // Your client secret
                grant_type: 'client_credentials'
            }),
            { headers: { 'Content-Type': 'application/x-www-form-urlencoded' } }
        );
        return response.data.access_token;
    } catch (error) {
        console.error("Failed to fetch admin token", error);
        throw error;
    }
};

