import React, {useEffect, useState} from "react";
import { useNavigate } from "react-router-dom";
import "../styles/Navbar.css";
import keycloak from "../keycloak";

const Navbar = () => {
    const navigate = useNavigate();
    const [role, setRole] = useState(null);

    useEffect(() => {
        setRole(localStorage.getItem("role"));
    }, []);

    const handleLogin = () => {
        keycloak.login({
            redirectUri: window.location.origin + "/post-login"
        });
    };

    const handleRegister = () => {
        keycloak.register({
            redirectUri: window.location.origin + "/post-registration",
        });
    };

    const handleLogout = () => {
        if (keycloak) {
            const redirectUri = "https://fullstack24-frontendnew.app.cloud.cbh.kth.se";

            const logoutUrl = `https://fullstackkc.app.cloud.cbh.kth.se/realms/PatientSystem/protocol/openid-connect/logout?client_id=user-ms&post_logout_redirect_uri=${encodeURIComponent(redirectUri)}`;
            localStorage.removeItem("role");
            localStorage.removeItem("token");
            localStorage.removeItem("userId");
            window.location.href = logoutUrl;
        }
    };




    const getDashboardTitle = () => {
        if (role === "DOCTOR") {
            return "Doctor Dashboard";
        } else if (role==="PATIENT") {
            return "Patient Dashboard";
        } else if (role==="STAFF") {
            return "Staff Dashboard";
        }
        return "Dashboard";
    };


    const renderLinks = () => {
        if (!keycloak.authenticated) {
            return (
                <div className="navbar-links">
                    <button onClick={handleLogin} className="nav-button">
                        Login
                    </button>
                    <button onClick={handleRegister} className="nav-button">
                        Register
                    </button>
                </div>
            );
        }

        return (
            <div className="navbar-links">
                <button onClick={() => navigate("/dashboard")} className="nav-button">
                    Dashboard
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
                <h2>{keycloak.authenticated ? getDashboardTitle() : "Patient System"}</h2>
                {renderLinks()}
            </div>
        </nav>
    );
};

export default Navbar;
