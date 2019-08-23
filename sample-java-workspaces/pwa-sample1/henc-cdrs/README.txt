* PWA
  - https://developers.google.com/web/fundamentals/codelabs/your-first-pwapp/?hl=ko
  - https://techhtml.github.io/blog/2018/pwa/
  - https://www.zerocho.com/category/HTML&DOM/post/5a9a638033c01a001bfa6912
  - https://www.slideshare.net/netil/pwa-65378869
  
* App Shell
  - https://developers.google.com/web/fundamentals/architecture/app-shell?hl=ko

* Web App Manifest
  - https://developers.google.com/web/fundamentals/web-app-manifest/?hl=ko
  - https://developer.mozilla.org/ko/docs/Web/Manifest

* Spring Boot SSL
  - https://jojoldu.tistory.com/350

* Service Worker
  - https://bscnote.tistory.com/33
  - https://serviceworke.rs/
  
* Examples
  - https://github.com/ukmadlz/cloudinary-pwa-jquery

* Troubleshooting
  # An SSL certificate error occurred when fetching the script.
    - https://deanhume.com/testing-service-workers-locally-with-self-signed-certificates/

* 웹 앱 설치 배너 (Install Banner)
  - https://developers.google.com/web/fundamentals/app-install-banners/?hl=ko
  - https://developers.google.com/web/updates/2018/06/a2hs-updates

* Firebase
  - https://console.firebase.google.com
  - JavaScript: https://firebase.google.com/docs/web/setup?authuser=0&hl=ko
  - Push: https://developers.google.com/web/fundamentals/codelabs/push-notifications/?hl=ko

* Service Worker
  - https://serviceworke.rs/

* Push & Notification
  - https://medium.com/@tarique_ejaz/progressive-web-app-push-notifications-making-the-web-app-more-native-in-nature-a167af22e004
  - https://joshua1988.github.io/web-development/pwa/pwa-push-noti-guide/
  - https://developers.google.com/web/fundamentals/codelabs/push-notifications/?hl=ko
  - https://developer.mozilla.org/ko/docs/Web/Progressive_web_apps/Re-engageable_Notifications_Push

* Sending Web push messages from Spring Boot to Browsers
  - https://golb.hplar.ch/2018/01/Sending-Web-push-messages-from-Spring-Boot-to-Browsers.html

* PWA Icon generator
  - https://www.favicon-generator.org/
  
* PWA 캐시 전략
  - https://gracefullight.github.io/2017/12/22/PWA-ServiceWorker-Web-Caching/
  - 실제 업무 개발 시에는 code/*, api/*, page/* 형태로 전략을 사용할 수 있도록 고려 필요

* 브라우저의 스토리지 용량 확인
  - https://developers.google.com/web/tools/workbox/guides/storage-quota
  - console.log(navigator.storage.estimate());

//==================================================================================================================
* Android 에뮬레이터 hosts 파일 수정
emulator -list-avds
emulator -avd Nexus_5X_API_24 -writable-system
adb devices
adb root
adb -s emulator-5554 root
adb remount
adb push d:\hosts /system/etc/

adb push /etc/hosts /system/etc/

https://composer10.tistory.com/entry/android-hosts-%ED%8C%8C%EC%9D%BC-%EC%88%98%EC%A0%95-Android-Emulator
https://stackoverflow.com/questions/43923996/adb-root-is-not-working-on-emulator

//==================================================================================================================
* Install Banner 디버깅
  - chrome://flags/
  - Enable native notification: endabled
  - Bypass user engagement checks: endabled

* Service worker 보기
  - chrome://serviceworker-internals/
  
* https://www.caniuse.com/#feat=push-api

* PWA 질의
  - 왜 PWA를 사용하여 구축하려고 하는가?
  - 기본적으로 PWA 아키텍처는 네이티브 앱의 사용자 경험을 웹브라우저를 이용하는 것으로
    네트워크가 안 되는 환경에서는 네이티브 앱과 동일하게 무용지물일 수도 있다.
    1. 일반적인 앱의 경우에는 사용자 권한에 따라 메뉴가 변경되는 일이 없기 때문에 앱셸을 통해
       앱의 껍데기를 구성하기가 용이하지만 일반적인 비지니스 앱은 권한에 따라 메뉴가 변경되므로
       이미 캐시할 데이터를 구성하기에 어려움이 있다.
    2. PWA의 오프라인 기능은 캐시에 의한 것으로 네트워크가 단절된 경우 기존에 캐시된 데이터를
       화면에 출력하는 것이 기본이다. 즉, 사용자의 입력 데이터가 캐시되는 것이 아니라 이미 과거에
       출력한 데이터를 캐시할 수 있는 것이다. 만약 사용자의 입력 데이터 오프라인 시에 저장하기 위해서는
       Indexed DB 또는 Local Storage 기능을 이용해야 하므로 이것은 PWA의 일부라고 볼 수도 있고 그렇지 않기도 하다.
   - PWA는 화면이 많지않은 또는 SPA에 적합한 기술인데 비지니스 앱은 다양한 화면과 또 화면마다 최근의 데이터를
     로드하여 업무가 진행되는 것이 일반적이므로 네트워크가 단절된 상황에서는 PWA를 구현했다 하더라도 그 의미가 결여됨.
     또한 많은 화면을 캐싱하는 것도 무리가 있어보임.
     (전체화면 50개 중에 어떤 화면은 캐싱되어 화면에 출력되고 어떤 것은 안되고 하는 것은 시스템의 신뢰성이 더욱 낮게보일 가능성이 큼)
   - PWA는 아키텍처 특성상 https를 기본으로 하므로 로컬 개발 및 테스트가 용이하지 않고 또한 최신의 브라우저가 아니라면 아예 PWA 기능을 사용할 수 없음
   - 물론 네이티브 앱과 같이 바로가기 아이콘 및 푸시를 할 수 있다는 장점은 있으나 그것을 구현하기 위해서는 많은 학습과 시행착오가 필요함.