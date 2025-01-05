import React, { useState, useEffect } from "react";
import { Outlet, useNavigate } from "react-router-dom";
import keycloak from "../keycloak";

const PrivateRoute = () => {
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate(); // Hook to navigate programmatically

    useEffect(() => {
        // Initialize Keycloak and check authentication status
        if (keycloak) {
            keycloak.init({ onLoad: "check-sso" }).then((authenticated) => {
                if (!authenticated) {
                    // If not authenticated, trigger the login
                    keycloak.login().then(() => {
                        // After successful login, navigate to the intended page (protected route)
                        navigate("/dashboard"); // Adjust the route as necessary
                    }).catch((err) => {
                        console.error("Login failed", err);
                        setLoading(false); // Stop loading if login fails
                    });
                } else {
                    // If authenticated, stop loading and render child routes
                    setLoading(false);
                }
            }).catch((err) => {
                console.error("Error during Keycloak initialization:", err);
                setLoading(false); // Stop loading even if initialization fails
            });
        } else {
            setLoading(false); // If Keycloak is not available, stop loading
        }
    }, [navigate]);

    // Show loading state while Keycloak is processing authentication
    if (loading) {
        return <div>Loading...</div>;
    }

    // Render child routes (protected routes) after authentication
    return <Outlet />;
};

export default PrivateRoute;
