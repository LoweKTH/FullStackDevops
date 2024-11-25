import React from "react";
import { useLocation, useNavigate } from "react-router-dom";

const PatientDetails = () => {
    const location = useLocation();
    const navigate = useNavigate();
    const { patient } = location.state; // Retrieve patient data passed through navigation state

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
                <p><strong>Date of Birth:</strong> {patient.dateOfBirth}</p>
                <p><strong>Email:</strong> {patient.email}</p>
            </div>
            <button
                onClick={() => navigate("/patients")}
                className="back-btn"
            >
                Back to Patient List
            </button>
        </div>
    );
};

export default PatientDetails;
