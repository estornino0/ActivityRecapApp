document.getElementById('date-form').addEventListener('submit', function(event) {
    event.preventDefault();

    var startDate = new Date(document.getElementById('start_date').value);
    var endDate = new Date(document.getElementById('end_date').value);

    if (startDate > endDate) {
        alert('The end date cannot be before the start date.');
        return;
    }

    var data = {
        after: startDate.toISOString(),
        before: endDate.toISOString()
    };

    fetch('http://localhost:8080/activityRecap', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(response => response.json())
    .then(result => {
        document.getElementById('activities').textContent = `${result.numberOfActivities}`;
        document.getElementById('distance').textContent = `${Math.floor(result.totalDistanceInKm)} Km`;
        document.getElementById('duration').textContent = `${Math.floor(result.totalTimeInHs)} Hs`;
        createHeatZones(result.polylines);
    })
    .catch(error => {
        console.error('Error:', error);
        document.getElementById('response').innerHTML = `
            <h2>Error</h2>
            <pre>${error.message}</pre>
        `;
    });
});

document.addEventListener('DOMContentLoaded', () => {
    const startDateInput = document.getElementById('start_date');
    const endDateInput = document.getElementById('end_date');
    const predefinedRanges = document.getElementById('predefined-ranges');

    predefinedRanges.addEventListener('change', (event) => {
        const value = event.target.value;
        let startDate, endDate;

        const today = new Date();
        const thisYear = today.getFullYear();

        switch (value) {
            case 'last-year':
                startDate = new Date(thisYear - 1, 0, 1);
                endDate = new Date(thisYear - 1, 11, 31);
                break;
            case 'this-year':
                startDate = new Date(thisYear, 0, 1);
                endDate = today;
                break;
            case 'last-three-month':
                startDate = new Date(thisYear, today.getMonth() - 3, 1);
                endDate = new Date(thisYear, today.getMonth(), 0);
                break;
            default:
                // Clear dates if custom interval is selected
                startDateInput.value = '';
                endDateInput.value = '';
                return;
        }

        // Format dates as YYYY-MM-DD for input fields
        const formatDate = (date) => date.toISOString().split('T')[0];
        startDateInput.value = formatDate(startDate);
        endDateInput.value = formatDate(endDate);
    });
});

function createHeatMap(){
    var map = L.map('map').setView([-34.54749451325281, -58.45645330694566], 13);

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: 'Map data © <a href="https://openstreetmap.org">OpenStreetMap</a> contributors'
    }).addTo(map);

    var heat = L.heatLayer([], {
        radius: 5,
        blur: 15,
        maxZoom: 17,
    }).addTo(map);

    return { map: map, heat: heat };
}

function createHeatZones(polylines){
    // Clean map
    heatmap.heat.setLatLngs([]);

    for(var i = 0; i < polylines.length; i++){
        addPolyline(polylines[i]);
    }
}

function addPolyline(polyline) {
    coordinates =  L.PolylineUtil.decode(polyline);
    for (var i = 0; i < coordinates.length; i++) {
        heatmap.heat.addLatLng(coordinates[i]);
    }
}


var heatmap = createHeatMap();