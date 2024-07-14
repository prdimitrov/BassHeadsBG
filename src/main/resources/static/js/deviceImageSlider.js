function setupGallery(images) {
    document.addEventListener('DOMContentLoaded', function() {
        let currentIndex = 0;

        const imgElement = document.getElementById('device-image');
        const prevButton = document.getElementById('prev');
        const nextButton = document.getElementById('next');
        const thumbnailsContainer = document.getElementById('thumbnails');

        function updateImage() {
            if (images.length > 0) {
                imgElement.src = images[currentIndex];
            } else {
                imgElement.src = '/'; // Fallback image
            }
        }

        function createThumbnails() {
            images.forEach((image, index) => {
                const thumbnail = document.createElement('img');
                thumbnail.src = image;
                thumbnail.classList.add('img-thumbnail', 'm-1');
                thumbnail.style.width = '100px';
                thumbnail.style.cursor = 'pointer';

                thumbnail.addEventListener('click', function() {
                    currentIndex = index;
                    updateImage();
                });

                thumbnailsContainer.appendChild(thumbnail);
            });
        }

        prevButton.addEventListener('click', function() {
            currentIndex = (currentIndex === 0) ? images.length - 1 : currentIndex - 1;
            updateImage();
        });

        nextButton.addEventListener('click', function() {
            currentIndex = (currentIndex === images.length - 1) ? 0 : currentIndex + 1;
            updateImage();
        });

        updateImage(); // Initial call to set the first image
        createThumbnails(); // Create thumbnails
    });
}