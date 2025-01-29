const jwt = require('jsonwebtoken');
const jwksClient = require('jwks-rsa');

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

        // Extract roles from resource_access
        const roles = decoded?.resource_access?.['user-ms']?.roles || [];
        req.user = { ...decoded, roles };

        next();
    });
}

function requireRole(role) {
    return (req, res, next) => {
        if (!req.user || !req.user.roles.includes(role)) {
            return res.status(403).json({ message: 'Forbidden: Insufficient permissions' });
        }
        next();
    };
}

module.exports = { verifyJWT, requireRole };
