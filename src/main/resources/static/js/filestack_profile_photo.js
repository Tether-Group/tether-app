const inputForPhotoURL = document.getElementById("profile-photo-url");
const displayForCurrentPhoto = document.getElementById("profile-photo-display");


const openFileStackModalProfile = () => {
    const options3 = {
        onFileUploadFinished(file) {
            inputForPhotoURL.value = file.url;
            displayForCurrentPhoto.src = file.url;
        }
    }
    client.picker(options3).open();
};
