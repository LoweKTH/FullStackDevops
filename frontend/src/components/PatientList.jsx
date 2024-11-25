import React, { useState, useEffect } from "react";
import {fetchPatients, fetchUserProfile} from "../api/Patient-ms-api";
import { fetchNotesForPatient } from "../api/Patient-ms-api";
import "../styles/PatientList.css";
import "../styles/AddDiagnosisModal.css";
import { useNavigate } from "react-router-dom";
import { fetchAssignedPatients } from "../api/Doctor-ms-api";
import { createConversation } from "../api/Message-ms-api";
import { addDiagnosis } from "../api/Patient-ms-api";
import AddDiagnosisModal from "../components/AddDiagnosisModal";

function PatientList() {
    const [patients, setPatients] = useState([]);
    const [assignedPatients, setAssignedPatients] = useState([]);
    const [error, setError] = useState("");
    const [notes, setNotes] = useState([]); // Add state for storing notes
    const [selectedNote, setSelectedNote] = useState(null); // Add state for selected note to display more info
    const navigate = useNavigate();
    const userId = Number(localStorage.getItem("userId"));
    const role = localStorage.getItem("role");
    const [showModal, setShowModal] = useState(false);
    const [selectedPatientId, setSelectedPatientId] = useState(null);

    useEffect(() => {
        const getPatients = async () => {
            try {
                const response = await fetchPatients();
                console.log("Patients fetched:", response.data);
                setPatients(response.data);
            } catch (error) {
                setError("Failed to fetch patients.");
                console.error("Error fetching patients:", error);
            }
        };
        getPatients();
    }, []);

    useEffect(() => {
        const getAssignedPatients = async () => {
            if (!userId) {
                console.log("No doctor ID available");
                return;
            }

            try {
                const response = await fetchAssignedPatients(userId);
                console.log("Assigned Patients fetched:", response.data);
                setAssignedPatients(response.data);
            } catch (error) {
                setError("Failed to fetch assigned patients.");
                console.error("Error fetching assigned patients:", error);
            }
        };
        getAssignedPatients();
    }, [userId]);

    const handleCreateNote = (userId) => {
        console.log("Creating note for patient with userId:", userId);
        const doctorstaffId = localStorage.getItem("userId");

        navigate("/create-note", {
            state: {
                userId, doctorstaffId
            },
        });
    };

    const handleMoreInfo = (patient) => {
        navigate("/patient-details", {
            state: { patient },
        });
    };

    const handleAddDiagnosis = (userId) => {
        setSelectedPatientId(userId);
        console.log("Show Modal State:", showModal);
        setShowModal(true);
    };

    const handleSubmitDiagnosis = async (diagnosisName, description) => {
        const doctorstaffId = userId;
        const diagnosisData = {
            diagnosisName,
            description,
            doctorstaffId,
        };

        try {
            await addDiagnosis(selectedPatientId, diagnosisData); // Call the API
            console.log("Diagnosis added successfully");
            alert("Diagnosis added successfully!");
            setShowModal(false); // Close the modal
        } catch (error) {
            console.error("Error adding diagnosis:", error);
            alert("Failed to add diagnosis. Please try again.");
        }
    };

    const handleMessageClick = async (patientId) => {
        console.log("Conversation with patient ID: ", patientId);

        try {
            patientId = Number(patientId);
            const response = await createConversation(userId, patientId);
            const conversationId = response.data.id;

            navigate("/messaging", {
                state: {
                    conversationId, userId, patientId
                },
            });
        } catch (error) {
            console.error("Error creating conversation:", error);
        }
    };

    return (
        <div className="patient-list">
            <h2>Patient List</h2>
            {error && <p className="error-message">{error}</p>}
            {!error && patients.length > 0 ? (
                <table>
                    <thead>
                    <tr>
                        <th>First Name</th>
                        <th>Last Name</th>
                        {role === "doctor" && (
                            <>
                                <th>Social Security Number</th>
                                <th>Phone Number</th>
                                <th>Address</th>
                                <th>Gender</th>
                                <th>Date of Birth</th>
                            </>
                        )}
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    {patients.map((patient) => (
                        <tr key={patient.userId}>
                            <td>{patient.firstname}</td>
                            <td>{patient.lastname}</td>
                            {role === "doctor" && (
                                <>
                                    <td>{patient.socialSecurityNumber}</td>
                                    <td>{patient.phoneNumber}</td>
                                    <td>{patient.address}</td>
                                    <td>{patient.gender}</td>
                                    <td>{patient.dateOfBirth}</td>
                                </>
                            )}
                            <td>
                                <button
                                    onClick={() => handleCreateNote(patient.userId)}
                                    className="create-note-btn"
                                >
                                    Create Note
                                </button>

                                <button
                                    onClick={() => handleAddDiagnosis(patient.userId)}
                                    className="add-diagnosis-btn"
                                >
                                    Add Diagnosis
                                </button>

                                <button
                                    onClick={() => handleMoreInfo(patient)}
                                    className="more-info-btn"
                                >
                                    More Info
                                </button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            ) : (
                <p>No patients found.</p>
            )}

            {!error && assignedPatients.length > 0 ? (
                <div>
                    <h3>Assigned Patients</h3>
                    <table>
                        <thead>
                        <tr>
                            <th>First Name</th>
                            <th>Last Name</th>
                            {role === "doctor" && (
                                <>
                                    <th>Social Security Number</th>
                                    <th>Phone Number</th>
                                    <th>Address</th>
                                    <th>Gender</th>
                                    <th>Date of Birth</th>
                                </>
                            )}
                            <th>Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        {assignedPatients.map((patient) => (
                            <tr key={patient.userId}>
                                <td>{patient.firstname}</td>
                                <td>{patient.lastname}</td>
                                {role === "doctor" && (
                                    <>
                                        <td>{patient.socialSecurityNumber}</td>
                                        <td>{patient.phoneNumber}</td>
                                        <td>{patient.address}</td>
                                        <td>{patient.gender}</td>
                                        <td>{patient.dateOfBirth}</td>
                                    </>
                                )}
                                <td>
                                    <button
                                        onClick={() => handleMessageClick(patient.userId)}
                                        className="message-btn"
                                    >
                                        Message
                                    </button>
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>
            ) : (
                <p>No assigned patients found.</p>
            )}
            <AddDiagnosisModal
                show={showModal}
                onClose={() => setShowModal(false)}
                onSubmit={handleSubmitDiagnosis}
            />

            {/* Display More Info modal or section */}
            {selectedNote && (
                <div className="note-details">
                    <h3>Note Details</h3>
                    <p><strong>Content:</strong> {selectedNote.content}</p>
                    <p><strong>Doctor/Staff:</strong> {selectedNote.doctorstaffName}</p>
                    <p><strong>Role:</strong> {selectedNote.role}</p>
                    <p><strong>Created At:</strong> {selectedNote.createdAt}</p>
                </div>
            )}
        </div>
    );
}

export default PatientList;
