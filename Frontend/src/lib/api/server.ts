type ServerConf = {
    serverUrl: string,
    apiUrl: string,
    port: number
};

export async function getServerData(): Promise<ServerConf> {
    const response = await fetch('server.conf.json');
    const data: ServerConf = await response.json();
    return data;
}

export async function getUrlBase(): Promise<string> {
    const serverData: ServerConf = await getServerData();
    return serverData.serverUrl + serverData.port + serverData.apiUrl;
}