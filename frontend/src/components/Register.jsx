import React, { useState } from "react";
import { registerUser } from "../api/User-ms-api";
import { useNavigate } from "react-router-dom";

function Register() {
    // Define state for user details
    const [email, setEmail] = useState("");
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    // Define state for additional info
    const [socialSecurityNumber, setSocialSecurityNumber] = useState("");
    const [firstname, setFirstname] = useState("");
    const [lastname, setLastname] = useState("");
    const [phoneNumber, setPhoneNumber] = useState("");
    const [address, setAddress] = useState("");
    const [gender, setGender] = useState("");
    const [dateOfBirth, setDateOfBirth] = useState("");

    const [error, setError] = useState("");
    const navigate = useNavigate();

    const handleRegister = async (event) => {
        event.preventDefault();

        const registrationDto = {
            userDetails: {
                email,
                username,
                password,
                role: "Patient", // Default role
            },
            additionalInfoDetails: {
                socialSecurityNumber,
                firstname,
                lastname,
                phoneNumber,
                address,
                gender,
                dateOfBirth,
            },
        };

        try {
            const response = await registerUser(registrationDto);
            console.log("User registered:", response.data);

            navigate("/");
        } catch (error) {
            console.error("Error during registration:", error);
            setError("Failed to register user. Please try again.");
        }
    };

    return (
        <div className="register">
            <form onSubmit={handleRegister}>
                <h2>Register</h2>

                {/* User details */}
                <input
                    type="email"
                    placeholder="Email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                />
                <input
                    type="text"
                    placeholder="Username"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                />
                <input
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                />

                {/* Additional info */}
                <input
                    type="text"
                    placeholder="Social Security Number"
                    value={socialSecurityNumber}
                    onChange={(e) => setSocialSecurityNumber(e.target.value)}
                />
                <input
                    type="text"
                    placeholder="First Name"
                    value={firstname}
                    onChange={(e) => setFirstname(e.target.value)}
                />
                <input
                    type="text"
                    placeholder="Last Name"
                    value={lastname}
                    onChange={(e) => setLastname(e.target.value)}
                />
                <input
                    type="text"
                    placeholder="Phone Number"
                    value={phoneNumber}
                    onChange={(e) => setPhoneNumber(e.target.value)}
                />
                <input
                    type="text"
                    placeholder="Address"
                    value={address}
                    onChange={(e) => setAddress(e.target.value)}
                />
                <select
                    value={gender}
                    onChange={(e) => setGender(e.target.value)}
                >
                    <option value="">Select Gender</option>
                    <option value="Male">Male</option>
                    <option value="Female">Female</option>
                    <option value="Other">Other</option>
                </select>
                <input
                    type="date"
                    value={dateOfBirth}
                    onChange={(e) => setDateOfBirth(e.target.value)}
                />

                <button type="submit">Register</button>
            </form>

            {/* Display error message if any */}
            {error && <p className="error-message">{error}</p>}

            {/* Back to Login button */}
            <button onClick={() => navigate("/")}>Back to Login</button>
        </div>
    );
}

export default Register;
