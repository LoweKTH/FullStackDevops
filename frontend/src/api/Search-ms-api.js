import axios from "axios";

const searchmsBaseURL = "http://localhost:8085/api/search";

const api = axios.create({
    baseURL: searchmsBaseURL,
});

export const searchPatients = async ({ query, filterType }) => {
    return await api.get(`/patients`, {
        params: { query, filter: filterType },
    });
};

export default api;
