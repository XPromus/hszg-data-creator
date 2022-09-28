<script lang="ts">

    import * as L from 'leaflet';
    import * as Object from '../api/objects';
    import * as Icons from '../graphics/icons';
    import 'leaflet/dist/leaflet.css';
    import { onMount } from 'svelte';
    
    import ObjectEditor from '../editor/ObjectEditor.svelte';
    import YearEditor from '../editor/YearEditor.svelte';
    import IdentifierUser from '../identifier/use/IdentifierUser.svelte';
    import IdentifierUserYear from '../identifier/use/IdentifierUserYear.svelte';
    import { currentYearId } from '../data/selectedData';

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

    let identifierUser;
    let identifierUserState: boolean = false;

    let identifierUserYear;
    let identifierUserYearState: boolean = false;

    let markerDraggable: boolean = true;

    let toolbarState: boolean = false;

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
            if (identifierUser !== undefined && identifierUser != null && identifierUserState == true) {
                closeIdentifierUserWindow();
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
        if (identifierUserYearState) { closeYearIdentifier(); }
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
        if (identifierUserYearState) { closeYearIdentifier(); }
        yearEditorState = false;
    }

    function openIdentifierUserWindow() {
        identifierUserState = true;
    }

    function closeIdentifierUserWindow() {
        identifierUserState = false;
    }

    function openYearIdentifier() {
        identifierUserYearState = true;
    }

    function closeYearIdentifier() {
        identifierUserYearState = false;
    }
    
    function openCreateObjectModal() {
        createObjectModal.classList.add("is-active");
    }

    function closeCreateObjectModal() {
        createObjectModal.classList.remove("is-active");
    }

    function changeToolbarState(state: boolean): void {
        toolbarState = state;
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
                <ObjectEditor bind:this="{objectEditor}" changeMarkerPosition="{changeMarkerPosition}" objectId={currentObjectId} deleteFunction="{deleteObject}" closeFunction="{closeObjectEditor}" openYearEditorFunction="{openYearEditor}" openIdentifierEditor="{openIdentifierUserWindow}"/>
            {/if}
            {#if yearEditorState}
                <YearEditor bind:this="{yearEditor}" closeFunction="{closeYearEditor}" openIdentifierEditor="{openYearIdentifier}"/>
            {/if}
            {#if identifierUserState}
                <IdentifierUser bind:this="{identifierUser}" objectId="{currentObjectId}" closeFunction="{closeIdentifierUserWindow}"/>
            {/if}
            {#if identifierUserYearState}
                <IdentifierUserYear bind:this="{identifierUserYear}" yearId="{currentYearId}" closeFunction="{closeYearIdentifier}"/>
            {/if}
        </div>
    </div>
</div>

{#if toolbarState}
    <div id="toolbar" class="box">
        <button on:click="{() => changeToolbarState(false)}" class="button is-dark">
            <i class="fa-solid fa-chevron-down"></i>
        </button>
        <button on:click="{() => changeMapLayer(getGoogleSatelliteLayer(), getOSMLayer())}" class="button is-success" style="margin-left: 25px;">
            <i class="fa-solid fa-map" />
        </button>
        <button on:click="{() => changeMapLayer(getOSMLayer(), getGoogleSatelliteLayer())}" class="button is-success" style="margin-left: 5px;">
            <i class="fa-solid fa-satellite"></i>   
        </button>
        <button on:click="{() => makeMarkersDraggable(!markerDraggable)}" class="button is-success" style="margin-left: 25px;">
            {#if markerDraggable == true}
                <span>Marker Bewegung Deaktivieren</span>
            {:else}
                <span>Marker Bewegung Aktivieren</span>
            {/if}
        </button>
    </div>
{:else}
    <div id="openToolbarButton" class="box">
        <button on:click="{() => changeToolbarState(true)}" class="button is-dark">
            <i class="fa-solid fa-chevron-up"></i>
        </button>
    </div>
{/if}

<button id="closeButton" on:click="{closeFunction}" class="delete is-large" />

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

    #closeButton {
        top: 0;
        right: 0;
        position: fixed;
        z-index: 500;
        margin-top: 10px;
        margin-right: 10px;
    }

    #openToolbarButton {
        bottom: 0;
        left: 0;
        position: fixed;
        z-index: 500;
        margin-bottom: 5px;
        margin-left: 5px;
    }

    #toolbar {
        bottom: 0;
        left: 0;
        position: fixed;
        z-index: 500;
        width: 100vw;
        height: 5rem;
        margin-bottom: 5px;
        margin-left: 5px;
        margin-right: 5px;
    }

</style>