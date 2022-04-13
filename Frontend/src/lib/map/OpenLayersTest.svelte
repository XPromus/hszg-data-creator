<script lang="ts">

    import { onMount } from "svelte";
    import View from "ol/View";
    import Map from "ol/Map";
    import Layer from "ol/layer/Layer";
    import OSM from "ol/source/OSM";
    import proj from "ol/proj";

    let map;

    const coords = {
        lati: 50.953,
        long: 14.873
    }

    onMount(() => {
        map = new Map({
            target: 'map',
            layers: [
                new Layer ({
                    source: new OSM()
                })
            ],
            view: new View({
                center: proj.fromLonLat([coords.lati, coords.long]),
                zoom: 4
            })
        });
    })

</script>

<div id="map" class="map">

</div>

<style>

    .map {
        height: 400px;
        width: 100%;
    }

</style>