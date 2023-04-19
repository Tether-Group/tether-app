"use strict";

document.addEventListener("DOMContentLoaded", async function () {
    let idsOfPostsThatLoggedInUserCanCommentOn = await fetch ("/verifyAddComments", {
        method: 'GET'
    }).then(function(request) {
        return request.json();
    })

    idsOfPostsThatLoggedInUserCanCommentOn.forEach( (postId) => {
        const postToAppendAddCommentBox = document.querySelector("#add-comment-box" + postId);
        postToAppendAddCommentBox.hidden = false;
    })
    console.log(idsOfPostsThatLoggedInUserCanCommentOn);
});