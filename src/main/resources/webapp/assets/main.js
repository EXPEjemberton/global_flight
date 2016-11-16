var t = document.querySelector('template');
// t.apiKey = 'AIzaSyDbPb1xhQFgesHR7kcmlzzYvaEsHx0jGR4';
if (location.origin === 'https://user-content-dot-custom-elements.appspot.com') {
    t.apiKey = 'AIzaSyD3E1D9b-Z7ekrT3tbhl_dy8DCXuIuDDRc'; // TODO: update to your own API Key!
}

document.addEventListener('dom-change', function (e) {
    geoip2.city((loc) => {
        t.locLatitude = loc.location.latitude;
        t.locLongitude = loc.location.longitude;
        t.locCityName = loc.city.names.en;
    }, (err) => {
        console.error('error in finding location');
    });

    var gmap = document.querySelector('google-map');

    var marker = document.querySelector('google-map-marker');

    marker.addEventListener('google-map-marker-dragend', function (e) {
        console.log('pin dropped for fufture', latLng.lat(), latLng.lng());
    });
});
