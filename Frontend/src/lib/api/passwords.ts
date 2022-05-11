import * as bcrypt from 'bcrypt';
import { hash } from 'ol/tilecoord';

const saltRounds: number = 10;

export async function hashPassword(password: string): Promise<string> {
    const hash: string = bcrypt.hashSync(password, saltRounds);
    return hash;
}

export async function comparePassword(password: string, hash: string): Promise<boolean> {
    const check: boolean = bcrypt.compareSync(password, hash);
    return check;
}

