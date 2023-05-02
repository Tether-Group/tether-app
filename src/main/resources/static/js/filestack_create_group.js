const preview = document.getElementById("preview");
const photoText = document.getElementById("no-photo-selected")
const inputForPhotoURL = document.getElementById("group-photo-url");

const client = filestack.init(FILESTACK_API_KEY);
const options = {
    onFileUploadFinished(file) {
        inputForPhotoURL.value = file.url;
        photoText.style.display = "none"
        preview.innerHTML = `<img src="${file.url}" alt="preview">`
    }
}
const openFileStackModal = () => {
    client.picker(options).open();
};
