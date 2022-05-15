import { getServerData } from "./server";

export type Media = {
    id: number,
    url: string
}

export async function createMedia(objectId: number, mediaUrl: string): Promise<Response> {

    const serverData = await getServerData();
    const data = {
        "url": mediaUrl
    }
    //@ts-ignore
    const url = serverData.serverUrl + serverData.port + "/api/v1/media/create/" + objectId;

    let response: Response = await fetch(url, {
        method: "POST",
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(data)
    });

    //console.log("Created Media");
    return response;

}

export async function getMediaById(id: number): Promise<Response> {

    const serverData = await getServerData();
    //@ts-ignore
    const url = serverData.serverUrl + serverData.port + "/api/v1/media/get/id/" + id;

    let response: Response = await fetch(url, {
        method: "GET",
        headers: {'Content-Type': 'application/json'},
    });

    return response;

}

export async function getMediaByObjectId(objectId: number): Promise<Response> {

    const serverData = await getServerData();
    //@ts-ignore
    const url = serverData.serverUrl + serverData.port + "/api/v1/media/get/object/" + objectId;

    let response: Response = await fetch(url, {
        method: "GET",
        headers: {'Content-Type': 'application/json'},
    });

    return response;

}

export async function editMedia(id: number, newUrl: string): Promise<Response> {

    const serverData = await getServerData();
    const data = {
        "newUrl": newUrl
    }
    //@ts-ignore
    const url = serverData.serverUrl + serverData.port + "/api/v1/media/edit/" + id;

    let response: Response = await fetch(url, {
        method: "POST",
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(data)
    });

    //console.log("Edit Media");
    return response;

}

export async function deleteMedia(id: number): Promise<Response> {

    const serverData = await getServerData();
    //@ts-ignore
    const url = serverData.serverUrl + serverData.port + "/api/v1/media/delete/" + id;

    let response: Response = await fetch(url, {
        method: "DELETE"
    });

    return response;

}