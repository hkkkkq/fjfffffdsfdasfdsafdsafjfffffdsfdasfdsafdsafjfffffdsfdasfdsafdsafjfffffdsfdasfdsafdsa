// navigator (브라우저)에 serviceWorker 기능이 있는지 확인
if ("serviceWorker" in navigator) {
    // 서비스워커 설치시 DOM 블로킹을 막아준다.
    window.addEventListener("load", function() {
        // 서비스워커를 register 하면 promise를 반환한다.
        navigator.serviceWorker
            .register("/sw.js")
            .then(function (){
                console.log("ServiceWorker Loaded!!.");
            })
            .catch(function(error){
                console.log(error);
            });

        if(!navigator.onLine){
            $(".topbar").addClass("off");
        }
    });

    /**
     * 네트워크 온라인 발생시
     */
    window.addEventListener('online', function(){
        $(".topbar").removeClass("off");
        console.log("On-line!!!!!!");
    });


    /**
     * 네트워크 오프라인 발생시
     */
    window.addEventListener('offline', function(){
        $(".topbar").addClass("off");
        console.log("Off-line!!!!!!");
    });



}

