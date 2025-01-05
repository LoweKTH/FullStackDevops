import Keycloak from "keycloak-js";

// Initialize the Keycloak instance
const keycloak = new Keycloak({
    url: "http://localhost:8085", // Keycloak server URL
    realm: "PatientSystem",    // Keycloak realm name
    clientId: "user-ms", // Keycloak client ID
    credentials: { secret: "rUBEOSf1G7rgCflAIaXVypNUA53zkhnX" },
});

keycloak
    .init({ onLoad: "check-sso" })
    .then((authenticated) => {
        if (authenticated) {
            console.log("User is authenticated:", keycloak.token);
            localStorage.setItem("token", keycloak.token);
        } else {
            console.log("User is not authenticated");
        }
    })
    .catch((error) => {
        console.error("Keycloak initialization failed:", error);
    });


export default keycloak;
