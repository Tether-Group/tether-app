"use strict";

let myInput = document.getElementById('register-password');
let lowercase = document.getElementById('lowercase');
let uppercase = document.getElementById('uppercase');
let number = document.getElementById('number');
let length = document.getElementById('length');
let special = document.getElementById('special');
let noSpace = document.getElementById('empty');
let pwMatch = document.getElementById('password-match');
let mySecondInput = document.getElementById('register-pw-verification');

let lowerCaseLetters = /[a-z]/;
let upperCaseLetters = /[A-Z]/g;
let numbers = /[0-9]/g;
let specialChars = /[ !"#$%&'()*+,-./:;<=>?@[\\\]^_`{|}~]/g;
let emptySpaces = /\s/g;

// let passwordInvalid = true;
// let disableBtn = !myInput.value.length && passwordInvalid;

document.getElementById('submit').disabled = true;

// When the user clicks on the password field, show the message box
myInput.onfocus = function () {
    document.getElementById('message').style.display = 'block';
    // $('#message').css('display', 'initial');
}

mySecondInput.onfocus = function () {
    document.getElementById('message').style.display = 'block';
    // $('#message').css('display', 'initial');
}

// When the user clicks outside the password field, hide the message box
myInput.onblur = function () {
    document.getElementById('message').style.display = 'none';
    // $('#message').css('display', 'none');
}

mySecondInput.onblur = function () {
    document.getElementById('message').style.display = 'none';
    // $('#message').css('display', 'none');
}

// When the user starts to type something inside the password field
myInput.onkeyup = function () {
    // Validate lowercase letters
    if (myInput.value.match(lowerCaseLetters)) {
        lowercase.classList.remove("invalid");
        lowercase.classList.add("valid");
    } else {
        lowercase.classList.remove("valid");
        lowercase.classList.add("invalid");
    }

    // Validate capital letters
    if (myInput.value.match(upperCaseLetters)) {
        uppercase.classList.remove("invalid");
        uppercase.classList.add("valid");
    } else {
        uppercase.classList.remove("valid");
        uppercase.classList.add("invalid");
    }

    // Validate numbers
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
    if (myInput.value.match(specialChars)) {
        special.classList.remove("invalid");
        special.classList.add("valid");
    } else {
        special.classList.remove("valid");
        special.classList.add("invalid");
    }

    // Validate empty spaces
    if (!myInput.value.match(emptySpaces)) {
        noSpace.classList.remove('invalid');
        noSpace.classList.add('valid');
    } else {
        noSpace.classList.remove('valid');
        noSpace.classList.add('invalid');
    }

    // Validate verification pw
    finalValidate();

    mySecondInput.onkeyup = function () {
        finalValidate();
    }


    function finalValidate() {
        // Validate verification pw
        if (myInput.value === mySecondInput.value) {
            pwMatch.classList.remove('invalid');
            pwMatch.classList.add('valid');
        } else {
            pwMatch.classList.remove('valid');
            pwMatch.classList.add('invalid');
        }

        if (myInput.value.match(lowerCaseLetters)
            && myInput.value.match(upperCaseLetters)
            && myInput.value.match(numbers)
            && myInput.value.length >= 8
            && myInput.value.length <= 20
            && myInput.value.match(specialChars)
            && !myInput.value.match(emptySpaces)
            && myInput.value === mySecondInput.value) {
            document.getElementById('submit').disabled = false;
        } else {
            document.getElementById('submit').disabled = true;
        }
    }
}
