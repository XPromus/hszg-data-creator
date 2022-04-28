<script lang="ts">

  import Map from './lib/map/Map.svelte';
  import Object from './lib/Object.svelte';

  let showEditor: boolean = true;

  const testData: object = {
    "id":0,
    "name":"Test Objekt",
    "latitude": 1,
    "longitude": 1,
    "years":[
      {
        "id":0,
        "year":"2000",
        "objectName":"Nebenhaus",
        "exists":true,
        "modelId":"12345",
        "textureId":"12345",
        "streetName":"Straße",
        "streetNumber":"1"
      }
    ],
    "images":[

    ]
  };

  const house = {
        name: "Neue Gasse 3, Dittelsdorf"
  };

  function openEditor() {

  }

</script>

<main>
  {#if showEditor == true}
    <Object data="{testData}"/>
  {/if}
  <Map />
</main>

<style>

</style>
