import * as bcrypt from 'bcryptjs';

export const minPasswordLength: number = 8;
const saltRounds: number = 10;

export async function hashPassword(password: string): Promise<string> {
    const hash: string = bcrypt.hashSync(password, saltRounds);
    return hash;
}

export async function comparePassword(password: string, hash: string): Promise<boolean> {
    const check: boolean = bcrypt.compareSync(password, hash);
    return check;
}
