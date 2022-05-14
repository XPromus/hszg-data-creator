import { getServerData, getUrlBase } from "./server";
import { hashPassword, comparePassword } from "./passwords";

export const alreadyExistsCode: number = 226;

export type User = {
    id: number,
    email: string,
    password: string,
    privileges: object[]
};

export type EditUser = {
    newEmail: string,
    newPassword: string
};

export async function getAllUsers(): Promise<User[]> {

    const base: string = await getUrlBase();
    const url: string = base + "all";

    let response = await fetch(url, {
        method: "GET"
    });

    if (response.ok) {
        const userData: User[] = await response.json();
        return userData;
    }

}

export async function createUser(email: string, password: string): Promise<Response> {

    const hash: string = await hashPassword(password);
    const data: object = {
        "email": email,
        "password": hash
    };

    const base: string = await getUrlBase();
    const url: string = base  + "user/create";

    let response = await fetch(url, {
        method: "POST",
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(data)
    });

    return response;

}

export async function getUserById(id: number): Promise<User> {

    const base: string = await getUrlBase();
    const url: string = base + "user/get/" + id;

    let response = await fetch(url, {
        method: "GET"
    });

    if (response.ok) {
        const userData: User = await response.json();
        return userData;
    }

}

export async function getUserByEmail(email: string): Promise<Response> {

    const base: string = await getUrlBase();
    const url: string = base + "user/get/email";

    let response = await fetch(url, {
        method: "POST",
        headers: {'Content-Type': 'text/plain'},
        body: email
    });

    return response;

}

export async function checkUserPermission(id: number): Promise<boolean> {

    const base: string = await getUrlBase();
    const url: string = base + "user/permission/" + id;

    let response = await fetch(url, {
        method: "GET",
    });

    if (response.ok) {
        const data: string = await response.text();
        return data === 'true';
    }

}

export async function editUser(id: number, data: EditUser): Promise<User> {

    const base: string = await getUrlBase();
    const url: string = base + "user/edit/" + id;

    let response = await fetch(url, {
        method: "POST",
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(data)
    });

    if (response.ok) {
        const userData: User = await response.json();
        return userData;
    }

}

export async function addPrivilegeToUser(id: number, privilege: string): Promise<User> {

    const base: string = await getUrlBase();
    const url: string = base + "user/add/privilege/" + id;

    let response = await fetch(url, {
        method: "POST",
        headers: {'Content-Type': 'text/plain'},
        body: privilege
    });

    if (response.ok) {
        const userData: User = await response.json();
        return userData;
    }

}
