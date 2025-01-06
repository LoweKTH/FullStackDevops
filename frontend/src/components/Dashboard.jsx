import React from "react";
import { useNavigate } from "react-router-dom";

function Dashboard() {
    const role = localStorage.getItem("role");
    const navigate = useNavigate();
    const userId = localStorage.getItem("userId");


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
            {(role === "Doctor" || role === "Staff") && (
                <>
                    <button onClick={handleCheckPatients}>Check Patients</button>
                    <button onClick={handleSearchCompForDoctors}>Search</button>
                </>

            )}
            {role === "Patient" && (
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
