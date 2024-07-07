$(document).ready(function() {
    // Select all div elements inside the form, whose class is 'row'
    $('form .row').each(function() {
        // Check if the row contains exactly only one element (field)
        var inputs = $(this).find('input, select, textarea');

        if (inputs.length === 1) {
            // Add classes to center align the input and the text also!
            $(this).addClass('d-flex text-center justify-content-center');
        }
    });
});