const jwt = require('jsonwebtoken');

// Middleware to check and adjust the token's issuer claim
function checkIssuer(req, res, next) {
    const token = req.headers['authorization']?.split(' ')[1]; // Extract Bearer token

    if (!token) {
        return res.status(401).json({ message: 'Token missing' });
    }

    try {
        console.log("test");
        // Decode the token without verifying it to inspect the claims
        const decodedToken = jwt.decode(token, { complete: true });

        // Check the 'iss' claim
        if (decodedToken && decodedToken.payload.iss === 'http://localhost:8080/realms/PatientSystem') {
            // If the 'iss' is localhost, modify it to match the container's Keycloak URL
            decodedToken.payload.iss = 'http://keycloak:8080/realms/PatientSystem';  // Adjust to the container name or the correct Keycloak URL
        }
        console.log(decodedToken.payload.iss);
        // Attach the adjusted decoded token to the request object
        req.decodedToken = decodedToken.payload;

        // Continue to the next middleware or route handler
        next();
    } catch (error) {
        res.status(401).json({ message: 'Invalid token or issuer', error: error.message });
    }
}

module.exports = checkIssuer;
