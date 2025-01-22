import React, {useEffect, useState} from "react";
import { useNavigate } from "react-router-dom";
import {jwtDecode} from "jwt-decode";

function Dashboard() {
    const [role, setRole] = useState(null);
    const navigate = useNavigate();
    const userId = localStorage.getItem("userId");

    useEffect(() => {
        const role = getInfoFromToken(); // Extract role from the token
        console.log(role);
        setRole(role); // Set the role state
    }, []);


    const getInfoFromToken = () => {
        const token = localStorage.getItem("token"); // Retrieve token from localStorage
       // const token = keycloak.token;
        console.log("token:" +   token);

        if (token) {
            const decodedToken = jwtDecode(token);
            // For client-level roles
            const clientRoles = decodedToken.resource_access?.['user-ms']?.roles || [];
            const userId = decodedToken.sub;
            localStorage.setItem("userId",userId);
            console.log('User ID:', userId);
            if(clientRoles.length>0){
                return clientRoles[0];
            }

            console.log('Client Roles:', clientRoles);
        }
        return "Guest"; // Default role if token is not found
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

    const handleSearchCompForPatients = () => {
        navigate("/patientsearch");
    }

    const handleSearchCompForDoctors = () => {
        navigate("/doctorsearch");
    }

    return (
        <div className="dashboard">
            <h2>Welcome to the Dashboard</h2>
            {(role === "DOCTOR" || role === "STAFF") && (
                <>
                    <button onClick={handleCheckPatients}>Check Patients</button>
                    <button onClick={handleSearchCompForDoctors}>Search</button>
                </>

            )}
            {role === "PATIENT" && (
                <>
                    <button onClick={handleCheckDoctorStaff}>Check Doctor/Staff</button>
                    <button onClick={handleMyProfile}>My Profile</button>
                    <button onClick={handleSearchCompForPatients}>Search</button>
                </>
            )}
        </div>
    );
}

export default Dashboard;
