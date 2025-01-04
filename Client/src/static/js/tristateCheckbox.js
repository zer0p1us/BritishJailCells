export function initializeTristateCheckboxes() {
    const tristateCheckboxes = document.querySelectorAll('.tristate-checkbox');
    
    tristateCheckboxes.forEach(checkbox => {
        checkbox.addEventListener('click', function() {
            // Get the target name from the data attribute
            const hiddenInput = document.getElementById(`${this.dataset.target}Value`);
            const newState = cycleState(this.dataset.state);
            
            // Update the visual state
            this.dataset.state = newState;
            hiddenInput.value = newState;
        });
    });
}

export function cycleState(currentState) {
    switch(currentState) {
        case 'none':
            return 'true';
        case 'true':
            return 'false';
        case 'false':
            return 'none';
        default:
            return 'none';
    }
}
