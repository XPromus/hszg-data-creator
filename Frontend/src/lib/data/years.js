import { getServerData } from './serverConfig';

export async function createNewYear(objectID, year) {

    const serverData = await getServerData();

    // @ts-ignore
    const url = serverData.serverUrl + serverData.port + "/api/v1/object/id/" + objectID;
    let getObjectResponse = await fetch(url);
    if (getObjectResponse.ok) {
        // @ts-ignore
        let response = await fetch(serverData.serverUrl + serverData.port + "/api/v1/year/create/" + objectID + "/" + year, {
            method: "POST",
            headers: {'Content-Type': 'application/json'},
        });
        if (response.ok) {
            let json = await response.json();
            console.log(json);
            return json;
        }
    }
}

export async function createYear(objectID, year) {
    
    const serverData = await getServerData();

    // @ts-ignore
    const url = serverData.serverUrl + serverData.port + "/api/v1/object/id/" + objectID
    let getObjectResponse = await fetch(url);
    if (getObjectResponse.ok) {

        let json = await getObjectResponse.json();
        let years = json.years;

        for (let i = 0; i < years.length; i++) {

            let getYear = years[i].year;

            if (year == getYear) {
                return years[i];
            }

        }

    }

    // @ts-ignore
    let response = await fetch(serverData.serverUrl + serverData.port + "/api/v1/year/create/" + objectID + "/" + year, {
        method: "POST",
        headers: {'Content-Type': 'application/json'},
    });

    if (response.ok) {
        let json = await response.json();
        console.log(json);
        return json;
    } else {
        alert(response.statusText);
    }

}

export async function editYearData(id, data) {

    const serverData = await getServerData();

    let newData = {};
    
    const newYear = data.year;
    if (newYear !== undefined) {
        newData.newYear = newYear;
    }

    const newObjectName = data.objectName;
    if (newObjectName !== undefined) {
        newData.newObjectName = newObjectName;
    }

    const newExists = data.exists;
    if (newExists !== undefined) {
        newData.newExists = newExists;
    }

    const newModelID = data.modelID;
    if (newModelID !== undefined) {
        newData.newModelID = newModelID;
    }

    const newTextureID = data.textureID;
    if (newTextureID !== undefined) {
        newData.newTextureID = newTextureID;
    }

    const newStreetName = data.streetName;
    if (newStreetName !== undefined) {
        newData.newStreetName = newStreetName;
    }

    const newStreetNumber = data.streetNumber;
    if (newStreetNumber !== undefined) {
        newData.newStreetNumber = newStreetNumber;
    }

    // @ts-ignore
    const url = serverData.serverUrl + serverData.port + "/api/v1/year/edit/" + id
    let response = await fetch(url, {
        method: "POST",
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(newData)
    });

    if (response.ok) {
        let json = await response.json();
        console.log(json);
        return json;
    } else {
        alert(response.status + " " + response.statusText);
    }

}

export async function deleteYear(id) {

    const serverData = await getServerData();

    // @ts-ignore
    const url = serverData.serverUrl + serverData.port + "/api/v1/year/delete/" + id;
    let response = await fetch(url + id, {
        method: "DELETE"
    });

    if (!response.ok) {
        alert(response.status);
    }

}