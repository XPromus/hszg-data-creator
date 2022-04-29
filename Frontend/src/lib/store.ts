import { writable } from "svelte/store";

export let objects = [];

class Object {
    id: number;
    name: string;
    latitude: number;
    longitude: number;
    years: Year[];
    images: Image[];
}

class Year {
    id: number;
    year: string;
    objectName: string;
    exists: boolean;
    modelId: number;
    textureId: number;
    streetName: string;
    streetNumber: number;
}

class Image {
    id: number;
}

export function createNewObject() {
    objects.push(new Object());
}

export function createNewYear(object: Object) {
    object.years.push(new Year());
}

export function createYearFromData(object: Object, data) {

    const newYear = new Year();
    newYear.id = data.id;
    newYear.year = data.year;
    newYear.objectName = data.objectName;
    newYear.exists = data.exists;
    newYear.modelId = data.modelId;
    newYear.textureId = data.textureId;
    newYear.streetName = data.streetName;
    newYear.streetNumber = data.streetNumber;

    object.years.push(newYear);

}

export function createObjectFromData(data) {

    createNewObject();
    const newObject = objects[objects.length - 1];
    newObject.id = data.id;
    newObject.name = data.name;
    newObject.latitude = data.latitude;
    newObject.longitude = data.longitude;

    const yearArray = data.years;
    for (let i = 0; i < yearArray.length; i++) {
        const yearData = yearArray[i];
        createYearFromData(newObject, yearData);
    }

    const imageArray = data.images;
    for (let i = 0; i < imageArray.length; i++) {

    }

}