import React, { useState } from 'react';
import { searchDoctorsWithPatients } from '../api/Search-ms-api';

const SearchCompForPatients = () => {
    const [searchName, setSearchName] = useState('');
    const [doctors, setDoctors] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    // Function to handle searching
    const handleSearch = async () => {
        setLoading(true);
        setError(null);
        try {
            const data = await searchDoctorsWithPatients(searchName);
            setDoctors(data);
        } catch (err) {
            setError('Failed to fetch doctors. Please try again.');
        } finally {
            setLoading(false);
        }
    };

    // Handle input change
    const handleInputChange = (e) => {
        setSearchName(e.target.value);
    };

    // Handle form submission
    const handleSubmit = (e) => {
        e.preventDefault();
        handleSearch();
    };

    // Render doctors
    const renderDoctors = () => {
        if (loading) return <p>Loading...</p>;
        if (error) return <p>{error}</p>;
        if (doctors.length === 0) return <p>No doctors found for "{searchName}".</p>;

        return (
            <div>
                {doctors.map((doctor, index) => (
                    <div key={index} className="doctor-card">
                        <h3>{doctor.doctor.name}</h3>
                        <p>Specialty: {doctor.doctor.specialty}</p>
                        <p>Patients:</p>
                        <ul>
                            {doctor.patients.map((patient, pIndex) => (
                                <li key={pIndex}>{patient.name}</li>
                            ))}
                        </ul>
                    </div>
                ))}
            </div>
        );
    };

    return (
        <div className="doctor-search">
            <form onSubmit={handleSubmit}>
                <input
                    type="text"
                    value={searchName}
                    onChange={handleInputChange}
                    placeholder="Search by doctor name"
                />
                <button type="submit">Search</button>
            </form>
            {renderDoctors()}
        </div>
    );
};

export default SearchCompForPatients;