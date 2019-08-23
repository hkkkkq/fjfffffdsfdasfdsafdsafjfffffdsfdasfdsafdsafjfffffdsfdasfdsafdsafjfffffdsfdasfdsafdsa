let installPromptEvent;
window.addEventListener("beforeinstallprompt", function(e) {
    console.log("beforeinstallprompt Event fired");
    // Prevent Chrome <= 67 from automatically showing the prompt
    e.preventDefault();
    // Stash the event so it can be triggered later.
    installPromptEvent = e;
    // Update the install UI to notify the user app can be installed

    $(".appdown").show();
    return false;
});

function addToHomeScreen() {
    if(installPromptEvent !== undefined) {
        // The user has had a postive interaction with our app and Chrome
        // has tried to prompt previously, so let's show the prompt.
        installPromptEvent.prompt();
        // Follow what the user has done with the prompt.
        installPromptEvent.userChoice.then(function(choiceResult) {
            console.log(choiceResult.outcome);
            if(choiceResult.outcome == "dismissed") {
                console.log("User cancelled home screen install");
            } else {
                console.log("User added to home screen");
            }
            // We no longer need the prompt.  Clear it up.
            installPromptEvent = null;
        });
    } else {
        alert("앱이 이미 설치되어 있습니다.");
    }
}