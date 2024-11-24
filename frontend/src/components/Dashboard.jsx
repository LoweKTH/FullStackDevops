import React from "react";
import { useNavigate } from "react-router-dom";

function Dashboard() {
    const role = localStorage.getItem("role");
    const navigate = useNavigate();

    const handleCheckPatients = () => {
        navigate("/patients");
    };

    const handleAssignedPatients = () => {
        navigate("/assigned");
    };

    return (
        <div className="dashboard">
            <h2>Welcome to the Dashboard</h2>
            {(role === "doctor" || role === "staff") && (
                <>
                    <button onClick={handleCheckPatients}>Check Patients</button>
                </>

            )}
        </div>
    );
}

export default Dashboard;
