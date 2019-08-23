const PUBLIC_VAPID_KEY = "BIqYSVgXH9V2hP1Y1iUOeqRSUeplXv44-ZSj_UFAc1eA_V3tYF5-YvUvUlt1-dKpl3PWlVcWgZm4iK8OvTdLQ-A";

let messaging = null;
const firebaseConfig = {
    apiKey: "AIzaSyD-l94aO4EzAYyplnoXcxCu8k6FCChVw9M",
    authDomain: "test-70441.firebaseapp.com",
    databaseURL: "https://test-70441.firebaseio.com",
    projectId: "test-70441",
    storageBucket: "test-70441.appspot.com",
    messagingSenderId: "194089234412",
    appId: "1:194089234412:web:c13ee0cef76fac86"
};

firebase.initializeApp(firebaseConfig);

if (firebase.messaging.isSupported()) {
    messaging = firebase.messaging();
    messaging.usePublicVapidKey(PUBLIC_VAPID_KEY);
    messaging.onTokenRefresh(function() {
        messaging.getToken().then(function(refreshedToken) {
            console.log("Token refreshed.");
            // Send Instance ID token to app server.
            sendTokenToServer(refreshedToken, true);
        }).catch(function(error) {
            console.log("Unable to retrieve refreshed token ", error);
        });
    });
    messaging.onMessage(function(payload) {
        console.log("Message received. ", payload);
    });

// Requesting permission
//     subscribe();
}


function subscribe() {
    if(!firebase.messaging.isSupported()){
        console.log("Firebase Massaging not support!!");
        return false;
    }
    messaging.requestPermission().then(function() {
        console.log("Notification permission granted.");
        messaging.getToken().then(function(currentToken) {
            if (currentToken) {
                sendTokenToServer(currentToken, true);
            } else {
                console.log("No Instance ID token available. Request permission to generate one.");
            }
        }).catch(function(error) {
            console.log("An error occurred while retrieving token. ", error);
        });
    }).catch(function(error) {
        console.log("Unable to get permission to notify.", error);
    });
}

function unsubscribe() {
    if(!firebase.messaging.isSupported()){
        console.log("Firebase Massaging not support!!");
        return false;
    }
    // Delete Instance ID token.
    messaging.getToken().then(function(currentToken) {
        messaging.deleteToken(currentToken).then(function() {
            console.log("Token deleted.");
            sendTokenToServer(currentToken, false);
            //setSubscribeButton();
        }).catch(function(error) {
            console.log("Unable to delete token. ", error);
        });
    }).catch(function(error) {
        console.log("Error retrieving Instance ID token. ", error);
    });
}

function sendTokenToServer(token, subscribed) {

    let method = subscribed ? "POST" : "DELETE";
    let params = 'clientToken='+ token;
    let url = (subscribed ? "/subscribe" : "/unsubscribe") + "?" + params;
    let response = fetch(url, {method: method});

}


function push() {
    var response = fetch("/push");
    var text = response.text();
    console.log("Push Response. ", text);
}