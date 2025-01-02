function toggleTheme() {
    const htmlElement = document.querySelector('html');
    const currentTheme = htmlElement.getAttribute('data-bs-theme');
    const newTheme = currentTheme === 'dark' ? 'light' : 'dark';
    htmlElement.setAttribute('data-bs-theme', newTheme);

    const iconDark = document.getElementById('icon-dark');
    const iconLight = document.getElementById('icon-light');
    
    if (newTheme === 'dark') {
        iconDark.style.display = 'inline';
        iconLight.style.display = 'none';
    } else {
        iconDark.style.display = 'none';
        iconLight.style.display = 'inline';
    }
}