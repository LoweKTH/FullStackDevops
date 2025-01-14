const express = require('express');
const cors = require('cors');
const path = require('path');
const imageController = require('./controller/ImageController');
const { sequelize } = require('./utils/db');
const Image = require('./model/Image');
const jwt = require('jsonwebtoken');
const jwksClient = require('jwks-rsa');

const app = express();

// JWKS client setup to fetch keys from Keycloak
const client = jwksClient({
    jwksUri: 'http://keycloak:8080/realms/PatientSystem/protocol/openid-connect/certs'
});

process.env.NODE_TLS_REJECT_UNAUTHORIZED = '0';

// Function to retrieve the Keycloak public key by key ID
function getKey(header, callback) {
    client.getSigningKey(header.kid, (err, key) => {
        if (err) {
            return callback(err, null);
        }
        const signingKey = key.publicKey || key.rsaPublicKey;
        callback(null, signingKey);
    });
}

async function verifyJWT(req, res, next) {
    const token = req.headers['authorization']?.split(' ')[1];

    if (!token) {
        return res.status(401).json({ message: 'Authorization header is missing' });
    }

    // Decode the token to inspect its header and payload
    const decodedToken = jwt.decode(token, { complete: true });
    if (!decodedToken || !decodedToken.header.kid) {
        return res.status(401).json({ message: 'Invalid token: Missing kid in header.' });
    }

    // Verify the JWT with the public key
    jwt.verify(token, getKey, { algorithms: ['RS256'] }, (err, decoded) => {
        if (err) {
            console.error('Token verification failed:', err.message);
            return res.status(401).json({ message: 'Invalid or expired token', error: err.message });
        }

        // Validate and adjust the issuer
        const expectedIssuer = 'http://keycloak:8080/realms/PatientSystem';
        if (decoded.iss === 'http://localhost:8080/realms/PatientSystem') {
            decoded.iss = expectedIssuer;
        }

        if (decoded.iss !== expectedIssuer) {
            return res.status(401).json({
                message: `Invalid token issuer. Expected: ${expectedIssuer}, Got: ${decoded.iss}`,
            });
        }

        const claims = {
            ...decoded,
            customClaim: 'Custom Value',
        };

        req.user = claims;

        next();
    });
}

// Enable CORS
app.use(cors({
    origin: '*',
    methods: ['GET', 'POST'],
    allowedHeaders: ['Content-Type', 'Authorization'],
}));

// Serve static files
app.use('/uploads', express.static(path.join(__dirname, 'uploads')));

// Protect your routes
app.use('/api/image', verifyJWT, imageController); // Protect this route

// Sync database
sequelize.sync().then(() => {
    console.log('Database connected and models synced');
}).catch((error) => {
    console.error('Unable to connect to the database:', error);
});

// Start server
const PORT = process.env.PORT || 3002;
app.listen(PORT, () => {
    console.log(`Server running on http://localhost:${PORT}`);
});
