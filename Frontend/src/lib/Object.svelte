<script lang="ts">

    import { onMount } from "svelte";
    import EditWindow from "./editor/EditWindow.svelte";

    let editorWindow: EditWindow;
    
    export let data;
    
    const objectIDKey: string = "id";
    const objectNameKey: string = "name";
    const latitudeKey: string = "latitude";
    const longitudeKey: string = "longitude";
    const yearListKey: string = "years";
    const imageListKey: string = "images";

    let objectID: number;
    let objectName: string;
    let latitude: number;
    let longitude: number;
    let yearData;
    let imageData;

    function saveData() {

    }

    export function toJSON() {

        const ret = {
            objectIDKey: objectID,
            objectNameKey: objectName,
            latitudeKey: latitude,
            longitudeKey: longitude,
            yearListKey: editorWindow.getYearDataAsJSON(),
            imageListKey: []
        };    

        alert(ret);
        return ret;

    }

    export function fromJsonObject(obj): void { 

        objectID = obj.id;
        objectName = obj.name;
        latitude = obj.latitude;
        longitude = obj.longitude;

        yearData = obj.years;
        imageData = obj.images;

    }

    onMount(async () => {
        if (data != undefined) {
            fromJsonObject(data);
        }
    });

    export let closeFunction;

</script>

<EditWindow bind:this={editorWindow} closeFunction={closeFunction} data="{data}"/>

<style>

</style>