// const pswdCheck = (text, passwordError) => {
//     let condition1 = /(?=.*\d)/; //should contain at least 1 number
//     let condition2 = /(?=.*[a-z])/; //should contain at least 1 lowercase letter
//     let condition3 = /(?=.*[A-Z])/; //should contain at least 1 uppercase letter
//     let condition4 = /[a-zA-Z0-9]{8,}/; //should contain at least 8 characters
//     let condition5 = /[!-\/:-@[-`{-~]/; //should contain 1 special character
//     passwordError.style.color = "red";
//
//     if (!text.match(condition1)) {
//         passwordError.style.display = "block";
//         passwordError.innerText = "Password should contain at least 1 number";
//         return;
//     }
//
//     if (!text.match(condition2)) {
//         passwordError.style.display = "block";
//         passwordError.innerText = "Password should contain at least 1 lowercase letter";
//         return;
//     }
//
//     if (!text.match(condition3)) {
//         passwordError.style.display = "block";
//         passwordError.innerText = "Password should contain at least 1 uppercase letter";
//         return;
//     }
//
//     if (!text.match(condition4)) {
//         passwordError.style.display = "block";
//         passwordError.innerText = "Password should contain at least 8 characters";
//         return;
//     }
//
//     if (!text.match(condition5)) {
//         passwordError.style.display = "block";
//         passwordError.innerText = "Password should contain at least 1 special character";
//         return;
//     }
//
//     passwordError.style.display = "none";
//     return;
// }
//
// let password = document.getElementById("register-password");
// let passwordError = document.getElementById("confirm-pw-error");
// passwordError.style.display = "none";
// password.addEventListener('keypress', event => {
//     let text = password.value + `${event.key}`;
//     pswdCheck(text, passwordError);
// })
