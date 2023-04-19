const client = filestack.init(FILESTACK_API_KEY);

const uploadForm = document.getElementById("uploadForm");
const fileInput = document.getElementById("fileInput");
const preview = document.getElementById("preview");

uploadForm.addEventListener("submit", (event) => {
    event.preventDefault();

    const file = fileInput.files[0];

    client.upload(file)
        .then((response) => {
            const fileUrl = response.url;
            preview.innerHTML = `<img src="${fileUrl}" alt="Preview">`;
        })
        .catch((error) => {
            console.error(error);
        });
});