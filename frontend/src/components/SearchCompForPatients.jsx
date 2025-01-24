import React, { useState } from 'react';
import { searchDoctorsWithPatients } from '../api/Search-ms-api';

const SearchCompForPatients = () => {
    const [searchName, setSearchName] = useState('');
    const [doctors, setDoctors] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    const handleSearch = async () => {
        setLoading(true);
        setError(null);
        try {
            console.log("Search term:", searchName);

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
                {doctors.map((doctorWithPatients, index) => (
                    <div key={index} className="doctor-card">
                        <h3>
                            {doctorWithPatients.doctor.firstname} {doctorWithPatients.doctor.lastname}
                        </h3>
                        <p>Email: {doctorWithPatients.doctor.email}</p>
                        <p>Patients:</p>
                        <ul>
                            {doctorWithPatients.patients.map((patient, pIndex) => (
                                <li key={pIndex}>
                                    {patient.firstname} {patient.lastname} - {patient.email}
                                </li>
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
