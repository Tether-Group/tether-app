const inputForPhotoURL = document.getElementById("profile-photo-url");
const displayForCurrentPhoto = document.getElementById("profile-photo-display");


const openFileStackModalEditGroup = () => {
    const options2 = {
        onFileUploadFinished(file) {
            inputForPhotoURL.value = file.url;
            displayForCurrentPhoto.src = file.url;
        }
    }
    client.picker(options2).open();
};
