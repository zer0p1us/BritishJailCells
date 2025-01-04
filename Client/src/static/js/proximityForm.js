export function calculateProximity(formSelector, durationFieldSelector, distanceFieldSelector) {
    $(document).on('submit', formSelector, function(event) {
        event.preventDefault(); // Prevent default form submission
        
        const form = this;
        const url = $(form).attr('action');
        const formData = $(form).serialize(); // Serialize form data

        $.ajax({
            type: 'GET',
            url: url,
            data: formData, // Send the serialized form data
            success: function(response) {
                $(durationFieldSelector).val(response.durationSeconds);
                $(distanceFieldSelector).val(response.distanceMeters);
            },
            error: function() {
                alert('An error occurred while fetching proximity data.');
            }
        });
    });
}
