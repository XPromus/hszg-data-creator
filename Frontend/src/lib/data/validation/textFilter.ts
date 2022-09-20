export function clsAlphaNoOnly(text: string): boolean {
    const regex: RegExp = new RegExp("^[a-zA-Z0-9 ]+$");
    return regex.test(text);
}

export function clsNoOnly(text: string): boolean {
    console.log(text);
    const regex: RegExp = new RegExp(/^\d+$/);
    return regex.test(text);
}