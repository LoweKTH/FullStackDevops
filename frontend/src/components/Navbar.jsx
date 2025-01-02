import React from "react";
import { useNavigate } from "react-router-dom";
import "../styles/Navbar.css";

const Navbar = () => {
    const navigate = useNavigate();

    const role = localStorage.getItem("role");
    const isLoggedIn = !!role;

    const handleLogout = () => {
        localStorage.clear();
        navigate("/login");
    };

    const getDashboardTitle = () => {
        switch (role) {
            case "Doctor":
                return "Doctor Dashboard";
            case "Patient":
                return "Patient Dashboard";
            case "Staff":
                return "Staff Dashboard";
            default:
                return "Dashboard";
        }
    };

    const renderLinks = () => {
        if (!isLoggedIn) {
            return (
                <div className="navbar-links">
                    <button onClick={() => navigate("/login")} className="nav-button">
                        Login
                    </button>
                    <button onClick={() => navigate("/register")} className="nav-button">
                        Register
                    </button>
                </div>
            );
        }

        // If a user is logged in
        return (
            <div className="navbar-links">
                <button onClick={() => navigate("/dashboard")} className="nav-button">
                    Dashboard
                </button>
                <button
                    onClick={() => navigate("/search")}
                    className="nav-button"
                    style={{display: ["Doctor", "Staff"].includes(localStorage.getItem("role")) ? "inline-block" : "none"}}
                >
                    Search
                </button>
                <button onClick={handleLogout} className="nav-button">
                    Logout
                </button>
            </div>
        );
    };

    return (
        <nav className="navbar">
            <div className="navbar-container">
                <h2>{isLoggedIn ? getDashboardTitle() : "Patient System"}</h2>
                {renderLinks()}
            </div>
        </nav>
    );
};

export default Navbar;
