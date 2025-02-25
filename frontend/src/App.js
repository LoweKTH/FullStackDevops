import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Navbar from "./components/Navbar";
import Login from "./components/Login";
import Register from "./components/Register";
import Dashboard from "./components/Dashboard";
import PatientList from "./components/PatientList";
import Note from "./components/Note";
import Messaging from "./components/Messaging";
import DoctorStaffList from "./components/DoctorStaffList";
import Profile from "./components/Profile";
import PatientDetails from "./components/PatientDetails";
import ImageUpload from "./components/ImageUpload";
import ImageEdit from "./components/ImageEdit";

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
                        <Route path="/messaging" element={<Messaging />} />
                        <Route path="/doctorstaff" element={<DoctorStaffList />} />
                        <Route path="/profile" element={<Profile />} />
                        <Route path="/patient-details" element={<PatientDetails />} />
                        <Route path="/image-upload" element={<ImageUpload />} />
                        <Route path="/image-edit" element={<ImageEdit />} />
                    </Routes>
                </div>
            </div>
        </Router>
    );
}

export default App;
