import axios from "axios";

const usermsBaseURL = 'http://localhost:8081/api/user';

const api = axios.create({
    baseURL: usermsBaseURL,
});

export const registerUser = async (userData) => {
    return await api.post('/register', userData);
};

export const loginUser = async (credentials) => {
    return await api.post('/login', credentials);
}
export default api;

