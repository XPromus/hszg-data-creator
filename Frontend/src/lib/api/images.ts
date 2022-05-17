import { getServerData } from "./server";

export type Image = {
    id: number
    filename: string,
    url: string
}

export async function uploadImage(objectId: number, file: File): Promise<number> {

    const serverData = await getServerData();
    const data: FormData = new FormData();
    data.append("file", file);

    const url = serverData.serverUrl + serverData.port + "/api/v1/image/upload/" + objectId;
    let response: Response = await fetch(url, {
        method: "POST",
        body: data
    });

    let json: Image = await response.json();
    return json.id;

}

export async function getImage(id: number) {

    const serverData = await getServerData();
    const url = serverData.serverUrl + serverData.port + "/api/v1/image/get/" + id;
    let response: Response = await fetch(url, {
        method: "GET"
    });

    let data = await response.blob();
    return data;

}

export async function getImagesFromObject(objectId: number): Promise<Image[]> {

    const serverData = await getServerData();
    const url = serverData.serverUrl + serverData.port + "/api/v1/image/get/object/" + objectId;
    let response: Response = await fetch(url, {
        method: "GET"
    });

    let data: Image[] = await response.json();
    return data;

}

export async function deleteAllImagesFromObject(objectId: number) {

    const serverData = await getServerData();
    const url = serverData.serverUrl + serverData.port + "/api/v1/image/delete/object/" + objectId;
    let response: Response = await fetch(url, {
        method: "DELETE"
    });

    console.log(response.status);

}

export async function deleteImage(id: number) {

    const serverData = await getServerData();
    const url: string = serverData.serverUrl + serverData.port + "/api/v1/image/delete/" + id;
    let response: Response = await fetch(url, {
        method: "DELETE"
    });

    console.log(response.status);

}