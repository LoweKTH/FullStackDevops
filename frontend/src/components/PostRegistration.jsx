import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import keycloak from "../keycloak";
import { addPatient } from "../api/Patient-ms-api";
import {addDoctor} from "../api/Doctor-ms-api";
import {addStaff} from "../api/Staff-ms-api";

const PostRegistration = () => {
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

        const userInfo = keycloak.tokenParsed;
        const userId = userInfo?.sub;
        const role = userInfo.role;
        if(role==="PATIENT"){
            console.log(userInfo);

            const patientDto = {
                firstname: userInfo.given_name,
                lastname: userInfo.family_name,
                email: userInfo.email,
                socialSecurityNumber: userInfo.socialsecuritynumber,
                phoneNumber: userInfo.phonenumber,
                address: userInfo.address,
                userId: userId,
                dateOfBirth: userInfo.dateOfBirth,
                gender: userInfo.gender,
            };
            console.log(patientDto);
            addPatient(patientDto, keycloak.token)
                .then((response) => {
                    console.log("Patient added successfully:", response);
                    navigate("/dashboard");
                })
                .catch((error) => {
                    console.error("Error adding patient:", error);
                });
        }else if(role==="DOCTOR"){
            console.log(userInfo);

            const doctorDto = {
                firstname: userInfo.given_name,
                lastname: userInfo.family_name,
                email: userInfo.email,
                socialSecurityNumber: userInfo.socialsecuritynumber,
                phoneNumber: userInfo.phonenumber,
                userId: userId,
                gender: userInfo.gender,
            };
            console.log(doctorDto);
            addDoctor(doctorDto, keycloak.token)
                .then((response) => {
                    console.log("doctor added successfully:", response);
                    navigate("/dashboard");
                })
                .catch((error) => {
                    console.error("Error adding doctor:", error);
                });
        }else{
            console.log(userInfo);
            const staffDto = {
                firstname: userInfo.given_name,
                lastname: userInfo.family_name,
                email: userInfo.email,
                socialSecurityNumber: userInfo.socialsecuritynumber,
                phoneNumber: userInfo.phonenumber,
                userId: userId,
                gender: userInfo.gender,
            };
            console.log(staffDto);

            addStaff(staffDto, keycloak.token)
                .then((response) => {
                    console.log("staff added successfully:", response);
                    navigate("/dashboard");
                })
                .catch((error) => {
                    console.error("Error adding staff:", error);
                });
        }

    }, [isReady, navigate]);

    return (
        <div>
            <h2>Processing Registration...</h2>
        </div>
    );
};

export default PostRegistration;
