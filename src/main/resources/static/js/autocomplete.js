let debounceTimer;
let selectedCityData = null;

function initializeAutocomplete() {
    const cityInput = document.getElementById('cityInput');
    const dropdown = document.getElementById('cityDropdown');
    
    if (!cityInput || !dropdown) {
        console.error('City input or dropdown not found');
        return;
    }

    // Add event listeners
    cityInput.addEventListener('input', handleCityInput);
    cityInput.addEventListener('keydown', handleKeyDown);
    cityInput.addEventListener('blur', handleBlur);
    
    // Close dropdown when clicking outside
    document.addEventListener('click', function(event) {
        if (!cityInput.contains(event.target) && !dropdown.contains(event.target)) {
            hideDropdown();
        }
    });
}

function handleCityInput(event) {
    const query = event.target.value.trim();
    
    // Clear previous timer
    clearTimeout(debounceTimer);
    
    if (query.length < 2) {
        hideDropdown();
        return;
    }
    
    // Debounce the API call
    debounceTimer = setTimeout(() => {
        searchCities(query);
    }, 300);
}

function handleKeyDown(event) {
    const dropdown = document.getElementById('cityDropdown');
    const items = dropdown.querySelectorAll('.dropdown-item');
    
    if (items.length === 0) return;
    
    let currentIndex = -1;
    
    // Find currently selected item
    for (let i = 0; i < items.length; i++) {
        if (items[i].classList.contains('active')) {
            currentIndex = i;
            break;
        }
    }
    
    switch (event.key) {
        case 'ArrowDown':
            event.preventDefault();
            currentIndex = currentIndex < items.length - 1 ? currentIndex + 1 : 0;
            updateSelection(items, currentIndex);
            break;
            
        case 'ArrowUp':
            event.preventDefault();
            currentIndex = currentIndex > 0 ? currentIndex - 1 : items.length - 1;
            updateSelection(items, currentIndex);
            break;
            
        case 'Enter':
            event.preventDefault();
            if (currentIndex >= 0) {
                selectCity(items[currentIndex]);
            }
            break;
            
        case 'Escape':
            hideDropdown();
            break;
    }
}

function handleBlur() {
    // Small delay to allow click events on dropdown items
    setTimeout(() => {
        hideDropdown();
    }, 150);
}

function updateSelection(items, selectedIndex) {
    items.forEach((item, index) => {
        if (index === selectedIndex) {
            item.classList.add('active');
        } else {
            item.classList.remove('active');
        }
    });
}

async function searchCities(query) {
    try {
        showLoading();
        
        const response = await fetch(`/search-cities?query=${encodeURIComponent(query)}`);
        
        if (!response.ok) {
            throw new Error('Failed to fetch cities');
        }
        
        const cities = await response.json();
        displayCities(cities);
        
    } catch (error) {
        console.error('Error searching cities:', error);
        hideDropdown();
    }
}

function displayCities(cities) {
    const dropdown = document.getElementById('cityDropdown');
    
    if (cities.length === 0) {
        hideDropdown();
        return;
    }
    
    dropdown.innerHTML = '';
    
    cities.forEach((city, index) => {
        const item = document.createElement('div');
        item.classList.add('dropdown-item');
        item.textContent = city.displayName;
        
        // Store city data for later use
        item.dataset.cityName = city.name;
        item.dataset.country = city.country;
        item.dataset.fullName = city.fullName;
        
        item.addEventListener('click', () => selectCity(item));
        
        // Add hover effects
        item.addEventListener('mouseenter', () => {
            dropdown.querySelectorAll('.dropdown-item').forEach(otherItem => {
                otherItem.classList.remove('active');
            });
            item.classList.add('active');
        });
        
        dropdown.appendChild(item);
    });
    
    showDropdown();
}

function selectCity(item) {
    const cityInput = document.getElementById('cityInput');
    
    // Store selected city data
    selectedCityData = {
        name: item.dataset.cityName,
        country: item.dataset.country,
        fullName: item.dataset.fullName
    };
    
    // Update input value
    cityInput.value = item.textContent;
    
    // Update hidden input for form submission
    const hiddenInput = document.getElementById('selectedCity');
    if (hiddenInput) {
        hiddenInput.value = selectedCityData.fullName;
    }
    
    hideDropdown();
}

function showDropdown() {
    const dropdown = document.getElementById('cityDropdown');
    dropdown.style.display = 'block';
    dropdown.classList.add('show');
}

function hideDropdown() {
    const dropdown = document.getElementById('cityDropdown');
    dropdown.style.display = 'none';
    dropdown.classList.remove('show');
}

function showLoading() {
    const dropdown = document.getElementById('cityDropdown');
    dropdown.innerHTML = '<div class="dropdown-item loading">Searching cities...</div>';
    showDropdown();
}

// Initialize when DOM is loaded
document.addEventListener('DOMContentLoaded', initializeAutocomplete);
