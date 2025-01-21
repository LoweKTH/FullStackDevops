const express = require('express');
const cors = require('cors');
const path = require('path');
const imageController = require('./controller/ImageController');
const { sequelize } = require('./utils/db');
const jwt = require('jsonwebtoken');
const jwksClient = require('jwks-rsa');

const app = express();

process.env.NODE_TLS_REJECT_UNAUTHORIZED = '0';

const client = jwksClient({
    jwksUri: 'https://fullstackkc.app.cloud.cbh.kth.se/realms/PatientSystem/protocol/openid-connect/certs'
});

function getKey(header, callback) {
    client.getSigningKey(header.kid, (err, key) => {
        if (err) {
            console.error('Failed to fetch signing key:', err);
            return callback(err, null);
        }
        const signingKey = key.publicKey || key.rsaPublicKey;
        callback(null, signingKey);
    });
}

async function verifyJWT(req, res, next) {
    const token = req.headers['authorization']?.split(' ')[1];
    if (!token) return res.status(401).json({ message: 'Authorization header is missing' });

    const decodedToken = (() => {
        try {
            return jwt.decode(token, { complete: true });
        } catch (err) {
            console.error('Error decoding token:', err.message);
            return null;
        }
    })();
    if (!decodedToken || !decodedToken.header.kid) {
        return res.status(401).json({ message: 'Invalid token: Missing kid in header.' });
    }

    jwt.verify(token, getKey, { algorithms: ['RS256'] }, (err, decoded) => {
        if (err) {
            console.error('Token verification failed:', err.message);
            return res.status(401).json({ message: 'Invalid or expired token', error: err.message });
        }
        const expectedIssuer = 'https://fullstackkc.app.cloud.cbh.kth.se/realms/PatientSystem';
        if (decoded.iss !== expectedIssuer) {
            return res.status(401).json({ message: `Invalid issuer: ${decoded.iss}` });
        }
        req.user = { ...decoded, customClaim: 'Custom Value' };
        next();
    });
}
app.use(express.json({ limit: '50mb' })); // Adjust to match your multer fileSize limit
app.use(express.urlencoded({ extended: true, limit: '50mb' }));

// CORS setup with logging for debugging
app.use((req, res, next) => {
    console.log('Incoming Request:', req.method, req.url);
    console.log('Request Headers:', req.headers); // Log incoming headers

    next(); // Proceed to the next middleware
});

app.use(cors({
    origin: 'https://fullstack24-frontendnew.app.cloud.cbh.kth.se', // Set specific origin (not '*')
    methods: ['GET', 'POST', 'PUT', 'DELETE', 'OPTIONS'],
    allowedHeaders: ['Authorization', 'Content-Type'],
    exposedHeaders: ['Authorization'],
    credentials: true,  // Allow credentials (Authorization header, cookies)
}));

// Log CORS-related headers on responses
app.use((req, res, next) => {
    const originalSend = res.send;

    res.send = function (body) {
        // Log the CORS headers being sent
        console.log('Response Headers:', res.getHeaders());

        // Call the original send method
        return originalSend.call(this, body);
    };

    next();
});

app.use((req, res, next) => {
    console.log('Request body size:', req.headers['content-length']);
    next();
});

// Handle pre-flight OPTIONS requests and log responses
app.options('*', (req, res) => {
    console.log('Handling OPTIONS request:', req.method, req.url);
    res.header('Access-Control-Allow-Origin', 'https://fullstack24-frontendnew.app.cloud.cbh.kth.se');
    res.header('Access-Control-Allow-Methods', 'GET, POST, PUT, DELETE, OPTIONS');
    res.header('Access-Control-Allow-Headers', 'Authorization, Content-Type');
    res.header('Access-Control-Allow-Credentials', 'true');  // Allow credentials
    res.sendStatus(204); // No content
});

// Additional logging for OPTIONS pre-flight request
app.use((req, res, next) => {
    if (req.method === 'OPTIONS') {
        console.log('Pre-flight OPTIONS request received');
    }
    next();
});

// Handle the rest of the routes
app.use('/uploads', express.static(path.join(__dirname, 'uploads')));
app.use('/api/image', verifyJWT, imageController);

sequelize.sync().then(() => console.log('Database connected')).catch(console.error);

const PORT = process.env.PORT || 3002;
app.listen(PORT, () => console.log(`Server running on http://localhost:${PORT}`));
