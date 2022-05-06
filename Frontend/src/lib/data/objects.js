export let currentData;

export async function getAllObjects() {

    let response = await fetch("http://localhost:8089/api/v1/object/all", {
        method: "GET",
        headers: {'Content-Type': 'application/json'}
    });

    if (response.ok) {
        let json = await response.json();
        console.log(json);
        return json;
    } else {
        alert(response.status);
    }

}

export async function getCurrentDataByObjectID(marker) {

    const loc = marker.marker.getLatLng();
    const id = marker.objectID;

    if (id == undefined) {
        await dataForNewObject(loc);
        marker.objectID = currentData.id;
    } else {
        let getResponse = await fetch("http://localhost:8089/api/v1/object/id/" + id, {
            method: "GET",
            headers: {'Content-Type': 'application/json'}
        });
        if (getResponse.ok) {
            let json = await getResponse.json();
            console.log("Got Object");
            console.log(json);
            currentData = json;
        } else {
            alert(getResponse.status);
        }
    }

    return currentData;

}

async function dataForNewObject(loc) {

    let data = {
        name: "Neues Objekt",
        latitude: loc.lat,
        longitude: loc.lng,
        years: [],
        images: []
    };

    let response = await fetch("http://localhost:8089/api/v1/object/create", {
        method: "POST",
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(data)
    });
    if (response.ok) {
        let json = await response.json();
        console.log("Created Object");
        console.log(json)
        currentData = json;
    } else {
        alert("Error creating object: " + response.status);
    }

}

export async function uploadObjectData(marker, name) {

    let data = {
        newLatitude: marker.marker.getLatLng().lat,
        newLongitude: marker.marker.getLatLng().lng
    };

    if (name !== undefined) {
        data.newName = name;
    }

    let response = await fetch("http://localhost:8089/api/v1/object/edit/" + marker.objectID, {
        method: "POST",
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(data)
    });

    if (!response.ok) {
        alert(response.status);
    } else {
        console.log("http://localhost:8089/api/v1/object/edit/" + marker.objectID);
        console.log(await response.json());
    }

}

export async function deleteObjectRequest(id) {

    let response = await fetch("http://localhost:8089/api/v1/object/delete/" + id, {
        method: "DELETE"
    });

    if (!response.ok) {
        alert(response.status);
    } 

}

