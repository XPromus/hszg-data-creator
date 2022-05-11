import { getServerData, getUrlBase } from "./server";

export type Privilege = {
    id: number,
    name: string    
};

export async function getAllPrivileges(): Promise<Privilege[]> {

    const base: string = await getUrlBase();
    const url: string = base + "all";

    let response = await fetch(url, {
        method: "GET"
    });

    if (response.ok) {
        const privilegeData: Privilege[] = await response.json();
        return privilegeData;
    }

}

export async function getPrivilegeByName(name: string): Promise<Privilege> {

    const base: string = await getUrlBase();
    const url: string = base + "get/name";

    let response = await fetch(url, {
        method: "GET",
        headers: {'Content-Type': 'text/plain'},
        body: name
    });

    if (response.ok) {
        const privilegeData: Privilege = await response.json();
        return privilegeData;
    }

}

export async function getPrivilegeById(id: number): Promise<Privilege> {

    const base: string = await getUrlBase();
    const url: string = base + "get/" + id;

    let response = await fetch(url, {
        method: "GET"
    });

    if (response.ok) {
        const privilegeData: Privilege = await response.json();
        return privilegeData;
    }

}