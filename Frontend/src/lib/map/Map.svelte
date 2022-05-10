<script lang="ts">

    import * as L from 'leaflet';
    import * as Object from '../api/objects';
    import 'leaflet/dist/leaflet.css';
    import { onMount } from 'svelte';
    
    import ObjectEditor from '../editor/ObjectEditor.svelte';
    import YearEditor from '../editor/YearEditor.svelte';

    let map;

    const startCoords = {
        lati: 50.95308,
        long: 14.87294
    }
    const customMarker = L.icon({
        iconUrl: 'marker-icon.png',
        iconSize: [14, 14],
        iconAnchor: [7, 7]
    });
    const customMarkerActive = L.icon({
        iconUrl: 'marker-icon-active.png',
        iconSize: [14, 14],
        iconAnchor: [7, 7]
    })

    function createMap(container) {
        let m = L.map(container).setView([startCoords.lati, startCoords.long], 16);
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

    function createMapMarkerFromSource(id, lat, lng) {

        const loc = L.latLng(lat, lng);
        let marker = L.marker(loc, {objectType: "marker", objectId: id, draggable: true, icon: customMarker});
        
        marker.on('dragend', function(e) {
            const newPos = marker.getLatLng();
            const data = { "newLatitude": newPos.lat, "newLongitude": newPos.lng };
            Object.editObject(marker.options.objectId, data);
        });

        marker.on('click', function(e) {
            disableAllMarkers();
            marker.setIcon(customMarkerActive);
            currentObjectId = marker.options.objectId;
            editorState = true;
        });

        marker.addTo(map);

    }

    async function createMapMarker(loc) {
        
        let id = await Object.createObject(loc);
        let marker = L.marker(loc, {objectType: "marker", objectId: id, draggable: true, icon: customMarker});
        
        marker.on('dragend', function(e) {
            const newPos = marker.getLatLng();
            const data = { "newLatitude": newPos.lat, "newLongitude": newPos.lng };
            Object.editObject(marker.options.objectId, data);
        });

        marker.on('click', function(e) {
            disableAllMarkers();
            marker.setIcon(customMarkerActive);
            currentObjectId = marker.options.objectId;
            editorState = true;
        });

        marker.addTo(map);

    }

    function disableAllMarkers() {
        map.eachLayer(function(layer) {
            if (layer.options.objectType == "marker") {
                layer.setIcon(customMarker);
            }
        });
    }

    onMount(async () => {

        let objects = await Object.getAllObjects();
        for (let i = 0; i < objects.length; i++) {
            let markerData = objects[i];
            createMapMarkerFromSource(markerData.id, markerData.latitude, markerData.longitude);
        }

        map.on('click', function(e) {
            createMapMarker(e.latlng);
        });

    });

    let objectEditor;
    function closeEditor() {

        if (yearEditorState) {
            alert("Schließe das Jahr Fenster zuerst");
            return;
        }

        editorState = false;
        disableAllMarkers();
    
    }

    function deleteObject() {
        map.eachLayer(function(layer) {
            if (layer.options.objectId == currentObjectId) {
                map.removeLayer(layer);
            }
        });
        editorState = false;
    }

    function openYearEditor() {
        yearEditorState = true;
    }

    function closeYearEditor() {
        if (editorState) {
            objectEditor.reloadYearList();
        }
        yearEditorState = false;
    }

    let editorState: boolean = false;
    let yearEditorState: boolean = false;
    let currentObjectId: number;

</script>

<svelte:window on:resize="{resizeMap}" />
<div id="map" use:mapAction />

<div class="hero">
    <div class="hero-body">
        <div class="columns">
            {#if editorState}
                <ObjectEditor bind:this="{objectEditor}" objectId={currentObjectId} deleteFunction="{deleteObject}" closeFunction="{closeEditor}" openYearEditorFunction="{openYearEditor}"/>
            {/if}
            {#if yearEditorState}
                <YearEditor closeFunction="{closeYearEditor}"/>
            {/if}
        </div>
    </div>
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

</style>