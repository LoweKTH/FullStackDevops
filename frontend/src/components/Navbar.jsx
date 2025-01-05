import React, {useEffect, useState} from "react";
import { useNavigate } from "react-router-dom";
import "../styles/Navbar.css";
import keycloak from "../keycloak"; // Assuming keycloak is initialized here



const Navbar = () => {
    const navigate = useNavigate();
    const [roles, setRoles] = useState([]);

    // Get user roles from the token after login
    useEffect(() => {
        if (keycloak && keycloak.authenticated) {
            const userRoles = getUserRoles();
            setRoles(userRoles);
        }
    }, [keycloak?.authenticated, keycloak?.tokenParsed]);

    const handleLogin = () => {
        keycloak.login({

            redirectUri: window.location.origin + "/dashboard", // Redirect to /dashboard after successful login
        });
    };

    const handleRegister = () => {
        keycloak.register({

            redirectUri: window.location.origin + "/dashboard", // Redirect to /dashboard after successful login
        });

    };

    const handleLogout = () => {
        if (keycloak) {
            keycloak.logout({
                redirectUri: window.location.href, // Redirect the user to the current page after logout
            }).then(() => {
                setRoles([]);
                localStorage.removeItem("token");
               // localStorage.removeItem("refresh_token");
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
            // Keycloak tokenParsed contains decoded JWT claims
            const roles = keycloak.tokenParsed?.realm_access?.roles || [];
            console.log("User roles:", roles);
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

        // If a user is logged in
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
