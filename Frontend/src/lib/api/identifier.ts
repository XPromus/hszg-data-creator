import { getServerData } from "./server";

export type Identifier = {
    id: number,
    identifierName: string,
    filename: string,
    url: string
}

export async function uploadJSON(name: string, jsonData): Promise<Identifier> {

    const serverData = await getServerData();
    const blob = new Blob([jsonData], {type: "text/plain"});
    const data: FormData = new FormData();
    data.append("file", blob, name + ".txt");

    const url = serverData.serverUrl + serverData.port + "/api/v1/identifier/upload/" + name;
    let response: Response = await fetch(url, {
        method: "POST",
        body: data
    });

    let json: Identifier = await response.json();
    return json;

}

export async function getJSON(id: number): Promise<string> {

    const serverData = await getServerData();
    const url = serverData.serverUrl + serverData.port + "/api/v1/identifier/get/" + id;
    let response: Response = await fetch(url, {
        method: "GET"
    });

    let data: string = await response.text();
    return data;

}

export async function getAllIdentifiers(): Promise<Identifier[]> {

    const serverData = await getServerData();
    const url = serverData.serverUrl + serverData.port + "/api/v1/identifier/all";
    let response: Response = await fetch(url, {
        method: "GET"
    });

    let data: Identifier[] = await response.json();
    return data;

}

export async function getIdentifierById(id: number): Promise<Identifier> {

    const serverData = await getServerData();
    const url = serverData.serverUrl + serverData.port + "/api/v1/identifier/id/" + id;
    let response: Response = await fetch(url, {
        method: "GET"
    });

    let data: Identifier = await response.json();
    return data;

}

export async function deleteIdentifier(id: number): Promise<void> {

    const serverData = await getServerData();
    const url = serverData.serverUrl + serverData.port + "/api/v1/identifier/delete/" + id;
    let response: Response = await fetch(url, {
        method: "DELETE"
    });

}