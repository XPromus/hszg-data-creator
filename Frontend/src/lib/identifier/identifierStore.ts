import { writable } from "svelte/store";

export type Option = {
    name: string,
    goal: number
};

export type Node = {
    id: number,
    title: string,
    type: string,
    oneGoal: boolean,
    goal: number,
    options: Option[]
};

export const nodes = writable([]);