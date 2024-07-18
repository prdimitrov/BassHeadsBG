// Define imageIndex globally to track the number of image inputs
let imageIndex = document.querySelectorAll('#image-inputs .input-group').length;

// Function to add a new image input field
function addImageInput() {
    let imageInputsContainer = document.getElementById('image-inputs');

    let newInputGroup = document.createElement('div');
    newInputGroup.classList.add('input-group', 'mb-3');

    let inputField = document.createElement('input');
    inputField.setAttribute('type', 'text');
    inputField.setAttribute('name', `images[${imageIndex}]`);
    inputField.setAttribute('class', 'form-control');
    inputField.setAttribute('placeholder', 'URL');

    let appendButton = document.createElement('div');
    appendButton.classList.add('input-group-append');

    let removeButton = document.createElement('button');
    removeButton.setAttribute('type', 'button');
    removeButton.setAttribute('class', 'btn btn-danger');
    removeButton.textContent = 'Remove';
    removeButton.addEventListener('click', function() {
        removeImageInput(removeButton); // Call the removeImageInput function properly
    });

    appendButton.appendChild(removeButton);
    newInputGroup.appendChild(inputField);
    newInputGroup.appendChild(appendButton);
    imageInputsContainer.appendChild(newInputGroup);

    imageIndex++;
}

// Function to remove an image input field
function removeImageInput(button) {
    button.closest('.input-group').remove();
    updateImageInputNames(); // Call the update function if needed
}

// Function to update input names (if needed)
function updateImageInputNames() {
    let imageInputs = document.querySelectorAll('#image-inputs .input-group input');
    imageInputs.forEach((input, index) => {
        input.setAttribute('name', `images[${index}]`);
    });
    imageIndex = imageInputs.length;
}

// Add event listener to the "Add Image" button
document.addEventListener('DOMContentLoaded', function() {
    let addButton = document.getElementById('add-image-button');
    if (addButton) {
        addButton.addEventListener('click', function() {
            addImageInput();
        });
    } else {
        console.error('Element with id "add-image-button" not found.');
    }
});
