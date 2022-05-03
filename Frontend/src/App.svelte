<script lang="ts">
  import { onMount } from 'svelte';

  import Map from './lib/map/Map.svelte';

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

  /*
  onMount(async () => {
    let response = await fetch("http://localhost:8080/test");
    if (response.ok) {
      response.text().then(function(text) {
        alert(text);
      })
    }
  });
  */

</script>

<main>
  <Map />
</main>

<style>

</style>
