"use strict";
document.addEventListener("DOMContentLoaded", async function () {
    let friends = await fetch ("/getFriends", {
        method: 'GET'
    }).then(function(request) {
        return request.json();
    })

    console.log(friends);

    let x = "";
    for (let i = 0; i < friends.length; i++) {
        x += `<li class="chat-li"><a class="dropdown-chat-item" href="/messages/talk/${friends[i].username}">${friends[i].firstName} ${friends[i].lastName}</a></li><hr class="chat-dropdown-divider">`
    }
    // console.log(x);
    const friendsDropdown = document.getElementById("list-friends");
    // console.log(friendsDropdown);
    if (friendsDropdown != null) {
        friendsDropdown.innerHTML = x;
    }
})

//for each friendship, add the attributes using what we get from the json object, THEN we can pass it to the html??