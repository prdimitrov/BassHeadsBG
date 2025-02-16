function setupGallery(images) {
    document.addEventListener('DOMContentLoaded', function() {
        let currentIndex = 0;

        const imgElement = document.getElementById('device-image');
        const prevButton = document.getElementById('prev');
        const nextButton = document.getElementById('next');
        const thumbnailsContainer = document.getElementById('thumbnails');

        // Function to update the main image
        function updateImage() {
            if (images.length > 0) {
                imgElement.src = 'data:image/jpeg;base64,' + images[currentIndex];
            } else {
                imgElement.src = '/'; // Fallback image if no images
            }
        }

        // Function to create the image thumbnails
        function createThumbnails() {
            images.forEach((image, index) => {
                const thumbnail = document.createElement('img');
                thumbnail.src = 'data:image/jpeg;base64,' + image; // Add base64 prefix here
                thumbnail.classList.add('img-thumbnail', 'm-1');
                thumbnail.style.width = '100px';
                thumbnail.style.cursor = 'pointer';

                // Click event to change main image
                thumbnail.addEventListener('click', function() {
                    currentIndex = index;
                    updateImage();
                });

                thumbnailsContainer.appendChild(thumbnail);
            });
        }

        // Event listeners for previous and next buttons
        prevButton.addEventListener('click', function() {
            currentIndex = (currentIndex === 0) ? images.length - 1 : currentIndex - 1;
            updateImage();
        });

        nextButton.addEventListener('click', function() {
            currentIndex = (currentIndex === images.length - 1) ? 0 : currentIndex + 1;
            updateImage();
        });

        // Initialize the gallery
        updateImage();
        createThumbnails();
    });
}
