export function loadGeoCodingResults(formSelector, latitudeFieldSelector, longitudeFieldSelector) {
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
                $(latitudeFieldSelector).val(response.latitude);
                $(longitudeFieldSelector).val(response.longitude);
            },
            error: function() {
                alert('An error occurred while fetching geocoding data.');
            }
        });
    });
}