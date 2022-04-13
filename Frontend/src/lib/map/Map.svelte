<script lang="ts">
    
    import EditWindow from "./EditWindow.svelte";

    const house = {
        name: "Neue Gasse 3, Dittelsdorf"
    };

</script>

<div>
    <iframe title="map" width="100%" height="980px" frameborder="0" scrolling="no" marginheight="0" marginwidth="0" src="https://www.openstreetmap.org/export/embed.html?bbox=14.868487715721132%2C50.951495956085644%2C14.877446293830873%2C50.954655649083904&amp;layer=mapnik&amp;marker=50.95307582943704%2C14.872967004776001"></iframe>
    <div id="editWindow">
        <EditWindow house="{house}"/>
    </div>
</div>

<style>

    

</style>