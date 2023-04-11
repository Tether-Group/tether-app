"use strict";

let myInput = $('#register-password');
let lowercase = $('#lowercase');
let uppercase = $('#uppercase');
let number = $('#number');
let length = $('#length');
let special = $('#special');
let noSpace = $('#empty');
let mySecondInput = $('#register-pw-verification');

// let passwordInvalid = true;
// let disableBtn = !myInput.value.length && passwordInvalid;

// When the user clicks on the password field, show the message box
myInput.onfocus = function () {
    $('#message').css('display', 'initial');
}

// When the user clicks outside the password field, hide the message box
myInput.onblur = function () {
    $('#message').css('display', 'none');
}

// When the user starts to type something inside the password field
myInput.onkeyup = function () {
    // Validate lowercase letters
    let lowerCaseLetters = /[a-z]/g;
    if (myInput.value.match(lowerCaseLetters)) {
        lowercase.classList.remove("invalid");
        lowercase.classList.add("valid");
    } else {
        lowercase.classList.remove("valid");
        lowercase.classList.add("invalid");
    }

    // Validate capital letters
    let upperCaseLetters = /[A-Z]/g;
    if (myInput.value.match(upperCaseLetters)) {
        uppercase.classList.remove("invalid");
        uppercase.classList.add("valid");
    } else {
        uppercase.classList.remove("valid");
        uppercase.classList.add("invalid");
    }

    // Validate numbers
    let numbers = /[0-9]/g;
    if (myInput.value.match(numbers)) {
        number.classList.remove("invalid");
        number.classList.add("valid");
    } else {
        number.classList.remove("valid");
        number.classList.add("invalid");
    }

    // Validate length
    if (myInput.value.length >= 8 && myInput.value.length <= 20) {
        length.classList.remove("invalid");
        length.classList.add("valid");
    } else {
        length.classList.remove("valid");
        length.classList.add("invalid");
    }

    // Validate special characters
    let specialChars = /[ !"#$%&'()*+,-./:;<=>?@[\\\]^_`{|}~]/g;
    if (myInput.value.match(specialChars)) {
        special.classList.remove("invalid");
        special.classList.add("valid");
    } else {
        special.classList.remove("valid");
        special.classList.add("invalid");
    }

    // Validate empty spaces
    let emptySpaces = /\s/g;
    if (!myInput.value.match(emptySpaces)) {
        noSpace.classList.remove('invalid');
        noSpace.classList.add('valid');
    } else {
        noSpace.classList.remove('valid');
        noSpace.classList.add('invalid');
    }

    // Validate verification pw
    if (myInput.value.match(mySecondInput)) {
        mySecondInput.classList.remove('invalid');
        mySecondInput.classList.add('valid');
    } else {
        mySecondInput.classList.remove('valid');
        mySecondInput.classList.add('invalid');
    }

    // if (myInput.value.match(lowerCaseLetters)
    //     && myInput.value.match(upperCaseLetters)
    //     && myInput.value.match(numbers)
    //     && myInput.value.length >= 8
    //     && myInput.value.length <= 20
    //     && myInput.value.match(specialChars)
    //     && !myInput.value.match(emptySpaces)
    //     && myInput.value.match(mySecondInput)) {
    //     passwordInvalid = false;
    // } else {
    //     passwordInvalid = true;
    // }
    //
    // disableBtn = !myInput.value.length && passwordInvalid;
}