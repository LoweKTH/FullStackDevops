import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import keycloak from "../keycloak";
import { addPatient } from "../api/Patient-ms-api";

const PostRegistration = () => {
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

        const userInfo = keycloak.tokenParsed;
        const userId = userInfo?.sub;
        console.log(userInfo);
        // Construct the PatientDto
        const patientDto = {
            firstname: userInfo.family_name,
            lastname: userInfo.given_name,
            email: userInfo.email,
            socialSecurityNumber: userInfo.socialsecuritynumber,
            phoneNumber: userInfo.phonenumber,
            address: userInfo.address,
            userId: userId,
        };
        console.log(patientDto);
        // Send data to the backend
        addPatient(patientDto, keycloak.token)
            .then((response) => {
                console.log("Patient added successfully:", response);
                navigate("/dashboard"); // Redirect to the dashboard
            })
            .catch((error) => {
                console.error("Error adding patient:", error);
            });
    }, [isReady, navigate]);

    return (
        <div>
            <h2>Processing Registration...</h2>
        </div>
    );
};

export default PostRegistration;
