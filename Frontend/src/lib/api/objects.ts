import { getServerData } from "./server";
import { deleteAllImagesFromObject } from "./images";

export async function createObject(loc): Promise<number> {

    const serverData = await getServerData();
    //@ts-ignore
    const url = serverData.serverUrl + serverData.port + "/api/v1/object/create";
    const data = {
        name: "Neues Objekt",
        latitude: loc.lat,
        longitude: loc.lng
    };

    let response = await fetch(url, {
        method: "POST",
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(data)
    });

    if (response.ok) {
        const objectData = await response.json();
        return objectData.id;
    }

}

export async function editObject(id: number, data): Promise<number> {

    const serverData = await getServerData();
    //@ts-ignore
    const url = serverData.serverUrl + serverData.port + "/api/v1/object/edit/" + id;

    let response = await fetch(url, {
        method: "POST",
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(data)
    });

    if (response.ok) {
        //console.log("Edit Success");
    }

    return response.status;

}

export async function getAllObjects() {
    
    const serverData = await getServerData();
    //@ts-ignore
    const url = serverData.serverUrl + serverData.port + "/api/v1/object/all";

    let response = await fetch(url, {
        method: "GET",
        headers: {'Content-Type': 'application/json'}
    });

    if (response.ok) {
        const objects = await response.json();
        return objects;
    }

}

export async function getObjectById(id: number) {

    const serverData = await getServerData();
    //@ts-ignore
    const url = serverData.serverUrl + serverData.port + "/api/v1/object/id/" + id;

    let response = await fetch(url, {
        method: "GET",
        headers: {'Content-Type': 'application/json'}
    });

    if (response.ok) {
        console.log("Gotten Object");
        const object = await response.json();
        return object;
    }

}

export async function deleteObject(id: number) {

    await deleteAllImagesFromObject(id);

    const serverData = await getServerData();
    //@ts-ignore
    const url = serverData.serverUrl + serverData.port + "/api/v1/object/delete/" + id;

    let response = await fetch(url, {
        method: "DELETE"
    });

    if (response.ok) {
        //console.log("Deleted Object");
    }

}