// const preview = document.getElementById("preview");
// const photoText = document.getElementById("no-photo-selected")
const inputForGroupPhotoURL = document.getElementById("group-photo-url");

const client = filestack.init(FILESTACK_API_KEY);

const openFileStackModalCreateGroup = () => {
    const options = {
        onFileUploadFinished(file) {
            inputForGroupPhotoURL.value = file.url;
            // photoText.style.display = "none"
            // preview.innerHTML = `<img src="${file.url}" alt="preview">`
        }
    }
    client.picker(options).open();
};
