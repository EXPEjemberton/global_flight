var t,
    fromLocationInput,
    toLocationInput,
    fromDateInput,
    closestAirport;

// t.apiKey = 'AIzaSyDbPb1xhQFgesHR7kcmlzzYvaEsHx0jGR4';
if (location.origin === 'https://user-content-dot-custom-elements.appspot.com') {
    t.apiKey = 'AIzaSyD3E1D9b-Z7ekrT3tbhl_dy8DCXuIuDDRc'; // TODO: update to your own API Key!
}

document.addEventListener('dom-change', function () {
    fromLocationInput = document.getElementById('fromLocation');
    toLocationInput = document.getElementById('toLocation');
    fromDateInput = document.getElementById('fromDate');
});

document.addEventListener('DOMContentLoaded', function () {
    t = document.querySelector('template');

    geoip2.city((loc) => {
        t.locLatitude = loc.location.latitude;
        t.locLongitude = loc.location.longitude;
        setCurrentLocation(loc);
        listenToDateChangeEvents();
        listenToLocationChangeEvents();
    }, (err) => {
        console.error('error in finding location');
    });
    // var gmap = document.querySelector('google-map');
    //
    // var marker = document.querySelector('google-map-marker');
    //
    // marker.addEventListener('google-map-marker-dragend', function (e) {
    //     console.log('pin dropped for fufture', latLng.lat(), latLng.lng());
    // });
});

function setCurrentLocation(loc) {
    var url = new URL("/find_nearest_city", window.location.origin),
        params = {
            latitude: loc.location.latitude,
            longitude: loc.location.longitude
        };
    Object.keys(params).forEach(key => url.searchParams.append(key, params[key]));
    fetch(url).then((res) => {
        return res.json()
    }).then((data) => {
        closestAirport = data.name;
        t.locCityName = data.name;
    });
}

function listenToLocationChangeEvents() {
    fromLocationInput.addEventListener('change', (e) => {
        if (fromLocationInput.value !== '') {
            updateDestinations();
        }
    });
    fromLocationInput.inputElement.addEventListener('blur', (e) => {
        if (fromLocationInput.value === '') {
            fromLocationInput.value = closestAirport;
        }
        updateDestinations();
    });
}

function listenToDateChangeEvents() {
    t.fromDate = new Date().toJSON().slice(0, 10); // default date
    fromDateInput.addEventListener('value-changed', (e) => {
        if (fromDateInput.value === '') {
            fromDateInput.value = new Date().toJSON().slice(0, 10);
        }
        updateDestinations();
    });
}

function updateDestinations() {
    var url = new URL("/get_flights", window.location.origin),
        forDate = new Date(fromDateInput.value),
        params = {
            origin: fromLocationInput.value,
            dest: toLocationInput.value,
            year: forDate.getFullYear(),
            month: forDate.getMonth() + 1,
            day: forDate.getDate()
        };
    Object.keys(params).forEach(key => url.searchParams.append(key, params[key]));
    fetch(url).then((res) => {
        return res.json()
    }).then((data) => {
        console.log(data);
    });
}
