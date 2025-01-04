import {toggleTheme, setThemeStorage} from './toggle_theme.js';
import {initializeTristateCheckboxes, cycleState} from './tristate_checkbox.js';
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