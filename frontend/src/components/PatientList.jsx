import React, { useState, useEffect } from "react";
import { fetchPatients } from "../api/Patient-ms-api";
import "../styles/PatientList.css";
import {useNavigate} from "react-router-dom";

function PatientList() {
    const [patients, setPatients] = useState([]);
    const [error, setError] = useState("");
    const navigate = useNavigate();

    useEffect(() => {
        const getPatients = async () => {
            try {
                const response = await fetchPatients();
                console.log("Patients fetched:", response.data); // Log the fetched data
                setPatients(response.data);
            } catch (error) {
                setError("Failed to fetch patients.");
                console.error("Error fetching patients:", error);
            }
        };
        getPatients();
    }, [])


    const handleCreateNote = (userId) => {
        console.log("Creating note for patient with userId:", userId); // Log userId
        const doctorId = localStorage.getItem("userId");

        navigate("/create-note", {
            state: {
                userId, doctorId
            },
        });
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
                        <th>Social Security Number</th>
                        <th>Phone Number</th>
                        <th>Address</th>
                        <th>Gender</th>
                        <th>Date of Birth</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    {patients.map((patient) => (
                        <tr key={patient.userId}>
                            <td>{patient.firstname}</td>
                            <td>{patient.lastname}</td>
                            <td>{patient.socialSecurityNumber}</td>
                            <td>{patient.phoneNumber}</td>
                            <td>{patient.address}</td>
                            <td>{patient.gender}</td>
                            <td>{patient.dateOfBirth}</td>
                            <td>
                                <button
                                    onClick={() => handleCreateNote(patient.userId)}
                                    className="create-note-btn"
                                >
                                    Create Note
                                </button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            ) : (
                <p>No patients found.</p>
            )}
        </div>
    );
}

export default PatientList;
