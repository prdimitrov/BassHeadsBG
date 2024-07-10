document.addEventListener('DOMContentLoaded', function() {
    const images = '/*[[${highRangeDetails.images}]]*/ []; // Thymeleaf syntax to inject images array
    let currentIndex = 0;

    const imgElement = document.getElementById('device-image');
    const prevButton = document.getElementById('prev');
    const nextButton = document.getElementById('next');

    function updateImage() {
        if (images.length > 0) {
            imgElement.src = images[currentIndex];
        } else {
            imgElement.src = '/'; // Fallback image
        }
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
});