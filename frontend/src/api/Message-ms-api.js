import axios from "axios";

const messagingBaseURL = 'http://localhost:30003/api/messages'; // Update this to your messaging API URL

const api = axios.create({
    baseURL: messagingBaseURL,
});

export const fetchMessages = async (conversationId) => {
    return api.get(`/${conversationId}`);
};

export const sendMessage = async (conversationId, senderId, message) => {
    return api.post(`/${conversationId}/send`, {
        senderId,
        content: message
    });
};

export const createConversation = async (senderId, recipientId) => {
    return api.post(`/conversation?senderId=${senderId}&recipientId=${recipientId}`);
};


export const fetchConversations = async (userId) => {
    return api.get("/getconversations", {
        params: { userId }
    });
};
