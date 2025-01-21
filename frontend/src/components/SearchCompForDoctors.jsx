import React, { useState } from "react";
import { searchPatientsByDiagnosis } from "../api/Search-ms-api";

const SearchCompForDoctors = () => {
    const [patients, setPatients] = useState([]);
    const [searchTerm, setSearchTerm] = useState("");
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");

    const handleSearch = async () => {
        if (!searchTerm.trim()) {
            setError("Please enter a diagnosis to search.");
            return;
        }
        setError("");
        try {
            setLoading(true);
            const response = await searchPatientsByDiagnosis(searchTerm);
            setPatients(response);
        } catch (error) {
            console.error("Error fetching patients:", error);
            setError("Failed to fetch patients. Please try again.");
        } finally {
            setLoading(false);
        }
    };

    const handleInputChange = (e) => {
        setSearchTerm(e.target.value);
    };

    return (
        <div>
            <h2>Search Patients by Diagnosis</h2>
            <div>
                <input
                    type="text"
                    placeholder="Enter diagnosis"
                    value={searchTerm}
                    onChange={handleInputChange}
                />
                <button onClick={handleSearch}>Search</button>
            </div>

            {loading && <p>Loading patients...</p>}

            {error && <p className="error-message">{error}</p>}

            {patients.length > 0 ? (
                <table>
                    <thead>
                    <tr>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Email</th>
                        <th>Phone Number</th>
                    </tr>
                    </thead>
                    <tbody>
                    {patients.map((patient) => (
                        <tr key={patient.userId}>
                            <td>{patient.firstname}</td>
                            <td>{patient.lastname}</td>
                            <td>{patient.email}</td>
                            <td>{patient.phoneNumber}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            ) : (
                !loading && !error && <p>No patients found.</p>
            )}
        </div>
    );
};

export default SearchCompForDoctors;
