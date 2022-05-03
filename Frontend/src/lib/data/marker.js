export let dataMarkers = [];

export function createDataMarkerFromSource(id, marker) {
    
    let newMarker = {};

    newMarker.objectID = id;
    newMarker.marker = marker;

    return newMarker;
    
}

export function createDataMarker(marker) {

    let newMarker = {};

    newMarker.objectID = undefined;
    newMarker.marker = marker;

    return newMarker;

}

export function deleteDataMarkerByObjectID(id) {

    let index;
    for (let i = 0; i < dataMarkers.length; i++) {
        if (dataMarkers[i].objectID == id) {
            index = i;
            break;
        }
    }

    if (index != undefined) {
        dataMarkers.splice(index, 1);
    }

}

export function getDataMarkerByObjectID(id) {
    for (let i = 0; i < dataMarkers.length; i++) {
        if (dataMarkers[i].objectID == id) {
            return dataMarkers[i];
        }
    }
}

export function getDataMarkerByObject(marker) {

    for(let i = 0; i < dataMarkers.length; i++) {
        if (dataMarkers[i].marker === marker) {
            return dataMarkers[i];
        }
    }

}