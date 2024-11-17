import React from "react";
import { useNavigate } from "react-router-dom";
import "../styles/Navbar.css";

const Navbar = () => {
    const navigate = useNavigate();

    const handleLogout = () => {
        // Clear the local storage to log out the user
        localStorage.clear();
        navigate("/login"); // Redirect to the login page
    };

    return (
        <nav className="navbar">
            <div className="navbar-container">
                <h2>Doctor Dashboard</h2>
                <div className="navbar-links">
                    <button onClick={() => navigate("/dashboard")} className="nav-button">
                        Dashboard
                    </button>
                    <button onClick={handleLogout} className="nav-button">
                        Logout
                    </button>
                </div>
            </div>
        </nav>
    );
};

export default Navbar;
