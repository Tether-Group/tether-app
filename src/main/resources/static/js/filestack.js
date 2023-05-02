const inputForPhotoURL = document.getElementById("profile-photo-url");
const displayForCurrentPhoto = document.getElementById("profile-photo-display");

const options2 = {
    onFileUploadFinished(file) {
        inputForPhotoURL.value = file.url;
        displayForCurrentPhoto.src = file.url;
    }
}
const openFileStackModalProfile = () => {
    client.picker(options2).open();
};
