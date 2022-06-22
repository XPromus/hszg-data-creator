import { writable } from "svelte/store";

export type Option = {
    name: string,
    goal: number
};

export type Node = {
    id: number,
    title: string,
    description: string,
    type: string, //start, end, normal
    oneGoal: boolean,
    goal: number,
    options: Option[]
};

export const nodes = writable([]);
export const nodeResults = writable([]);