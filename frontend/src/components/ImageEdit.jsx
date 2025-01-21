import React, { useRef, useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { Canvas, Image, Textbox, PencilBrush } from "fabric";
import { updateImage } from "../api/Image-ms-api";
import "../styles/ImageEdit.css";

const ImageEdit = () => {
    const location = useLocation();
    const navigate = useNavigate();
    const { imagePath, imageId } = location.state;

    const canvasRef = useRef(null);
    const [canvas, setCanvas] = useState(null);

    useEffect(() => {
        const fabricCanvas = new Canvas(canvasRef.current, {
            width: 800,
            height: 600,
        });
        setCanvas(fabricCanvas);

        const handleKeyDown = (event) => {

            if (fabricCanvas && event.key === 'Delete') {
                const activeObject = fabricCanvas.getActiveObject();
                if (activeObject) {
                    fabricCanvas.remove(activeObject);
                    fabricCanvas.renderAll();
                }
            }
        };


        window.addEventListener('keydown', handleKeyDown);

        if (fabricCanvas && imagePath) {
            Image.fromURL(imagePath, {
                crossOrigin: "anonymous",
            })
                .then((img) => {
                    if(img) {
                        console.log("Image loaded successfully:", img);
                        fabricCanvas.add(img);
                        fabricCanvas.setDimensions({width: img.width, height: img.height});
                        img.selectable = false;
                    }
                })
                .catch((error) => {
                    console.error("Error loading image:", error);

                });
        }



        return () => {
            window.removeEventListener('keydown', handleKeyDown);
            fabricCanvas.dispose();
        };
    }, [imagePath]);

    const addText = () => {
        if (canvas) {
            const text = new Textbox("New Text", {
                left: 50,
                top: 50,
                fontSize: 20,
                fill: "#000",
            });
            canvas.add(text);
            canvas.setActiveObject(text);
        }
    };

    const toggleDrawingMode = () => {
        if (canvas) {
            canvas.isDrawingMode = !canvas.isDrawingMode;
            if (canvas.isDrawingMode) {
                if (!canvas.freeDrawingBrush) {
                    canvas.freeDrawingBrush = new PencilBrush(canvas);
                    canvas.freeDrawingBrush.width = 5;
                    canvas.freeDrawingBrush.color = "#ff0000";
                }

                console.log("Drawing Mode Activated");
            } else {
                console.log("Drawing Mode Deactivated");
            }
        }
    };

    const setBrushColor = (color) => {
        if (canvas && canvas.freeDrawingBrush) {
            canvas.freeDrawingBrush.color = color;
            console.log(`Brush color set to ${color}`);
        }
    };


    const saveChanges = async (event) => {
        event.preventDefault();

        if (!canvas || !imageId) {
            alert("Please provide the canvas and image ID.");
            return;
        }

        const dataURL = canvas.toDataURL({
            format: "png",
            quality: 1,
        });
        // Convert the dataURL to a Blob (this is similar to the process of converting to a file)
        const blob = await fetch(dataURL)
            .then(res => res.blob())
            .catch(err => console.error("Error converting to Blob:", err));

        // Create a file from the Blob
        const file = new File([blob], "edited-image.png", { type: "image/png" });
        console.log(`Blob size: ${blob.size} bytes`);


        const formData = new FormData();
        formData.append("image", file);
        formData.append("imageId", imageId);


        try {
            await updateImage(formData);

            alert("Image updated successfully!");
            navigate(-1);

        } catch (err) {
            console.error("Error updating image:", err);
            alert("Error updating image.");
        }
    };

    return (
        <div className="image-edit-container">
            <h2 className="title">Edit Image</h2>

            <div className="canvas-container">
                <div className="color-buttons">
                    <button
                        className="color-btn red"
                        onClick={() => setBrushColor("#ff0000")}
                    >
                        Red
                    </button>
                    <button
                        className="color-btn green"
                        onClick={() => setBrushColor("#00ff00")}
                    >
                        Green
                    </button>
                    <button
                        className="color-btn blue"
                        onClick={() => setBrushColor("#0000ff")}
                    >
                        Blue
                    </button>
                </div>


                <canvas
                    ref={canvasRef}
                    className="image-edit-canvas"
                    width={800}
                    height={600}
                />
            </div>

            <div className="controls">
                <button className="cancel-btn" onClick={() => navigate(-1)}>
                    Cancel
                </button>
                <button className="save-btn" onClick={saveChanges}>
                    Save
                </button>
                <button className="add-text-btn" onClick={addText}>
                    Add Text
                </button>
                <button className="toggle-draw-btn" onClick={toggleDrawingMode}>
                    Toggle Draw Mode
                </button>
            </div>
        </div>
    );

};

export default ImageEdit;
