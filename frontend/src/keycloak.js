import Keycloak from "keycloak-js";

const keycloak = new Keycloak({
    url: "https://fullstackkc.app.cloud.cbh.kth.se",
    realm: "PatientSystem",
    clientId: "user-ms",
});

keycloak
    .init({ onLoad: "check-sso" })
    .then((authenticated) => {
        if (authenticated) {
            console.log("User is authenticated:", keycloak.token);
            scheduleTokenRefresh();
        } else {
            console.log("User is not authenticated");
        }
    })
    .catch((error) => {
        console.error("Keycloak initialization failed:", error);
    });


function scheduleTokenRefresh() {
    const refreshInterval = (keycloak.tokenParsed.exp - keycloak.tokenParsed.iat) * 1000 * 0.8;

    setInterval(() => {
        keycloak.updateToken(30)
            .then((refreshed) => {
                if (refreshed) {
                    console.log("Token refreshed:", keycloak.token);
                } else {
                    console.log("Token is still valid");
                }
            })
            .catch((error) => {
                console.error("Failed to refresh token:", error);

                keycloak.login();
            });
    }, refreshInterval);
}


export default keycloak;
