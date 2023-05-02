const inputForEditGroupPhotoURL = document.getElementById("edit-group-photo-url");


const openFileStackModalEditGroup = () => {
    const options2 = {
        onFileUploadFinished(file) {
            inputForEditGroupPhotoURL.value = file.url;
        }
    }
    client.picker(options2).open();
};
