import React from 'react';
import { useNavigate } from 'react-router-dom';
import './Navbar.css';

function Navbar() {

    const navigate = useNavigate();

    const handleLoginClick = () => {
        navigate('/login');
    };

    const handlePatientClick = () => {
        navigate('/patients');
    };

    return (
        <nav className="navbar">
            <h2>App name</h2>
            <div className="navbar-buttons">
                <button onClick={handlePatientClick}>Patients</button>
                <button onClick={handleLoginClick}>Login</button>
            </div>
        </nav>
    );
}

export default Navbar;
