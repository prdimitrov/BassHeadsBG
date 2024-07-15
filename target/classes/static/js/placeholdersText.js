$(document).ready(function() {
    $('input[type="number"]').each(function() {
        var value = $(this).val();
        var placeholderText = $(this).attr('placeholder');

        // Check if the the value is empty or 0 or 0,0
        if (!value || parseFloat(value) === 0) {
            $(this).attr('placeholder', placeholderText);
            $(this).val(''); // Clear the field
        }
    });
});