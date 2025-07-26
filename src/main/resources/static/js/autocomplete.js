// Popular cities with their country codes and display names
const cities = [
    { value: "Melbourne,AU", display: "Melbourne, Australia" },
    { value: "Melbourne,US", display: "Melbourne, United States" },
    { value: "Sydney,AU", display: "Sydney, Australia" },
    { value: "Sydney,CA", display: "Sydney, Canada" },
    { value: "Perth,AU", display: "Perth, Australia" },
    { value: "Perth,GB", display: "Perth, United Kingdom" },
    { value: "Adelaide,AU", display: "Adelaide, Australia" },
    { value: "Brisbane,AU", display: "Brisbane, Australia" },
    { value: "Canberra,AU", display: "Canberra, Australia" },
    { value: "Darwin,AU", display: "Darwin, Australia" },
    { value: "Hobart,AU", display: "Hobart, Australia" },
    { value: "London,GB", display: "London, United Kingdom" },
    { value: "London,CA", display: "London, Canada" },
    { value: "London,US", display: "London, United States" },
    { value: "Paris,FR", display: "Paris, France" },
    { value: "Paris,US", display: "Paris, United States" },
    { value: "New York,US", display: "New York, United States" },
    { value: "Los Angeles,US", display: "Los Angeles, United States" },
    { value: "Chicago,US", display: "Chicago, United States" },
    { value: "Toronto,CA", display: "Toronto, Canada" },
    { value: "Vancouver,CA", display: "Vancouver, Canada" },
    { value: "Montreal,CA", display: "Montreal, Canada" },
    { value: "Tokyo,JP", display: "Tokyo, Japan" },
    { value: "Beijing,CN", display: "Beijing, China" },
    { value: "Shanghai,CN", display: "Shanghai, China" },
    { value: "Mumbai,IN", display: "Mumbai, India" },
    { value: "Delhi,IN", display: "Delhi, India" },
    { value: "Bangkok,TH", display: "Bangkok, Thailand" },
    { value: "Singapore,SG", display: "Singapore" },
    { value: "Berlin,DE", display: "Berlin, Germany" },
    { value: "Munich,DE", display: "Munich, Germany" },
    { value: "Rome,IT", display: "Rome, Italy" },
    { value: "Milan,IT", display: "Milan, Italy" },
    { value: "Madrid,ES", display: "Madrid, Spain" },
    { value: "Barcelona,ES", display: "Barcelona, Spain" },
    { value: "Amsterdam,NL", display: "Amsterdam, Netherlands" },
    { value: "Stockholm,SE", display: "Stockholm, Sweden" },
    { value: "Oslo,NO", display: "Oslo, Norway" },
    { value: "Copenhagen,DK", display: "Copenhagen, Denmark" },
    { value: "Helsinki,FI", display: "Helsinki, Finland" },
    { value: "Dublin,IE", display: "Dublin, Ireland" },
    { value: "Edinburgh,GB", display: "Edinburgh, United Kingdom" },
    { value: "Manchester,GB", display: "Manchester, United Kingdom" },
    { value: "Birmingham,GB", display: "Birmingham, United Kingdom" },
    { value: "Birmingham,US", display: "Birmingham, United States" }
];

let currentFocus = -1;

function initAutocomplete() {
    const inp = document.getElementById("cityInput");
    const autocompleteList = document.getElementById("autocomplete-list");

    if (!inp || !autocompleteList) return;

    inp.addEventListener("input", function(e) {
        const val = this.value;
        closeAllLists();
        if (!val) return false;
        currentFocus = -1;

        // Filter cities based on input
        const filteredCities = cities.filter(city => 
            city.display.toLowerCase().includes(val.toLowerCase()) ||
            city.value.toLowerCase().includes(val.toLowerCase())
        ).slice(0, 8); // Show max 8 suggestions

        if (filteredCities.length === 0) return false;

        filteredCities.forEach((city, index) => {
            const item = document.createElement("div");
            item.innerHTML = `<span class="city-name">${city.display}</span>`;
            item.addEventListener("click", function(e) {
                inp.value = city.value;
                closeAllLists();
            });
            autocompleteList.appendChild(item);
        });
    });

    inp.addEventListener("keydown", function(e) {
        const items = autocompleteList.getElementsByTagName("div");
        if (e.keyCode === 40) { // DOWN
            currentFocus++;
            addActive(items);
        } else if (e.keyCode === 38) { // UP
            currentFocus--;
            addActive(items);
        } else if (e.keyCode === 13) { // ENTER
            e.preventDefault();
            if (currentFocus > -1 && items[currentFocus]) {
                items[currentFocus].click();
            }
        }
    });

    function addActive(items) {
        if (!items) return false;
        removeActive(items);
        if (currentFocus >= items.length) currentFocus = 0;
        if (currentFocus < 0) currentFocus = (items.length - 1);
        items[currentFocus].classList.add("autocomplete-active");
    }

    function removeActive(items) {
        for (let i = 0; i < items.length; i++) {
            items[i].classList.remove("autocomplete-active");
        }
    }

    function closeAllLists() {
        autocompleteList.innerHTML = "";
    }

    // Close autocomplete when clicking outside
    document.addEventListener("click", function (e) {
        if (e.target !== inp) {
            closeAllLists();
        }
    });
}

// Initialize autocomplete when page loads
document.addEventListener("DOMContentLoaded", initAutocomplete);
