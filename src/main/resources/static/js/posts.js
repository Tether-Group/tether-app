"use strict";

const editPost = (event) => {
    const postId = event.target.getAttribute("id").match(/\d+/)[0];
    const editInput = document.getElementById("hidden-form" + postId);
    editInput.hidden = false;
    const postContent = document.getElementById("post-content" + postId);
    postContent.hidden = true;
    const editButton = event.target;
    editButton.hidden = true;
    const cancelButton = document.getElementById("cancel-button" + postId);
    cancelButton.hidden = false;
    const saveButton = document.getElementById("save-button" + postId);
    saveButton.hidden = false;
};

const cancelEditingPost = (event) => {
    const postId = event.target.getAttribute("id").match(/\d+/)[0];
    const editInput = document.getElementById("hidden-form" + postId);
    editInput.hidden = true;
    const postContent = document.getElementById("post-content" + postId);
    postContent.hidden = false;
    const editButton = document.getElementById("edit-button" + postId);
    editButton.hidden = false;
    const cancelButton = event.target;
    cancelButton.hidden = true;
    const saveButton = document.getElementById("save-button" + postId);
    saveButton.hidden = true;
};

const showEditCommentForm = (event) => {
    const commentId = event.target.getAttribute("id").match(/\d+/)[0];
    const showEditCommentFormButton = event.target;
    showEditCommentFormButton.hidden = true;
    const editCommentForm = document.getElementById("edit-comment-form" + commentId);
    editCommentForm.hidden = false;
};

const hideEditCommentForm = (event) => {
    const commentId = event.target.getAttribute("id").match(/\d+/)[0];
    const editCommentForm = document.getElementById("edit-comment-form" + commentId);
    editCommentForm.hidden = true;
    const showEditCommentFormButton = document.getElementById("edit-comment-button" + commentId);
    showEditCommentFormButton.hidden = false;
};