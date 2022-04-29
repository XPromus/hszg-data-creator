<script lang="ts">

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

</script>

<main>
  <Map />
</main>

<style>

</style>
