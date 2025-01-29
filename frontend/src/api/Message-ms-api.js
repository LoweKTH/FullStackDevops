import axios from "axios";
import keycloak from "../keycloak";

const messagingBaseURL = 'https://fullstack24-messaging.app.cloud.cbh.kth.se/api/messages';

const api = axios.create({
    baseURL: messagingBaseURL,
    headers: {
        'Content-Type': 'application/json',
    },
});


api.interceptors.request.use(
    (config) => {
        const token = keycloak.token;
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


export const fetchMessages = async (conversationId) => {
    try {
        const response = await api.get(`/${conversationId}`);
        return response;
    } catch (error) {
        console.error("Error fetching messages:", error);
        throw error;
    }
};

export const sendMessage = async (conversationId, senderId, message) => {
    try {
        const response = await api.post(`/${conversationId}/send`, {
            senderId,
            content: message,
        });
        return response;
    } catch (error) {
        console.error("Error sending message:", error);
        throw error;
    }
};

export const createConversation = async (senderId, recipientId) => {
    try {
        const response = await api.post(`/conversation`, null, {
            params: { senderId, recipientId },
        });
        return response;
    } catch (error) {
        console.error("Error creating conversation:", error);
        throw error;
    }
};

export const fetchConversations = async (userId) => {
    try {
        const response = await api.get("/getconversations", {
            params: { userId },
        });
        return response;
    } catch (error) {
        console.error("Error fetching conversations:", error);
        throw error;
    }
};
