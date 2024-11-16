import React, { useState, useEffect } from "react";
import { fetchPatients } from "../api/Patient-ms-api";

function PatientList() {
    const [patients, setPatients] = useState([]);
    const [error, setError] = useState("");

    useEffect(() => {
        const getPatients = async () => {
            try {
                const response = await fetchPatients();
                setPatients(response.data);
            } catch (error) {
                setError("Failed to fetch patients.");
            }
        };

        getPatients();
    }, []);

    return (
        <div className="patient-list">
            <h2>Patients</h2>
            {error && <p className="error-message">{error}</p>}
            <ul>
                {patients.map((patient) => (
                    <li key={patient.id}>
                        {patient.firstname} {patient.lastname} - {patient.email}
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default PatientList;
