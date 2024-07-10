document.addEventListener('DOMContentLoaded', function () {
    let imageCounter = 0;

    document.getElementById('add-image-button').addEventListener('click', function () {
        const imageInputsDiv = document.getElementById('image-inputs');

        imageCounter++;

        const newImageDiv = document.createElement('div');
        newImageDiv.classList.add('form-group', 'mb-3');

        const label = document.createElement('label');
        label.setAttribute('for', `images${imageCounter}`);
        label.classList.add('text-white', 'font-weight-bold');
        label.innerText = `URL ${imageCounter}`;

        const input = document.createElement('input');
        input.setAttribute('type', 'text');
        input.setAttribute('name', `images[${imageCounter}]`);
        input.setAttribute('id', `images${imageCounter}`);
        input.classList.add('form-control');

        newImageDiv.appendChild(label);
        newImageDiv.appendChild(input);

        imageInputsDiv.appendChild(newImageDiv);
    });
});