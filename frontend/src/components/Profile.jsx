import React, { useState, useEffect } from "react";
import "../styles/Profile.css";
import { fetchUserProfile, fetchNotesForPatient, fetchDiagnosesForPatient } from "../api/Patient-ms-api";

function Profile() {
    const [profileData, setProfileData] = useState(null);
    const [notes, setNotes] = useState([]);
    const [diagnoses, setDiagnoses] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");
    const [modalData, setModalData] = useState(null); // Data to display in modal
    const [modalTitle, setModalTitle] = useState(""); // Title for the modal
    const [isModalOpen, setIsModalOpen] = useState(false);

    useEffect(() => {

        const userId = localStorage.getItem("userId");

        const getProfile = async () => {
            try {
                setLoading(true);
                console.log(userId);
                const response = await fetchUserProfile(userId);
                setProfileData(response.data);
            } catch (err) {
                console.error("Error fetching profile data:", err);
                setError("Failed to load profile data.");
            } finally {
                setLoading(false);
            }
        };

        const getNotes = async () => {
            try {
                const response = await fetchNotesForPatient(userId);
                setNotes(response.data);
            } catch (err) {
                console.error("Error fetching notes:", err);
            }
        };

        const getDiagnoses = async () => {
            try {
                const response = await fetchDiagnosesForPatient(userId);
                setDiagnoses(response.data);
            } catch (err) {
                console.error("Error fetching diagnoses:", err);
            }
        };

        getProfile();
        getNotes();
        getDiagnoses();
    }, []);

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

    if (loading) {
        return <p>Loading profile...</p>;
    }

    if (error) {
        return <p className="error-message">{error}</p>;
    }

    return (
        <div className="profile">
            <h2>My Profile</h2>
            {profileData ? (
                <div className="profile-details">
                    <p><strong>Social Security Number:</strong> {profileData.socialSecurityNumber}</p>
                    <p><strong>First Name:</strong> {profileData.firstname}</p>
                    <p><strong>Last Name:</strong> {profileData.lastname}</p>
                    <p><strong>Date of Birth:</strong> {new Date(profileData.dateOfBirth).toLocaleDateString()}</p>
                    <p><strong>Address:</strong> {profileData.address}</p>
                    <p><strong>Gender:</strong> {profileData.gender}</p>
                    <p><strong>Email:</strong> {profileData.email}</p>
                    <p><strong>Phone Number:</strong> {profileData.phoneNumber}</p>
                </div>
            ) : (
                <p>No profile data available.</p>
            )}

            <div className="actions">
                <button onClick={() => openModal(notes, "Patient Notes")}>View Notes</button>
                <button onClick={() => openModal(diagnoses, "Patient Diagnoses")}>View Diagnoses</button>
            </div>

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
}

export default Profile;
