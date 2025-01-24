import React, { useState } from "react";
import { searchPatientsByDiagnosis } from "../api/Search-ms-api";
import { searchPatientsByName } from "../api/Search-ms-api";  // Assuming you create this API function

const SearchCompForDoctors = () => {
    const [patients, setPatients] = useState([]);
    const [searchTerm, setSearchTerm] = useState("");
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");
    const [searchType, setSearchType] = useState("diagnosis");  // To keep track of whether the search is by name or diagnosis

    const handleSearch = async () => {
        if (!searchTerm.trim()) {
            setError("Please enter a value to search.");
            return;
        }
        setError("");
        try {
            setLoading(true);
            let response;
            if (searchType === "diagnosis") {
                response = await searchPatientsByDiagnosis(searchTerm);
            } else if (searchType === "name") {
                response = await searchPatientsByName(searchTerm);  // Assuming this function exists in your API
            }
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

    const handleSearchTypeChange = (e) => {
        setSearchType(e.target.value);  // Switch between 'diagnosis' and 'name'
    };

    return (
        <div>
            <h2>Search Patients</h2>
            <div>
                <input
                    type="text"
                    placeholder={searchType === "diagnosis" ? "Enter diagnosis" : "Enter patient name"}
                    value={searchTerm}
                    onChange={handleInputChange}
                />
                <button onClick={handleSearch}>Search</button>
            </div>

            <div>
                <label>
                    <input
                        type="radio"
                        name="searchType"
                        value="diagnosis"
                        checked={searchType === "diagnosis"}
                        onChange={handleSearchTypeChange}
                    />
                    Search by Diagnosis
                </label>
                <label>
                    <input
                        type="radio"
                        name="searchType"
                        value="name"
                        checked={searchType === "name"}
                        onChange={handleSearchTypeChange}
                    />
                    Search by Name
                </label>
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
