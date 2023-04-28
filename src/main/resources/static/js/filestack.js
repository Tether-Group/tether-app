const inputForPhotoURL = document.getElementById("profile-photo-url");
const displayForCurrentPhoto = document.getElementById("profile-photo-display");

const client = filestack.init(FILESTACK_API_KEY);
const options = {
    onFileUploadFinished(file) {
        inputForPhotoURL.value = file.url;
        displayForCurrentPhoto.src = file.url;
    }
}
const openFileStackModalProfile = () => {
    client.picker(options).open();
};
