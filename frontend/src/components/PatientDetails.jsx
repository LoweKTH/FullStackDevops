import React, { useState, useEffect } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { fetchNotesForPatient, fetchDiagnosesForPatient } from "../api/Patient-ms-api";
import "../styles/PatientDetails.css";

const PatientDetails = () => {
    const location = useLocation();
    const navigate = useNavigate();
    const { patient } = location.state; // Retrieve patient data passed through navigation state

    const [notes, setNotes] = useState([]);
    const [diagnoses, setDiagnoses] = useState([]);
    const [modalData, setModalData] = useState(null); // Data to display in modal
    const [modalTitle, setModalTitle] = useState(""); // Title for the modal
    const [isModalOpen, setIsModalOpen] = useState(false);

    useEffect(() => {
        const getNotes = async () => {
            try {
                const response = await fetchNotesForPatient(patient.userId);
                setNotes(response.data);
            } catch (err) {
                console.error("Error fetching notes:", err);
            }
        };

        const getDiagnoses = async () => {
            try {
                const response = await fetchDiagnosesForPatient(patient.userId);
                setDiagnoses(response.data);
            } catch (err) {
                console.error("Error fetching diagnoses:", err);
            }
        };

        getNotes();
        getDiagnoses();
    }, [patient.userId]);

    const openModal = (data, title) => {
        setModalData(data);
        setModalTitle(title);
        setIsModalOpen(true);
    };

    const closeModal = () => {
        setIsModalOpen(false);
        setModalData(null);
        setModalTitle("");
    };

    return (
        <div className="patient-details-container">
            <h2>Patient Details</h2>
            <div className="patient-details">
                <p><strong>First Name:</strong> {patient.firstname}</p>
                <p><strong>Last Name:</strong> {patient.lastname}</p>
                <p><strong>Social Security Number:</strong> {patient.socialSecurityNumber}</p>
                <p><strong>Phone Number:</strong> {patient.phoneNumber}</p>
                <p><strong>Address:</strong> {patient.address}</p>
                <p><strong>Gender:</strong> {patient.gender}</p>
                <p><strong>Date of Birth:</strong> {new Date(patient.dateOfBirth).toLocaleDateString()}</p>
                <p><strong>Email:</strong> {patient.email}</p>
            </div>
            <div className="actions">
                <button onClick={() => openModal(notes, "Patient Notes")}>View Notes</button>
                <button onClick={() => openModal(diagnoses, "Patient Diagnoses")}>View Diagnoses</button>
            </div>
            <button
                onClick={() => navigate("/patients")}
                className="back-btn"
            >
                Back to Patient List
            </button>

            {isModalOpen && (
                <div className="modal">
                    <div className="modal-content">
                        <h3>{modalTitle}</h3>
                        <button className="close-btn" onClick={closeModal}>Close</button>
                        <div className="modal-body">
                            {modalData && modalData.length > 0 ? (
                                <ul>
                                    {modalData.map((item) => (
                                        <li key={item.id}>
                                            {modalTitle === "Patient Notes" ? (
                                                <>
                                                    <p><strong>Content:</strong> {item.content}</p>
                                                    <p><strong>Created At:</strong> {new Date(item.createdAt).toLocaleString()}</p>
                                                    <p><strong>Doctor/Staff:</strong> {item.doctorstaffName} ({item.role})</p>
                                                </>
                                            ) : (
                                                <>
                                                    <p><strong>Diagnosis:</strong> {item.diagnosisName}</p>
                                                    <p><strong>Description:</strong> {item.description}</p>
                                                    <p><strong>Diagnosis Date:</strong> {new Date(item.diagnosisDate).toLocaleString()}</p>
                                                    <p><strong>Doctor/Staff:</strong> {item.doctorstaffName} ({item.role})</p>
                                                </>
                                            )}
                                            <hr />
                                        </li>
                                    ))}
                                </ul>
                            ) : (
                                <p>No data available.</p>
                            )}
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
};

export default PatientDetails;
