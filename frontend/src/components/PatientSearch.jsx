/**
 * PatientSearch Component
 * Purpose: Allows doctors to search for their assigned patients by name.
 *
 * Props:
 * - doctorId: The ID of the logged-in doctor (required).
 *
 * API Dependencies:
 * - fetchAssignedPatients: Fetches the list of patients assigned to the given doctor.
 */


import React, { useState, useEffect } from "react";
import { fetchAssignedPatients } from "../api/doctor-ms-api";

const PatientSearch = ({ doctorId }) => {
    const [patients, setPatients] = useState([]);
    const [searchQuery, setSearchQuery] = useState("");
    const [filteredPatients, setFilteredPatients] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchPatients = async () => {
            try {
                const response = await fetchAssignedPatients(doctorId);
                setPatients(response.data); // Store all patients
                setFilteredPatients(response.data); // Initialize filtered list
            } catch (err) {
                setError(err.message);
            } finally {
                setLoading(false);
            }
        };

        fetchPatients();
    }, [doctorId]);

    // Handle filtering based on search query
    const handleSearch = (event) => {
        const query = event.target.value.toLowerCase();
        setSearchQuery(query);

        // Filter patients based on their name
        const filtered = patients.filter((patient) =>
            patient.name.toLowerCase().includes(query)
        );
        setFilteredPatients(filtered);
    };

    if (loading) return <p>Loading...</p>;
    if (error) return <p>Error: {error}</p>;

    return (
        <div>
            <h2>Search Patients</h2>
            <input
                type="text"
                placeholder="Search by patient name"
                value={searchQuery}
                onChange={handleSearch}
            />
            <div>
                {filteredPatients.length > 0 ? (
                    filteredPatients.map((patient) => (
                        <div key={patient.id} className="patient-card">
                            <h3>{patient.name}</h3>
                            <p>{patient.details}</p>
                        </div>
                    ))
                ) : (
                    <p>No patients found.</p>
                )}
            </div>
        </div>
    );
};

export default PatientSearch;
