import React, {useEffect, useState} from "react";
import { useNavigate } from "react-router-dom";
import {jwtDecode} from "jwt-decode";
import keycloak from "../keycloak";

function Dashboard() {
    const [role, setRole] = useState(null);
    const navigate = useNavigate();
    const userId = localStorage.getItem("userId");

    useEffect(() => {
        const role = getInfoFromToken();
        console.log(role);
        setRole(role);
    }, []);


    const getInfoFromToken = () => {

        const token = keycloak.token;
        console.log("token:" +   token);

        if (token) {
            const decodedToken = jwtDecode(token);
            const clientRoles = decodedToken.resource_access?.['user-ms']?.roles || [];
            const userId = decodedToken.sub;
            localStorage.setItem("userId",userId);
            console.log('User ID:', userId);
            if(clientRoles.length>0){
                return clientRoles[0];
            }

            console.log('Client Roles:', clientRoles);
        }
        return "Guest";
    };


    const handleCheckPatients = () => {
        navigate("/patients");
    };

    const handleCheckDoctorStaff = () => {
        navigate("/doctorstaff");
    };

    const handleMyProfile = () => {
        navigate("/profile", {
            state: {
                userId
            },
        });
    }

    const handleSearchCompForStaff = () => {
        navigate("/staffsearch");
    }

    const handleSearchCompForDoctors = () => {
        navigate("/doctorsearch");
    }

    return (
        <div className="dashboard">
            <h2>Welcome to the Dashboard</h2>
            {(role === "DOCTOR") && (
                <>
                    <button onClick={handleCheckPatients}>Check Patients</button>
                    <button onClick={handleSearchCompForDoctors}>Search Patients</button>
                </>

            )}
            {role === "PATIENT" && (
                <>
                    <button onClick={handleCheckDoctorStaff}>Check Doctor/Staff</button>
                    <button onClick={handleMyProfile}>My Profile</button>
                </>
            )}
            {role === "STAFF" && (
                <>
                    <button onClick={handleCheckPatients}>Check Patients</button>
                    <button onClick={handleSearchCompForDoctors}>Search Patients</button>
                    <button onClick={handleSearchCompForStaff}>Search Doctors</button>
                </>

            )}
        </div>
    );
}

export default Dashboard;
