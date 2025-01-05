import React, {useEffect, useState} from "react";
import { useNavigate } from "react-router-dom";
import "../styles/Navbar.css";
import keycloak from "../keycloak";
import { addPatient } from "../api/Patient-ms-api";
import {jwtDecode} from "jwt-decode";

const Navbar = () => {
    const navigate = useNavigate();
    const [roles, setRoles] = useState([]);

    useEffect(() => {
        if (keycloak && keycloak.authenticated) {
            const userRoles = getUserRoles();
            setRoles(userRoles);
        }
    }, [keycloak?.authenticated, keycloak?.tokenParsed]);

    const handleLogin = () => {
        keycloak.login({
            redirectUri: window.location.origin + "/dashboard"
        });
    };

    const handleRegister = () => {
        keycloak.register({
            redirectUri: window.location.origin + "/post-registration", // Redirect here after registration
        });
    };


    const handleLogout = () => {
        if (keycloak) {
            keycloak.logout({
                redirectUri: window.location.href
            }).then(() => {
                setRoles([]);
                localStorage.removeItem("token");
            });
        }
    };

    const getDashboardTitle = () => {
        if (roles.includes("DOCTOR")) {
            return "Doctor Dashboard";
        } else if (roles.includes("PATIENT")) {
            return "Patient Dashboard";
        } else if (roles.includes("STAFF")) {
            return "Staff Dashboard";
        }
        return "Dashboard";
    };

    const getUserRoles = () => {
        if (keycloak && keycloak.tokenParsed) {
            const roles = keycloak.tokenParsed?.realm_access?.roles || [];
            return roles;
        }
        return [];
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
