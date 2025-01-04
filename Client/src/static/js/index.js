import {toggleTheme, setThemeStorage} from './toggleTheme.js';
import {initializeTristateCheckboxes, cycleState} from './tristateCheckbox.js';
import {loadGeoCodingResults} from './geoCodingForm.js';

// theme toggle setup
window.toggleTheme = toggleTheme;

// tristate checkbox setup
document.addEventListener('DOMContentLoaded', function() {
    window.initializeTristateCheckboxes = initializeTristateCheckboxes;
    window.initializeTristateCheckboxes();
});

// geocoding form setup
$(document).ready(function() {
    loadGeoCodingResults('#geocodingForm', '#geocodingLatitudeField', '#geocodingLongitudeField');
});