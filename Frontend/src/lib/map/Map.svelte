<script lang="ts">

    import * as L from 'leaflet';
    import * as Object from '../api/objects';
    import * as Icons from '../graphics/icons';
    import 'leaflet/dist/leaflet.css';
    import { onMount } from 'svelte';
    
    import ObjectEditor from '../editor/ObjectEditor.svelte';
    import YearEditor from '../editor/YearEditor.svelte';

    const startCoords = {
        lati: 50.95308,
        long: 14.87294
    };
    
    const customMarker = Icons.customMarkerNormal;
    const customMarkerActive = Icons.customMarkerActive;

    export let closeFunction;

    let map;
    
    let newObjectPosition;
    let createObjectModal;
    let objectEditor;
    let objectEditorState: boolean = false;
    let currentObjectId: number;
    
    let yearEditor: YearEditor;
    let yearEditorState: boolean = false;

    let markerDraggable: boolean = true;

    function createMap(container) {
        const m = L.map(container).setView([startCoords.lati, startCoords.long], 16);
        const osmLayer = getOSMLayer();
        m.addLayer(osmLayer);
        return m;
    }

    function changeMapLayer(oldLayer, newLayer) {
        map.removeLayer(oldLayer);
        map.addLayer(newLayer);
    }

    function getOSMLayer() {
        const osm = L.tileLayer('https://{s}.basemaps.cartocdn.com/rastertiles/voyager/{z}/{x}/{y}{r}.png', {
            attribution: `&copy;<a href="https://www.openstreetmap.org/copyright" target="_blank">OpenStreetMap</a>,&copy;<a href="https://carto.com/attributions" target="_blank">CARTO</a>`,
            subdomains: 'abcd',
            maxZoom: 25
        });
        return osm;
    }

    function getGoogleSatelliteLayer() {
        const googleSat = L.tileLayer('http://{s}.google.com/vt/lyrs=s&x={x}&y={y}&z={z}', {
            maxZoom: 25,
            subdomains: ['mt0', 'mt1', 'mt2', 'mt3']
        });
        return googleSat;
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

    async function createMapMarker(loc) {
        let id = await Object.createObject(loc);
        let marker = createMapMarkerFromSource(id, loc.lat, loc.lng);
        closeCreateObjectModal();
        markerClickAction(marker);
    }

    function createMapMarkerFromSource(id, lat, lng) {

        const loc = L.latLng(lat, lng);
        let marker = L.marker(loc, {objectType: "marker", objectId: id, draggable: true, icon: customMarker});
        
        marker.on('dragend', function(e) {
            const newPos = marker.getLatLng();
            const data = { "newLatitude": newPos.lat, "newLongitude": newPos.lng };
            Object.editObject(marker.options.objectId, data);
            if (objectEditorState) {
                objectEditor.changeSavedPosition(newPos.lat, newPos.lng);
            }
        });

        marker.on('click', function(e) {
            markerClickAction(marker);
        });

        marker.addTo(map);
        return marker;

    }

    function markerClickAction(marker) {

        disableAllMarkers();
        marker.setIcon(customMarkerActive);
        currentObjectId = marker.options.objectId;

        if (objectEditorState) { 
            objectEditor.reloadEditor(marker.options.objectId); 
            if (yearEditor !== undefined && yearEditor != null && yearEditorState == true) {
                yearEditor.callCloseEditor();
            }
        }

        openObjectEditor();

    }

    async function changeMarkerPosition() {
        map.eachLayer(async function(layer) {
            if (layer.options.objectId == currentObjectId) {
                let object = await Object.getObjectById(currentObjectId);
                let pos = L.latLng(object.latitude, object.longitude);
                layer.setLatLng(pos);
            }
        });
    }

    function makeMarkersDraggable(draggable: boolean): void {
        map.eachLayer(function(layer) {
            if (layer.options.objectType == "marker") {
                if (draggable) {
                    layer.dragging.enable();
                } else {
                    layer.dragging.disable();
                }
            }
        });
        markerDraggable = draggable;
    }

    function disableAllMarkers() {
        map.eachLayer(function(layer) {
            if (layer.options.objectType == "marker") {
                layer.setIcon(customMarker);
            }
        });
    }

    function deleteObject() {
        map.eachLayer(function(layer) {
            if (layer.options.objectId == currentObjectId) {
                map.removeLayer(layer);
            }
        });
        closeObjectEditor();
    }

    function openObjectEditor() {
        if (objectEditorState) {
            objectEditor.reloadEditor(currentObjectId);
            objectEditor.setDefaultEditorPage();
        } else {
            objectEditorState = true;
        }
    }

    function closeObjectEditor() {
        if (yearEditorState) { yearEditor.callCloseEditor(); }
        objectEditorState = false;
        disableAllMarkers();
    }

    async function openYearEditor() {

        if (yearEditorState) {
            closeYearEditor();
        }

        yearEditorState = true;

    }

    async function closeYearEditor() {
        await objectEditor.reloadYearList();
        yearEditorState = false;
    }
    
    function openCreateObjectModal() {
        createObjectModal.classList.add("is-active");
    }

    function closeCreateObjectModal() {
        createObjectModal.classList.remove("is-active");
    }

    onMount(async () => {

        let objects = await Object.getAllObjects();
        for (let i = 0; i < objects.length; i++) {
            let markerData = objects[i];
            createMapMarkerFromSource(markerData.id, markerData.latitude, markerData.longitude);
        }

        map.on('click', function(e) {
            newObjectPosition = e.latlng;
            openCreateObjectModal();
        });

        map.on('contextmenu', (e) => {
            map.openPopup("Objekt erstellen", e.latlng);
        });

    });

</script>

<svelte:window on:resize="{resizeMap}" />
<div id="map" use:mapAction />

<div bind:this="{createObjectModal}" class="modal is-clipped">
    <div class="modal-background"></div>
    <div class="modal-card">
        <header class="modal-card-head">
            <p class="modal-card-title">Neues Objekt Erstellen</p>
            <button on:click="{closeCreateObjectModal}" class="delete" aria-label="close"></button>
        </header>
        <footer class="modal-card-foot">
            <button on:click="{() => createMapMarker(newObjectPosition)}" class="button is-success">Objekt erstellen</button>
            <button on:click="{closeCreateObjectModal}" class="button">Abbrechen</button>
        </footer>
    </div>
</div>

<div class="hero">
    <div class="hero-body">
        <div class="columns">
            {#if objectEditorState}
                <ObjectEditor bind:this="{objectEditor}" changeMarkerPosition="{changeMarkerPosition}" objectId={currentObjectId} deleteFunction="{deleteObject}" closeFunction="{closeObjectEditor}" openYearEditorFunction="{openYearEditor}"/>
            {/if}
            {#if yearEditorState}
                <YearEditor bind:this="{yearEditor}" closeFunction="{closeYearEditor}"/>
            {/if}
        </div>
    </div>
</div>

<div id="map-layer-selector">
    <button on:click="{() => changeMapLayer(getGoogleSatelliteLayer(), getOSMLayer())}" class="button is-success">
        <i class="fa-solid fa-map" />
    </button>
    <button on:click="{() => changeMapLayer(getOSMLayer(), getGoogleSatelliteLayer())}" class="button is-success">
        <i class="fa-solid fa-satellite"></i>
    </button>
</div>

<div id="marker-drag-toggle">
    <button class="button is-success" on:click="{() => makeMarkersDraggable(!markerDraggable)}">
        {#if markerDraggable == true}
            <span>Marker Bewegung Deaktivieren</span>
        {:else}
            <span>Marker Bewegung Aktivieren</span>
        {/if}
    </button>
    <button on:click="{closeFunction}" class="delete is-large" />
</div>

<style>

    #map {
        position: absolute;
        top: 0;
        bottom: 0;
        width: 100%;
    }

    .columns {
        margin-top: 25px;
        margin-left: -49px;
    }

    .modal {
        z-index: 500;
    }

    #map-layer-selector {
        left: 0;
        bottom: 0;
        margin-bottom: 10px;
        margin-left: 10px;
        position: absolute;
        z-index: 500;
    }

    #marker-drag-toggle {
        top: 0;
        right: 0;
        margin-right: 10px;
        margin-top: 10px;
        position: absolute;
        z-index: 500;
    }

</style>