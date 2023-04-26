const inputForPhotoURL = document.getElementById("profile-photo-url");
const displayFileName = document.getElementById("display-file-name");
// const preview = document.getElementById("preview");

const client = filestack.init(FILESTACK_API_KEY);
const options = {
    onFileUploadFinished(file) {
        inputForPhotoURL.value = file.url;
        displayFileName.innerText = "Uploaded image. Press \"Save\" to save profile photo.";
        // console.log(preview)
        // preview.innerHTML = `<img src="${fileUrl}" alt="preview">`
    }
}
const openFileStackModalProfile = () => {
    client.picker(options).open();
};
