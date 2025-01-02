import React, { useState } from "react";
import { searchPatients } from "../api/Search-ms-api";
import "../styles/SearchPage.css";

const SearchPage = () => {
    const [query, setQuery] = useState("");
    const [filterType, setFilterType] = useState("name"); // Default filter
    const [results, setResults] = useState([]);
    const [error, setError] = useState("");

    const role = localStorage.getItem("role");

    const handleSearch = async (e) => {
        e.preventDefault();

        try {
            const response = await searchPatients({ query, filterType });
            setResults(response.data);
            setError("");
        } catch (err) {
            console.error("Search error:", err);
            setError("Failed to fetch search results. Please try again.");
        }
    };

    const renderResultsTable = () => {
        if (results.length === 0) {
            return <p>No results found.</p>;
        }

        return (
            <table className="results-table">
                <thead>
                <tr>
                    {Object.keys(results[0]).map((key) => (
                        <th key={key}>{key}</th>
                    ))}
                </tr>
                </thead>
                <tbody>
                {results.map((result, index) => (
                    <tr key={index}>
                        {Object.values(result).map((value, idx) => (
                            <td key={idx}>{value}</td>
                        ))}
                    </tr>
                ))}
                </tbody>
            </table>
        );
    };

    return (
        <div className="search-page">
            <h2>{"Search patient"}</h2>
            <form onSubmit={handleSearch} className="search-form">
                <select
                    value={filterType}
                    onChange={(e) => setFilterType(e.target.value)}
                    className="filter-dropdown"
                >
                    <option value="name">Name</option>
                    <option value="condition">Condition</option>
                </select>
                <input
                    type="text"
                    placeholder={`Search by ${filterType}`}
                    value={query}
                    onChange={(e) => setQuery(e.target.value)}
                    className="search-input"
                />
                <button type="submit" className="search-button">
                    Search
                </button>
            </form>

            {error && <p className="error-message">{error}</p>}
            <div className="search-results">{renderResultsTable()}</div>
        </div>
    );
};

export default SearchPage;
