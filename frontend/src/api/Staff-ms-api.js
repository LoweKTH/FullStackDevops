import axios from "axios";

const staffmsBaseUrl = 'https://fullstack24-doctorstaff.app.cloud.cbh.kth.se/api/staff';

const api = axios.create({
    baseURL: staffmsBaseUrl,
});


export const addStaff = async (staffDto, token) => {
    try {
        const response = await api.post('/addStaff', staffDto, {
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json',
            }
        });
        return response.data;
    } catch (error) {
        console.error('Error adding staff:', error);
        throw error;
    }
};
