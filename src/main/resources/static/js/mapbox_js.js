"use strict";

function geocode(search, token) {
    var baseUrl = 'https://api.mapbox.com';
    var endPoint = '/geocoding/v5/mapbox.places/';
    return fetch(baseUrl + endPoint + encodeURIComponent(search) + '.json' + "?" + 'access_token=' + token)
        .then(function (res) {
            return res.json();
            // to get all the data from the request, comment out the following three lines...
        }).then(function (data) {
            return data.features[0].center;
        });
}

document.addEventListener("DOMContentLoaded", function () {
    mapboxgl.accessToken = "pk.eyJ1IjoibWFkbWFyY29zIiwiYSI6ImNsNWw0YWNobTBnY3AzanN4dzdpemhpeG0ifQ.OO7BegDPBkP3SV0CaLbnyQ";

    //adds map with starting center point position
    const map = new mapboxgl.Map({
        container: "event-map",
        style: "mapbox://styles/mapbox/outdoors-v12",
        zoom: 0,
        center: [-97.620004, 30.43937]
    });

    //adds built-in mapbox zoom-btns
    map.addControl(new mapboxgl.NavigationControl());

    //adds built-in geolocator onto the map itself with position options
    var geolocateControl = new mapboxgl.GeolocateControl({
        positionOptions: {
            enableHighAccuracy: true
        },
        trackUserLocation: true,
        showUserHeading: true
    });

    //adds the geolocator on the map which sets a marker for the users current location if they accept
    map.addControl(geolocateControl);
    geolocateControl.on("geolocate", function (e) {
        var longitude = e.coords.longitude;
        var latitude = e.coords.latitude
        var result = [longitude, latitude];
        const marker = new mapboxgl.Marker({'color': 'rgba(0,102,255,0.38)',});
        marker.setLngLat(result);
        marker.addTo(map);
        map.setZoom(12);
        map.setCenter(result);
        console.log(result);
    })

    // function uses geocoder to log result and pin input address
    function addMarker(address) {
        geocode(address, "pk.eyJ1IjoibWFkbWFyY29zIiwiYSI6ImNsNWw0YWNobTBnY3AzanN4dzdpemhpeG0ifQ.OO7BegDPBkP3SV0CaLbnyQ")
            .then(function (result) {
                console.log(result);
                const marker = new mapboxgl.Marker({'color': 'rgba(255,0,21,0.65)',});
                marker.setLngLat(result);
                marker.addTo(map);
                map.setZoom(13);
                map.setCenter(result);

                // const popup = new mapboxgl.Popup();
                // popup.setHTML(`<h3 class="text-center fraunces-font">${resul}</h3>
                //         <hr>
                //         <div class="text-center cambay-font">Current Conditions: ${upperCase(weatherData.list[0].weather[0].description)}</div>
                //         <div class="text-center cambay-font">Current Temp: ${Math.round(weatherData.list[0].main.temp)}°F</div>
                //         <div class="text-center cambay-font">Feels like: ${Math.round(weatherData.list[0].main.feels_like)}°F</div>`);
                // marker.setPopup(popup);

            }).catch(function (error) {
            console.log("This location does not exist, please try a different location.");
            // var errorHTML = '';
            // errorHTML = `<div class="control-group error w-50 mx-auto">
            //                 <div class="p-0 my-0 mx-auto text-decoration-underline bg-danger rounded-2">Invalid input</div>
            //             </div>`
            // $('#invalid-feedback').html(errorHTML);
        });
    }
    let eventMarker = document.getElementById("event-address");
    let newEventMarker = eventMarker.innerText;
    console.log(newEventMarker);
    addMarker(newEventMarker);

    // //adding search bar functionality
    // function locationSearch(e) {
    //     e.preventDefault();
    //     let userLocationSearch = document.getElementById("event-address");
    //     let newLocationSearch = (userLocationSearch.focus().val());
    //     console.log(newLocationSearch);
    //     addMarker(newLocationSearch);
    // }

    // document.querySelector('.search-location').addEventListener("click", locationSearch);


});