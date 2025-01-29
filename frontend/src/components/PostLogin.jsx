import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import keycloak from "../keycloak";
import {jwtDecode} from "jwt-decode";

const PostLogin = () => {
    const navigate = useNavigate();
    const [isReady, setIsReady] = useState(false);

    useEffect(() => {
        const interval = setInterval(() => {
            if (keycloak.authenticated && keycloak.tokenParsed) {
                console.log("User is authenticated.");
                setIsReady(true);
                clearInterval(interval);
            } else {
                console.log("Waiting for authentication...");
            }
        }, 1000);

        return () => clearInterval(interval);
    }, []);



    useEffect(() => {
        if (!isReady) return;

        const role = getInfoFromToken();
        console.log("User Role:", role);


        localStorage.setItem("role", role);


        navigate("/dashboard");
    }, [isReady, navigate]);

    const getInfoFromToken = () => {
        const token = keycloak.token;
        console.log("Token:", token);

        if (token) {
            const decodedToken = jwtDecode(token);


            const clientRoles = decodedToken.resource_access?.['user-ms']?.roles || [];


            if (clientRoles.length > 0) {
                return clientRoles[0];
            }
        }

        return "Guest";
    };

    return (
        <div>
            <h2>Processing Login...</h2>
        </div>
    );
};

export default PostLogin;
