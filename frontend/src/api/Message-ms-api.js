import axios from "axios";

const messagingBaseURL = 'https://fullstack24-messaging.app.cloud.cbh.kth.se/api/messages'; // Update this to your messaging API URL

// Create an Axios instance
const api = axios.create({
    baseURL: messagingBaseURL,
    headers: {
        'Content-Type': 'application/json',
    },
});

// Add a request interceptor to attach the token
api.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem("token"); // Replace with your token storage mechanism
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    (error) => {
        console.error("Error in request interceptor:", error);
        return Promise.reject(error);
    }
);

// Define API calls using the `api` instance
export const fetchMessages = async (conversationId) => {
    try {
        const response = await api.get(`/${conversationId}`);
        return response; // Return the response data (e.g., messages)
    } catch (error) {
        console.error("Error fetching messages:", error);
        throw error; // Rethrow the error for further handling
    }
};

export const sendMessage = async (conversationId, senderId, message) => {
    try {
        const response = await api.post(`/${conversationId}/send`, {
            senderId,
            content: message,
        });
        return response; // Return the response data (e.g., success message)
    } catch (error) {
        console.error("Error sending message:", error);
        throw error; // Rethrow the error for further handling
    }
};

export const createConversation = async (senderId, recipientId) => {
    try {
        const response = await api.post(`/conversation`, null, {
            params: { senderId, recipientId }, // Send params via the query string
        });
        return response; // Return the response data (e.g., conversation details)
    } catch (error) {
        console.error("Error creating conversation:", error);
        throw error; // Rethrow the error for further handling
    }
};

export const fetchConversations = async (userId) => {
    try {
        const response = await api.get("/getconversations", {
            params: { userId },
        });
        return response; // Return the response data (e.g., list of conversations)
    } catch (error) {
        console.error("Error fetching conversations:", error);
        throw error; // Rethrow the error for further handling
    }
};
