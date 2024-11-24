import ReactDOM from "react-dom";
import { useState } from "react";

const AddDiagnosisModal = ({ show, onClose, onSubmit }) => {
    const [diagnosisName, setDiagnosisName] = useState("");
    const [description, setDescription] = useState("");

    const handleSubmit = () => {
        if (diagnosisName && description) {
            onSubmit(diagnosisName, description);
        } else {
            alert("Please fill out all fields.");
        }
    };

    if (!show) {
        console.log("Modal hidden."); // Debug
        return null;
    }

    return ReactDOM.createPortal(
        <div className="modal">
            <div className="modal-content">
                <h2>Add Diagnosis</h2>
                <label htmlFor="diagnosisName">Diagnosis Name:</label>
                <input
                    type="text"
                    id="diagnosisName"
                    value={diagnosisName}
                    onChange={(e) => setDiagnosisName(e.target.value)}
                    placeholder="Enter diagnosis name"
                />
                <label htmlFor="description">Description:</label>
                <textarea
                    id="description"
                    value={description}
                    onChange={(e) => setDescription(e.target.value)}
                    placeholder="Enter description"
                />
                <button onClick={handleSubmit}>Submit Diagnosis</button>
                <button onClick={onClose}>Close</button>
            </div>
        </div>,
        document.body
    );
};

export default AddDiagnosisModal;
