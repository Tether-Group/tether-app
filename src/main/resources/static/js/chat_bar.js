"use strict";
document.addEventListener("DOMContentLoaded", async function () {
    let friends = await fetch ("/getFriends", {
        method: 'GET'
    }).then(function(request) {
        return request.json();
    })
    console.log(friends);
    let a = "";
    for (let i = 0; i < friends.length; i++) {
        a += `<li><a href="/messages/talk/${friends[i].username}">Send ${friends[i].username} a message</a></li>`
    }
    document.getElementById("list-friends").innerHTML = a;
})

//for each friendship, add the attributes using what we get from the json object, THEN we can pass it to the html??