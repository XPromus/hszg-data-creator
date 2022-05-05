<script lang="ts">

    import * as L from 'leaflet';
    import 'leaflet/dist/leaflet.css';
    import { onMount } from 'svelte';
    import Object from '../Object.svelte';

    import { dataMarkers, createDataMarker, getDataMarkerByObject, createDataMarkerFromSource, getDataMarkerByObjectID } from '../data/dataMarker';
    import { currentData, getCurrentDataByObjectID, getAllObjects, uploadObjectData, deleteObjectRequest } from '../data/objects';

    let map; 

    const coords = {
        lati: 50.95308,
        long: 14.87294
    }

    function createMap(container) {
        
        let m = L.map(container).setView([coords.lati, coords.long], 16);
        L.tileLayer(
            'https://{s}.basemaps.cartocdn.com/rastertiles/voyager/{z}/{x}/{y}{r}.png',
            {
                attribution: `&copy;<a href="https://www.openstreetmap.org/copyright" target="_blank">OpenStreetMap</a>,&copy;<a href="https://carto.com/attributions" target="_blank">CARTO</a>`,
                subdomains: 'abcd',
                maxZoom: 25,
            }
        ).addTo(m);

        return m;

    }

    function mapAction(container) {
        map = createMap(container);
        return {
            destroy: () => {
                map.remove();
            }
        }
    }

    function resizeMap() {
        if(map) {
            map.invalidateSize();
        }
    }

    function createMarkerFromSource(id, lat, lng) {

        let loc = L.latLng(lat, lng);
        let newMarker = L.marker(loc, {draggable: true});
        let dataMarker = createDataMarkerFromSource(id, newMarker)
        dataMarkers.push(dataMarker);

        newMarker.on('dragend', function(e) {
            let dataMarker = getDataMarkerByObject(newMarker);
            uploadObjectData(dataMarker, undefined);
        });

        newMarker.on('click', function(e) {
            let selected = getDataMarkerByObject(newMarker);
            openEditorWindow(selected);
        });

        newMarker.addTo(map);

    }   

    function createMarker(loc) {

        let newMarker = L.marker(loc, {draggable: true});
        let dataMarker = createDataMarker(newMarker)
        dataMarkers.push(dataMarker);
        getCurrentDataByObjectID(dataMarker);


        newMarker.on('dragend', function(e) {
            let dataMarker = getDataMarkerByObject(newMarker);
            uploadObjectData(dataMarker, undefined);
        });

        newMarker.on('click', function(e) {
            let selected = getDataMarkerByObject(newMarker);
            openEditorWindow(selected);
        });

        newMarker.addTo(map);
   
    }

    let preDataPromise;
    onMount(async () => {

        preDataPromise = await getAllObjects();
        for (let i = 0; i < preDataPromise.length; i++) {
            console.log("Create Marker");
            let markerData = preDataPromise[i];
            createMarkerFromSource(markerData.id, markerData.latitude, markerData.longitude);
        }


        map.on('click', function(e) {
            const pos = e.latlng;
            createMarker(pos);
        });
    })

    let editorWindowState: boolean = false;
    let dataPromise;
    function openEditorWindow(dataMarker) {
        dataPromise = getCurrentDataByObjectID(dataMarker);
        editorWindowState = true;
    }

    function closeEditorWindow() {
        editorWindowState = false;
    }

    function deleteObject() {
        const id = currentData.id;
        map.removeLayer(getDataMarkerByObjectID(id).marker);
        deleteObjectRequest(id);
        closeEditorWindow();
    }

</script>

<svelte:window on:resize="{resizeMap}"></svelte:window>

<div id="map" style="" use:mapAction />
{#await dataPromise}
    <p>Loading Data</p>
{:then data} 
    {#if editorWindowState}
        <Object closeFunction="{closeEditorWindow}" data="{data}" deleteObject="{deleteObject}"/>
    {/if}
{/await}

<style>

    #map {
        position: absolute;
        top: 0;
        bottom: 0;
        width: 100%;
    }

</style>