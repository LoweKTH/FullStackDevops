import React, { useState } from "react";
import { createNote } from "../api/Patient-ms-api";
import { useNavigate, useLocation } from "react-router-dom";
import "../styles/Note.css"

const Note = () => {
    const [noteContent, setNoteContent] = useState("");
    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");
    const navigate = useNavigate();
    const location = useLocation();


    const { doctorstaffId, userId } = location.state;

    if (!userId) {
        console.error("Error: patientUserId is undefined in Note.jsx.");
    } else {
        console.log(`Received patientUserId: ${userId}`);
    }

    const handleNoteSubmit = async (event) => {
        event.preventDefault();

        const noteData = {
            content: noteContent,
            doctorstaffId,
        };

        try {
            await createNote(userId, noteData);
            setSuccess("Note successfully created!");
            setNoteContent("");

            setTimeout(() => navigate("/patients"), 2000);
        } catch (err) {
            console.error("Error creating note:", err);
            setError("Failed to create note. Please try again.");
        }
    };

    return (
        <div className="note-container">
            <h2>Create Note for Patienteee</h2>
            {error && <p className="error-message">{error}</p>}
            {success && <p className="success-message">{success}</p>}
            <form onSubmit={handleNoteSubmit} className="note-form">
                <textarea
                    className="note-textarea"
                    placeholder="Write your note here..."
                    value={noteContent}
                    onChange={(e) => setNoteContent(e.target.value)}
                    rows="5"
                    cols="50"
                />
                <div className="button-group">
                    <button type="submit" className="submit-note-btn">
                        Submit Note
                    </button>
                    <button
                        type="button"
                        onClick={() => navigate("/patients")}
                        className="back-btn"
                    >
                        Back to Patients
                    </button>
                </div>
            </form>
        </div>
    );
};

export default Note;
