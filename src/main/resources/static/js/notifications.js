"use strict";

const displayNotificationCount = document.getElementById("notification-count");
document.addEventListener("DOMContentLoaded", async function () {
    let notificationCount = await fetch ("/getNotificationCount", {
        method: 'GET'
    }).then(function(request) {
        return request.json();
    });

    displayNotificationCount.innerText = "" + notificationCount;
    console.log(notificationCount);
});