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

    return (
        <div className="dashboard">
            <h2>Welcome to the Dashboard</h2>
            {(role === "doctor" || role === "staff") && (
                <>
                    <button onClick={handleCheckPatients}>Check Patients</button>
                </>

            )}
            {role === "Patient" && (
                <>
                    <button onClick={handleCheckDoctorStaff}>Check Doctor/Staff</button>
                    <button onClick={handleMyProfile}>My Profile</button>
                </>
            )}
        </div>
    );
}

export default Dashboard;
