export async function getServerData(): Promise<object> {
    const response = await fetch('server.conf.json');
    const data: object = await response.json();
    return data;
}