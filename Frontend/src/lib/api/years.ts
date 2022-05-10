import { getServerData } from "./server";

export async function createYear(objectId: number, year: number) {

    const serverData = await getServerData();
    //@ts-ignore
    const url = serverData.serverUrl + serverData.port + "/api/v1/year/create/" + objectId + "/" + year;

    let response = await fetch(url, {
        method: "POST",
        headers: {'Content-Type': 'application/json'}
    });

    if (response.ok) {
        console.log("Created Year");
        const yearData = await response.json();
        return yearData;
    }

}

export async function getAllYearsFromObject(objectId: number) {
    
    const serverData = await getServerData();
    //@ts-ignore
    const url = serverData.serverUrl + serverData.port + "/api/v1/year/get/object/" + objectId;

    let response = await fetch(url, {
        method: "GET",
        headers: {'Content-Type': 'application/json'}
    });

    if (response.ok) {
        console.log("Got Years");
        const yearData = await response.json();
        return yearData;
    }

}

export async function getYearById(yearId: number) {
    
    const serverData = await getServerData();
    //@ts-ignore
    const url = serverData.serverUrl + serverData.port + "/api/v1/year/get/" + yearId;

    let response = await fetch(url, {
        method: "GET",
        headers: {'Content-Type': 'application/json'}
    });

    if (response.ok) {
        console.log("Got Year");
        const yearData = await response.json();
        return yearData;
    }

}

export async function editYear(yearId: number, changes) {
    
    const serverData = await getServerData();
    //@ts-ignore
    const url = serverData.serverUrl + serverData.port + "/api/v1/year/edit/" + yearId;

    let response = await fetch(url, {
        method: "POST",
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(changes)
    });

    if (response.ok) {
        console.log("Edited Year");
        const yearData = await response.json();
        return yearData;
    }

}

export async function deleteYear(yearId: number) {

    const serverData = await getServerData();
    //@ts-ignore
    const url = serverData.serverUrl + serverData.port + "/api/v1/year/delete/" + yearId;

    let response = await fetch(url, {
        method: "DELETE"
    });

    if (response.ok) {
        console.log("Deleted Year");
    }

}