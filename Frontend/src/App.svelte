<script lang="ts">
  import { onMount } from 'svelte';

  import Map from './lib/map/Map.svelte';
  import { getServerData } from './lib/data/serverConfig';

  onMount(async () => {
    console.log("JSON Data: ");
    console.log(await getServerData());
  });

</script>

<main>
  <Map />
</main>

<style>

</style>
