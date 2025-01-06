import React, { useState } from "react";
import { fetchPatientsByDiagnosis } from "../api/Search-ms-api";

function SearchCompForDoctors() {
    const [query, setQuery] = useState("");
    const [results, setResults] = useState([]);
    const [error, setError] = useState("");

    const handleSearch = async () => {
        try {
            const data = await fetchPatientsByDiagnosis(query);

            // Ensure data is an array
            if (Array.isArray(data)) {
                setResults(data);
            } else {
                throw new Error("Unexpected response format");
            }
        } catch (err) {
            console.error("Search error:", err);
            setError("Failed to fetch patients");
            setResults([]);
        }
    };

    return (
        <div>
            <h1>Search Patients by Diagnosis</h1>
            <input
                type="text"
                value={query}
                onChange={(e) => setQuery(e.target.value)}
                placeholder="Enter diagnosis..."
            />
            <button onClick={handleSearch}>Search</button>

            {error && <p style={{ color: "red" }}>{error}</p>}

            <div>
                {results.length > 0 ? (
                    <table>
                        <thead>
                        <tr>
                            <th>First Name</th>
                            <th>Last Name</th>
                            <th>Email</th>
                        </tr>
                        </thead>
                        <tbody>
                        {results.map((patient) => (
                            <tr key={patient.id}>
                                <td>{patient.firstname}</td>
                                <td>{patient.lastname}</td>
                                <td>{patient.email}</td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                ) : (
                    !error && <p>No patients found.</p>
                )}
            </div>
        </div>
    );
}

export default SearchCompForDoctors;
