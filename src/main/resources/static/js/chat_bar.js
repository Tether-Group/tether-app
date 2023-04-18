"use strict";
document.addEventListener("DOMContentLoaded", async function () {
    let friends = await fetch ("/getFriends", {
        method: 'GET'
    }).then(function(request) {
        return request.json();
    })

    let x = "";
    for (let i = 0; i < friends.length; i++) {
        x += `<li><a href="/messages/talk/${friends[i].username}">Send ${friends[i].username} a message</a></li>`
    }
    document.getElementById("list-friends").innerHTML = x;
})

//for each friendship, add the attributes using what we get from the json object, THEN we can pass it to the html??