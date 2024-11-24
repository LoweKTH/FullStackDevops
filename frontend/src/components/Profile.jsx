import React, { useState, useEffect } from "react";
import "../styles/Profile.css";
import { fetchUserProfile } from "../api/Patient-ms-api";
import {useLocation} from "react-router-dom";

function Profile() {
    const [profileData, setProfileData] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");
    const location = useLocation();
    const { userId } = location.state;


    useEffect(() => {
        const getProfile = async () => {
            try {
                setLoading(true);
                const response = await fetchUserProfile(userId);
                setProfileData(response.data);
            } catch (err) {
                console.error("Error fetching profile data:", err);
                setError("Failed to load profile data.");
            } finally {
                setLoading(false);
            }
        };

        getProfile();
    }, []);

    if (loading) {
        return <p>Loading profile...</p>;
    }

    if (error) {
        return <p className="error-message">{error}</p>;
    }

    return (
        <div className="profile">
            <h2>My Profile</h2>
            {profileData ? (
                <div className="profile-details">
                    <p><strong>Social Security Number:</strong> {profileData.socialSecurityNumber}</p>
                    <p><strong>First Name:</strong> {profileData.firstname}</p>
                    <p><strong>Last Name:</strong> {profileData.lastname}</p>
                    <p><strong>Date of Birth:</strong> {new Date(profileData.dateOfBirth).toLocaleDateString()}</p>
                    <p><strong>Address:</strong> {profileData.address}</p>
                    <p><strong>Gender:</strong> {profileData.gender}</p>
                    <p><strong>Email:</strong> {profileData.email}</p>
                    <p><strong>Phone Number:</strong> {profileData.phoneNumber}</p>
                </div>
            ) : (
                <p>No profile data available.</p>
            )}
        </div>
    );
}

export default Profile;
