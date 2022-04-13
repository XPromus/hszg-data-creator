<script lang="ts">

  import ListView from './lib/ListView.svelte';
  import LeafletTest from './lib/map/LeafletTest.svelte';
  import MapUI from './lib/map/MapUI.svelte';
  import EditWindow from "./lib/map/EditWindow.svelte";

  const house = {
        name: "Neue Gasse 3, Dittelsdorf"
  };

</script>

<main>
  <EditWindow house="{house}"/>
  <LeafletTest />
  <!--<OpenLayersTest />-->
  <!--<MapUI />-->
  <!--<ListView />-->
</main>

<style>

</style>
