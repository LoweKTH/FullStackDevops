import React, { useState } from 'react';
import { registerUser } from "../api/User-ms-api";
import { useNavigate } from "react-router-dom";

function Register(){
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [email, setEmail] = useState('');
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();

    const handleRegister = async (event) => {
        event.preventDefault();
        setError('');

        if(username.length < 3 || password.length < 3){
            setError("Username and password must be at least 3 characters");
            return;
        }

        const userDetails = {
            username,
            password,
            email,
        };

        setLoading(true);
        try{
            const response = await registerUser(userDetails);
            console.log(response.data);
            navigate('/login'); //redirect to login page after successful registration
        }catch(error){
            setError("Registration failed. Please try again.");
        }finally{
            setLoading(false);
        }
    };

    return(
        <div className="register">
            <form onSubmit={handleRegister}>
                <input
                    type="text"
                    placeholder="Username"
                    value={username}
                    onChange={(e) => {
                        setUsername(e.target.value);
                        if(error) setError('');
                    }}
                />
                <input
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e) =>{
                        setPassword(e.target.value);
                        if(error) setError('');
                    }}
                />
                <input
                    type="email"
                    placeholder="Email"
                    value={email}
                    onChange={(e) =>{
                        setEmail(e.target.value);
                        if(error) setError('');
                    }}
                />
                <button type="submit" disabled={loading}>
                    {loading ? "Registering..." : "Register"}
                </button>
            </form>

            {/* Display error message if there is one */}
            {error && <p className="error-message">{error}</p>}
            <p>Already have an account? <a href="/login">Login here</a></p>
        </div>
    );
}

export default Register;