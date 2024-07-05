function initializeFloatInputValidation() {
    var floatFields = document.querySelectorAll('input[type="number"]');
    floatFields.forEach(function(field) {
        field.addEventListener('input', function() {
            if (this.value && !/^(\d+(\.\d*)?|\.\d+)$/.test(this.value)) {
                this.setCustomValidity('Please enter a valid number.');
            } else {
                this.setCustomValidity('');
            }
        });
    });
}

document.addEventListener('DOMContentLoaded', function() {
    initializeFloatInputValidation();
});