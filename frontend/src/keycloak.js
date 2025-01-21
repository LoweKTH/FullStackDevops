import Keycloak from "keycloak-js";

// Initialize the Keycloak instance
const keycloak = new Keycloak({
    url: "https://fullstackkc.app.cloud.cbh.kth.se", // Keycloak server URL
    realm: "PatientSystem",    // Keycloak realm name
    clientId: "user-ms", // Keycloak client ID
});

keycloak
    .init({ onLoad: "check-sso" })
    .then((authenticated) => {
        if (authenticated) {
            console.log("User is authenticated:", keycloak.token);
            localStorage.setItem("token", keycloak.token);
            scheduleTokenRefresh();
        } else {
            console.log("User is not authenticated");
        }
    })
    .catch((error) => {
        console.error("Keycloak initialization failed:", error);
    });


function scheduleTokenRefresh() {
    const refreshInterval = (keycloak.tokenParsed.exp - keycloak.tokenParsed.iat) * 1000 * 0.8; // Refresh at 80% of token lifetime

    setInterval(() => {
        keycloak.updateToken(30) // Attempt to refresh if token will expire in the next 30 seconds
            .then((refreshed) => {
                if (refreshed) {
                    console.log("Token refreshed:", keycloak.token);
                    localStorage.setItem("token", keycloak.token);
                } else {
                    console.log("Token is still valid");
                }
            })
            .catch((error) => {
                console.error("Failed to refresh token:", error);
                // Handle token refresh failure (e.g., force login)
                keycloak.login();
            });
    }, refreshInterval);
}


export default keycloak;
