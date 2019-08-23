importScripts("https://www.gstatic.com/firebasejs/5.9.1/firebase-app.js");
importScripts("https://www.gstatic.com/firebasejs/5.9.1/firebase-messaging.js");

//Initialize the Firebase app in the service worker by passing in the messagingSenderId.
firebase.initializeApp({
    messagingSenderId: "194089234412"
});

//Retrieve an instance of Firebase Messaging so that it can handle background messages.
var messaging = firebase.messaging();

//If you would like to customize notifications that are received in the background (Web app is closed or not in browser focus) then you should implement this optional method
messaging.setBackgroundMessageHandler(function(payload) {
    console.log('[firebase-messaging-sw.js] Received background message ', payload);
    // Customize notification here
    /*
    var notificationTitle = 'Weater Forecast Widget';
    var notificationOptions = {
        body: 'Background Message body.'
    };
    */
    var notification = payload.notification;
    return self.registration.showNotification(notification.title + "[Background]", notification);
});

self.addEventListener("push", function(event) {
    console.log("[firebase-messaging-sw.js] Push Received.");
    console.log(`[firebase-messaging-sw.js] Push had this data: "${event.data.text()}"`);
    /*
    var title = "Weather Forecaset Widget";
    var options = {
        body: event.data.text(),
        icon: "images/icon/android-icon-192x192.png",
        badge: "images/badge.png",
        vibrate: [500, 110, 500, 110, 450, 110, 200, 110, 170, 40, 450, 110, 200, 110, 170, 40, 500],
        sound: "/audios/notification-sound.mp3",
        data: {
            url: "https://www.naver.com"
        }
    };
    */

    let notification = event.data.json().notification;
    event.waitUntil(self.registration.showNotification(notification.title, notification));
});

self.addEventListener("notificationclick", function(event) {
    const data = event.notification.data;
    console.log("[firebase-messaging-sw.js] Notification click Received.", data);
    event.notification.close();
    event.waitUntil(
        clients.openWindow(data.url)
    );
});