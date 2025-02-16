document.addEventListener('DOMContentLoaded', function() {
    const fileInput = document.querySelector('input[type="file"]');
    const maxSize = 5 * 1024 * 1024; // 5MB in bytes

    fileInput.addEventListener('change', function(e) {
        const files = e.target.files;
        const largerFiles = [];

        // Check each file
        for (let i = 0; i < files.length; i++) {
            const file = files[i];

            if (file.size > maxSize) {
                largerFiles.push(file.name);
            }
        }

        if (largerFiles.length > 0) {
            const message = `The following files are too large (maximum 5MB):\n\n` +
                           largerFiles.join('\n');
            alert(message);
            e.target.value = ''; // Clear the input
        }
    });
});