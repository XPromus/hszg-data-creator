import { getServerData } from './serverConfig';

export let currentData;

export async function getAllObjects() {

    const serverData = await getServerData();
    // @ts-ignore
    const url = serverData.serverUrl + serverData.port + "/api/v1/object/all";
    let response = await fetch(url, {
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

    const serverData = await getServerData();
    const loc = marker.marker.getLatLng();
    const id = marker.objectID;

    if (id == undefined) {
        await dataForNewObject(loc);
        marker.objectID = currentData.id;
    } else {
        // @ts-ignore
        const url = serverData.serverUrl + serverData.port + "/api/v1/object/id/" + id;
        let getResponse = await fetch(url, {
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

    const serverData = await getServerData();

    let data = {
        name: "Neues Objekt",
        latitude: loc.lat,
        longitude: loc.lng,
        years: [],
        images: []
    };

    // @ts-ignore
    const url = serverData.serverUrl + serverData.port + "/api/v1/object/create";
    let response = await fetch(url, {
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

    const serverData = await getServerData();

    let data = {
        newLatitude: marker.marker.getLatLng().lat,
        newLongitude: marker.marker.getLatLng().lng
    };

    if (name !== undefined) {
        data.newName = name;
    }

    // @ts-ignore
    const url = serverData.serverUrl + serverData.port + "/api/v1/object/edit/" + marker.objectID
    let response = await fetch(url, {
        method: "POST",
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(data)
    });

    if (!response.ok) {
        alert(response.status);
    } else {
        // @ts-ignore
        console.log(serverData.serverUrl + serverData.port + "/api/v1/object/edit/" + marker.objectID);
        console.log(await response.json());
    }

}

export async function deleteObjectRequest(id) {

    const serverData = await getServerData();

    // @ts-ignore
    const url = serverData.serverUrl + serverData.port + "/api/v1/object/delete/" + id;
    let response = await fetch(url, {
        method: "DELETE"
    });

    if (!response.ok) {
        alert(response.status);
    } 

}

