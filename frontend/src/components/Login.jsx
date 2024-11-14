import React, { useState } from 'react';
import './LoginPage.css';
import {loginUser} from "../api/User-ms-api";
import {useNavigate} from "react-router-dom";

function Login() {
    // Define state for username, password, and error message
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleLogin = async (event) => {
        event.preventDefault();

        const credentials = {
            username,
            password,
        };

        try{
            const response = await loginUser(credentials);
            console.log(response.data);


            navigate('/dashboard');
        } catch (error){
            setError('Wrong username or password')
        }

    };

    return (
        <div className="login">
            <form onSubmit={handleLogin}>
                <input
                    type="text"
                    placeholder="Username"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)} // Update username state
                />
                <input
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)} // Update password state
                />
                <button type="submit">Login</button>
            </form>

            {/* Display error message if there is one */}
            {error && <p className="error-message">{error}</p>}
        </div>
    );
}

export default Login;
