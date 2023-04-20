"use strict";

let myInput = document.getElementById('register-password');
let lowercase = document.getElementById('lowercase');
let uppercase = document.getElementById('uppercase');
let number = document.getElementById('number');
let length = document.getElementById('length');
let special = document.getElementById('special');
let noSpace = document.getElementById('empty');
let pwMatch = document.getElementById('password-match');
let asperand = document.getElementById('asperand-register');
let domain = document.getElementById('.com-register');
let myEmail = document.getElementById('register-email');
let mySecondInput = document.getElementById('register-pw-verification');

let lowerCaseLetters = /[a-z]/g;
let upperCaseLetters = /[A-Z]/g;
let numbers = /[0-9]/g;
let specialChars = /[ !"#$%&'()*+,-./:;<=>?@[\\\]^_`{|}~]/g;
let emptySpaces = /\s/g;
let domainName = ".com";
let emailAsperandCheck = "@";

// let passwordInvalid = true;
// let disableBtn = !myInput.value.length && passwordInvalid;

document.getElementById('submit-pw').disabled = true;

// When the user clicks on the password field, show the message box
myInput.onfocus = function () {
    document.getElementById('message2').style.display = 'block';
}

mySecondInput.onfocus = function () {
    document.getElementById('message2').style.display = 'block';
}

myInput.onblur = function () {
    document.getElementById('message2').style.display = 'block';
}

mySecondInput.onblur = function () {
    document.getElementById('message2').style.display = 'block';
}

myEmail.onfocus = function () {
    document.getElementById('message1').style.display = 'block';
}

myEmail.onblur = function () {
    document.getElementById('message1').style.display = 'block';
}

myEmail.onkeyup = function () {
    emailValidate();

    function emailValidate() {
        //validate domain .com
        if (myEmail.value.includes(domainName)) {
            domain.classList.remove("invalid");
            domain.classList.add("valid");
        } else {
            domain.classList.remove("valid");
            domain.classList.add("invalid");
        }

        //validate asperand check
        if (myEmail.value.includes(emailAsperandCheck)) {
            asperand.classList.remove("invalid");
            asperand.classList.add("valid");
        } else {
            asperand.classList.remove("valid");
            asperand.classList.add("invalid");
        }
    }
}

// When the user starts to type something inside the password field
myInput.onkeyup = function () {
    initialValidate();
    finalValidate();

    mySecondInput.onkeyup = function () {
        finalValidate();
    }

    function initialValidate() {
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
            && myInput.value === mySecondInput.value
            && myEmail.value.includes(domainName)
            && myEmail.value.includes(emailAsperandCheck)) {
            document.getElementById('submit-pw').disabled = false;
        } else {
            document.getElementById('submit-pw').disabled = true;
        }
    }
}
