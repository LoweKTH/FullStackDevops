// app.js
const express = require('express');
const cors = require('cors');
const path = require('path');
const imageController = require('./controller/ImageController');
const app = express();
const { sequelize } = require('./utils/db');
const Image = require('./model/Image');


app.use(cors({
    origin: 'http://localhost:30000',
    methods: ['GET', 'POST'],
    allowedHeaders: ['Content-Type'],
}));

app.use('/uploads', express.static(path.join(__dirname, 'uploads')));

app.use('/api/image', imageController);

sequelize.sync().then(() => {
    console.log('Database connected and models synced');
}).catch((error) => {
    console.error('Unable to connect to the database:', error);
});


const PORT = process.env.PORT || 3002;
app.listen(PORT, () => {
    console.log(`Server running on http://localhost:${PORT}`);
});
