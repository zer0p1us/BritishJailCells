export function toggleTheme() {
    const htmlElement = document.querySelector('html');
    const currentTheme = htmlElement.getAttribute('data-bs-theme');
    const newTheme = currentTheme === 'dark' ? 'light' : 'dark';
    htmlElement.setAttribute('data-bs-theme', newTheme);

    const iconDark = document.getElementById('icon-dark');
    const iconLight = document.getElementById('icon-light');
    
    if (newTheme === 'dark') {
        iconDark.style.display = 'inline';
        iconLight.style.display = 'none';
        setThemeStorage('dark');
    } else {
        iconDark.style.display = 'none';
        iconLight.style.display = 'inline';
        setThemeStorage('light');
    }
}

export function setThemeStorage(themeValue) {
    fetch('/set_theme', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ theme: themeValue }) });
}