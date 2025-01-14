import React, { useState, useEffect } from "react";
import { fetchAssignedDoctorStaff } from "../api/Patient-ms-api";
import "../styles/DoctorStaffList.css";
import { createConversation } from "../api/Message-ms-api";
import { useNavigate } from "react-router-dom";

function DoctorStaffList( ) {
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(true);

    const [doctors, setDoctors] = useState([]);
    const [staff, setStaff] = useState([]);
    const navigate = useNavigate();
    const userId = localStorage.getItem("userId");

    useEffect(() => {
        const getAssignedDoctorStaff = async () => {
            try {
                setLoading(true);
                const response = await fetchAssignedDoctorStaff(userId);
                console.log("Assigned Doctor/Staff fetched:", response.data);
                setDoctors(response.data.doctors);
                setStaff(response.data.staffs);
            } catch (err) {
                console.error("Error fetching assigned doctor/staff:", err);
                setError("Failed to fetch assigned doctor/staff.");
            } finally {
                setLoading(false);
            }
        };
        getAssignedDoctorStaff();
    }, [userId]);


    const handleMessageClick = async (doctorstaffId) => {
        console.log("Conversation with doctorstaff ID: ", doctorstaffId);
        console.log("test");
        try {
            const response = await createConversation(userId, doctorstaffId);
            const conversationId = response.data.id;

            navigate("/messaging", {
                state: {
                    conversationId, userId,doctorstaffId
                },
            });
        } catch (error) {
            console.error("Error creating conversation:", error);
        }
    };

    if (loading) {
        return <p>Loading assigned doctor/staff...</p>;
    }

    if (error) {
        return <p className="error-message">{error}</p>;
    }

    if (doctors.length === 0 && staff.length === 0) {
        return <p>No assigned doctor or staff found.</p>;
    }

    return (
        <div className="doctor-staff-list">
            <h2>Assigned Doctor/Staff</h2>

            {/* Doctors Table */}
            {doctors.length > 0 && (
                <div className="my-doctors">
                    <h3>My Doctors</h3>
                    <table>
                        <thead>
                        <tr>
                            <th>Name</th>
                            <th>Email</th>
                            <th>Phone Number</th>
                            <th>Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        {doctors.map((doctor) => (
                            <tr key={doctor.id}>
                                <td>{doctor.firstname}</td>
                                <td>{doctor.email}</td>
                                <td>{doctor.phoneNumber}</td>
                                <td>
                                    <button
                                        onClick={() => handleMessageClick(doctor.userId)}
                                        className="message-btn"
                                    >
                                        Message
                                    </button>
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>
            )}

            {/* Staff Table */}
            {staff.length > 0 && (
                <div className="my-staff">
                    <h3>My Staff</h3>
                    <table>
                        <thead>
                        <tr>
                            <th>Name</th>
                            <th>Email</th>
                            <th>Phone Number</th>
                            <th>Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        {staff.map((staffMember) => (
                            <tr key={staffMember.id}>
                                <td>{staffMember.firstname}</td>
                                <td>{staffMember.email}</td>
                                <td>{staffMember.phoneNumber}</td>
                                <td>
                                    <button
                                        onClick={() => handleMessageClick(staffMember.userId)}
                                        className="message-btn"
                                    >
                                        Message
                                    </button>
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>
            )}
        </div>
    );
}

export default DoctorStaffList;
