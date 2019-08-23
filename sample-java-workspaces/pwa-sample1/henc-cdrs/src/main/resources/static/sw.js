//https://developers.google.com/web/tools/workbox/guides/get-started
importScripts("/webjars/workbox-sw/3.3.1/build/workbox-sw.js");

/**
 * 이미지 cache
 * Cashe First > Network second
 */
workbox.routing.registerRoute(
    // Cache image files.
    new RegExp("\.(?:png|jpg|jpeg|svg|gif|woff2)$"),
    // Use the cache if it's available.
    new workbox.strategies.CacheFirst({
        // Use a custom cache name.
        cacheName: "cdrs-cacheFirst-cache-v1",
        plugins: [
            new workbox.expiration.Plugin({
                // Cache only 40 images.
                maxEntries: 40,
                // Cache for a maximum of a week.
                maxAgeSeconds: 7 * 24 * 60 * 60,
            })
        ],
    })
);

/**
 * css, js, html 정보
 * cache first and network to cache update
 */
workbox.routing.registerRoute(
    new RegExp("\.(?:html|js|css|ttf)$"),
    new workbox.strategies.StaleWhileRevalidate({
        cacheName: "cdrs-stale-cache-v1"
    })
);

/**
 * 메뉴 1 케시 > 2 네트워크 > 3 케시 업데이트
 */
workbox.routing.registerRoute(
    new RegExp("/mList.m"),
    new workbox.strategies.CacheFirst({
        cacheName: "cdrs-menu-cache-v1"
    })
);


/**
 * 일보 화면 조회 내용 cash
 */
workbox.routing.registerRoute(
    new RegExp("\/"),
    new workbox.strategies.NetworkFirst({
        cacheName: "cdrs-networkFirst-cache-v1"
    })
);


/**
 * 오프라인 > 온라인 변경시 Notification
 * @param resentRequests
 */
const showNotification = (resentRequests) => {
    console.log(resentRequests); // check if empty
    self.registration.showNotification('Post Sent', {
        body: 'You are back online and your post was successfully sent!',
        icon: '/images/icon/android-icon-192x192.png',
        badge: '/images/icon/android-icon-192x192.png'
    });
};

/**
 * backgroundSysc 설정
 * @type {workbox.backgroundSync.Plugin}
 */
const bgSyncPlugin = new workbox.backgroundSync.Plugin('DailSaveQueue', {
    maxRetentionTime: 24 * 60, // Retry for max of 24 Hours,
    callbacks: {
        queueDidReplay: showNotification
    }
});

/**
 * POST Submit Cache
 * POST 저장 submit시 OffLine 인경우 대기 > Online이 되면 서버로 전송 한다.
 */
workbox.routing.registerRoute(
    new RegExp('\/daily\/partner\/savePartnerDailyData'),
    workbox.strategies.networkOnly({
        plugins: [bgSyncPlugin]
    }),
    'POST'
);
workbox.routing.registerRoute(
    new RegExp('\/daily\/field\/saveDailyMasterData'),
    workbox.strategies.networkOnly({
        plugins: [bgSyncPlugin]
    }),
    'POST'
);
workbox.routing.registerRoute(
    new RegExp('\/daily\/field\/saveDailyModalData'),
    workbox.strategies.networkOnly({
        plugins: [bgSyncPlugin]
    }),
    'POST'
);

