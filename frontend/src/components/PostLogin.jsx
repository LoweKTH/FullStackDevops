import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import keycloak from "../keycloak";
import {jwtDecode} from "jwt-decode";

const PostLogin = () => {
    const navigate = useNavigate();
    const [isReady, setIsReady] = useState(false); // Track Keycloak readiness

    useEffect(() => {
        const interval = setInterval(() => {
            if (keycloak.authenticated && keycloak.tokenParsed) {
                console.log("User is authenticated.");
                setIsReady(true);
                clearInterval(interval); // Stop polling once authenticated
            } else {
                console.log("Waiting for authentication...");
            }
        }, 1000); // Poll every second

        return () => clearInterval(interval); // Clean up on unmount
    }, []);



    useEffect(() => {
        if (!isReady) return; // Wait until Keycloak is ready

        const role = getInfoFromToken(); // Extract role from the token
        console.log("User Role:", role);

        // Save the user role and userId to localStorage
        localStorage.setItem("role", role);
        localStorage.setItem("token",keycloak.token);

        // Redirect user after processing login (optional)
        navigate("/dashboard"); // or any other route you'd like to redirect to
    }, [isReady, navigate]);

    const getInfoFromToken = () => {
        const token = keycloak.token;
        console.log("Token:", token);

        if (token) {
            const decodedToken = jwtDecode(token);

            // For client-level roles
            const clientRoles = decodedToken.resource_access?.['user-ms']?.roles || [];

            // Return the first role from the client roles (adjust logic if needed)
            if (clientRoles.length > 0) {
                return clientRoles[0];
            }
        }

        return "Guest"; // Default role if token is not found
    };

    return (
        <div>
            <h2>Processing Login...</h2>
        </div>
    );
};

export default PostLogin;
