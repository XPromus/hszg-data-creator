<script lang="ts">

    import * as L from 'leaflet';
    import 'leaflet/dist/leaflet.css';
    import { onMount } from 'svelte';
    import Object from '../Object.svelte';

    let map; 
    let markers = [];

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

    function createMarker(loc) {
        let marker = L.marker(loc, {draggable:true});
        markers.push(marker);

        const id = markers[markers.length - 1];

        marker.on('dragend', function(e) {
            let pos = marker.getLatLng();
        });

        marker.on('click', function(e) {
            for(let i = 0; i < markers.length; i++) {
                if (markers[i] === marker) {
                    openEditorWindow();
                }
            }
        });

        marker.addTo(map);
    }

    onMount(async () => {
        map.on('click', function(e) {
            const pos = e.latlng;
            createMarker(pos);
        });
    })

    function changeEditorWindow(index) {

    }

    let editorWindowState: boolean = false;
    function openEditorWindow() {
        editorWindowState = true;
    }

    function closeEditorWindow() {
        editorWindowState = false;
    }

</script>

<svelte:window on:resize="{resizeMap}"></svelte:window>

<div id="map" style="" use:mapAction />
{#if editorWindowState}
    <Object closeFunction="{closeEditorWindow}" data="" />
{/if}

<style>

    #map {
        position: absolute;
        top: 0;
        bottom: 0;
        width: 100%;
    }

</style>