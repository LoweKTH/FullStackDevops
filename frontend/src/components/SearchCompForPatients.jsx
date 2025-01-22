import React, { useState } from "react";
import { searchDoctorsForPatient } from "../api/Search-ms-api";
import "../styles/SearchCompForPatients.css";

function SearchCompForPatients() {
    const [searchTerm, setSearchTerm] = useState("");
    const [searchResults, setSearchResults] = useState([]);
    const [filter, setFilter] = useState("Name");
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(false);

    const patientId = Number(localStorage.getItem("userId")); // Assume patient ID is stored in localStorage

    const handleSearch = async () => {
        setError("");
        setLoading(true);

        try {
            const results = await searchDoctorsForPatient(patientId, searchTerm);
            console.log("Search results:", results);
            setSearchResults(results.doctors || []); // Extract only the doctors list
        } catch (err) {
            console.error("Error searching for doctors:", err);
            setError("Failed to retrieve search results.");
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="search-comp-for-patients">
            <h2>Search page</h2>
            <div className="search-container">
                {/* Search Input */}
                <input
                    type="text"
                    placeholder={`Search by ${filter.toLowerCase()}...`}
                    value={searchTerm}
                    onChange={(e) => setSearchTerm(e.target.value)}
                    className="search-input"
                />

                {/* Filter Dropdown */}
                <select
                    value={filter}
                    onChange={(e) => setFilter(e.target.value)}
                    className="filter-dropdown"
                >
                    <option value="Name">Name</option>
                </select>

                {/* Search Button */}
                <button
                    onClick={handleSearch}
                    disabled={loading}
                    className="search-button"
                >
                    {loading ? "Searching..." : "Search"}
                </button>
            </div>

            {/* Error Message */}
            {error && <p className="error-message">{error}</p>}

            {/* Search Results */}
            {searchResults.length > 0 && (
                <div className="search-results">
                    <h3>Search Results</h3>
                    <table>
                        <thead>
                        <tr>
                            <th>Name</th>
                            <th>Email</th>
                            <th>Phone Number</th>
                        </tr>
                        </thead>
                        <tbody>
                        {searchResults.map((doctor) => (
                            <tr key={doctor.id}>
                                <td>{doctor.firstname} {doctor.lastname}</td>
                                <td>{doctor.email}</td>
                                <td>{doctor.phoneNumber}</td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>
            )}

            {/* No Results */}
            {searchResults.length === 0 && !loading && searchTerm && (
                <p>No doctors found for your search criteria.</p>
            )}
        </div>
    );
}

export default SearchCompForPatients;