import axios from "axios";
import keycloak from "../keycloak";

const usermsBaseURL = 'http://localhost:30001/api/user';

const api = axios.create({
    baseURL: usermsBaseURL,
});

export const registerUser = async (userData) => {
    return await api.post('/register', userData);
};

export const loginUser = async (credentials) => {
    return await api.post('/login', credentials);
}

api.interceptors.request.use(
    (config) => {
        if (keycloak.token) {
            // Add the Keycloak token to the Authorization header
            config.headers.Authorization = `Bearer ${keycloak.token}`;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

export default api;

