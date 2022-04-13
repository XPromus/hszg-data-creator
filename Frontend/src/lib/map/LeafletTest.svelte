<script>

    import * as L from 'leaflet';
    import 'leaflet/dist/leaflet.css';
    import EditWindow from "./EditWindow.svelte";

    let map;

    const house = {
        name: "Neue Gasse 3, Dittelsdorf"
    };

    const coords = {
        lati: 50.953,
        long: 14.873
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
        
        let marker = L.marker([50.95308, 14.87294]).addTo(m);
        let polygon = L.polygon([
            [50.9531561, 14.8728262],
            [50.9531383, 14.873073],
            [50.9530285, 14.8730528],
            [50.9530505, 14.8728047],
            [50.9531536, 14.8728248]
        ]).addTo(m);

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

</script>

<svelte:window on:resize="{resizeMap}"></svelte:window>

<div>
    <div id="map" style="" use:mapAction />
</div>



<style>

    #map {
        position: absolute;
        top: 0;
        bottom: 0;
        width: 100%;
    }

</style>