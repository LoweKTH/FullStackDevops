import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Navbar from "./components/Navbar";
import Login from "./components/Login";
import Register from "./components/Register";
import Dashboard from "./components/Dashboard";
import PatientList from "./components/PatientList";
import Note from "./components/Note";

function App() {
    return (
        <Router>
            <div>
                <Navbar />
                <div className="content">
                    <Routes>
                        <Route path="/login" element={<Login />} />
                        <Route path="/register" element={<Register />} />
                        <Route path="/dashboard" element={<Dashboard />} />
                        <Route path="/patients" element={<PatientList />} />
                        <Route path="/create-note" element={<Note />} />
                    </Routes>
                </div>
            </div>
        </Router>
    );
}

export default App;
