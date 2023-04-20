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
            fetch('/add-profile-photo', {
                method: 'POST',
                body: JSON.stringify({
                    photoURL: fileUrl,

                }),
                headers: {
                    'Content-type': 'application/json; charset=UTF-8',
                }
            }).catch((error) => {
                console.log("OH NO NOT INIT NO MO");
                console.log(error);
            })
            preview.innerHTML = `<img src="${fileUrl}" alt="Preview">`;
        })
        .catch((error) => {
            console.error(error);
        });
});
