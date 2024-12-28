import React, { useState, useEffect } from "react";
import { useLocation } from "react-router-dom";
import { fetchMessages, sendMessage } from "../api/Message-ms-api";

function Messaging() {
    const { state } = useLocation();
    const { conversationId, userId } = state || {};

    const [messages, setMessages] = useState([]);
    const [newMessage, setNewMessage] = useState("");

    useEffect(() => {
        const getMessages = async () => {
            try {
                const response = await fetchMessages(conversationId);
                console.log(response.data);
                setMessages(response.data);
            } catch (error) {
                console.error("Error fetching messages:", error);
            }
        };
        getMessages();
    }, [conversationId]);

    const handleSendMessage = async () => {
        if (!newMessage) {
            alert("Please enter a message.");
            return;
        }

        try {
            await sendMessage(conversationId, userId, newMessage);
            setNewMessage(""); // Clear the input after sending
            // Refetch the messages to show the latest one
            const response = await fetchMessages(conversationId);
            setMessages(response.data);
        } catch (error) {
            console.error("Error sending message:", error);
        }
    };

    return (
        <div>
            <h3>Messagesdd</h3>
            <div>
                {messages.length === 0 ? (
                    <p>No messages yet.</p>
                ) : (
                    messages.map((message, index) => (
                        <div key={index}>
                            <p><strong>{message.senderName}:</strong> {message.content}</p>
                            <p><strong>Sent at:</strong> {new Date(message.timestamp).toLocaleString()}</p>
                        </div>
                    ))
                )}

            </div>
            <textarea
                value={newMessage}
                onChange={(e) => setNewMessage(e.target.value)}
                placeholder="Type your message here..."
            />
            <button onClick={handleSendMessage}>Send</button>
        </div>
    );
}

export default Messaging;
