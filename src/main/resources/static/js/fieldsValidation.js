$(document).ready(function() {
    // Placeholder management for all input types
    $('input[type="number"], input[type="text"]').each(function() {
        var value = $(this).val();
        var placeholderText = $(this).attr('placeholder');

        // Check if the value is empty or '0' or '0.0' (for number inputs)
        if (!value || ($(this).attr('type') === 'number' && parseFloat(value) === 0)) {
            $(this).attr('placeholder', placeholderText);
            $(this).val(''); // Clear the field if it's '0' or empty
        }
    });

    // Form validation on submit
    $('form').submit(function(event) {
        var valid = true;

        // Validate number inputs
        $('input[type="number"]').each(function() {
            if (!$(this).val()) {
                $(this).addClass('bg-warning');
                valid = false;
            }
        });

        //Validate text inputs
        $('input[type="text"]').each(function() {
            if (!$(this).val()) {
                $(this).addClass('bg-warning');
                valid = false;
            }
        });

        if (!valid) {
            event.preventDefault(); // Prevent form submission if validation fails
        }
    });
});