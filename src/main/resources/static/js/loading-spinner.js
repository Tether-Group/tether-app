"use strict";

document.onreadystatechange = function() {
    const loadingSpinner = document.getElementById("loading-spinner");
    if (document.readyState !== "complete") {
        loadingSpinner.hidden = false;
    } else {
        loadingSpinner.hidden = true;
    }
};