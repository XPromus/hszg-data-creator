<script lang="ts">
  import TopBar from './lib/TopBar.svelte'
  import MainBody from './lib/MainBody.svelte'
  import type Object from './lib/Object.svelte';

  let mainBody;

  function addObject() {
    mainBody.handleAdd();
  }

  function uploadData() {

    const objects: Object[] = mainBody.getObjects();
    let data: string = '[';

    for (let i = 0; i < objects.length; i++) {
      data += objects[i].toJSON();
      if (i < objects.length - 1) {
        data += ','
      }
    }  

    data += ']';
    alert(data);

    //TODO: Upload data

  }

  function initObjectsFromJSON(data: string) {

    const objects = JSON.parse(data);
    for (let i = 0; i < objects.length; i++) {
      const objectData = objects[i];
      addObject();
      let object: Object = mainBody.getObjects()[i];
      object.fromJSONObj(objectData);

    }

  }

  /*
  function testJSON() {
    const yearData: string = '{'
            + '"year" : 2000,'
            + '"objectName" : "Test Object",'
            + '"exists" : true,'
            + '"modelId" : 100,'
            + '"textureId" : 54,'
            + '"streetName" : "Straße West",'
            + '"streetNumber" : 10,'
            + '"latitude" : 1,'
            + '"longitude" : 1'
            + '}';

    const objectData: string = '['
      + '{'
      + '"objectName" : "Test Object",'
      + '"years" : [' + yearData + ']'
      + '}'
      + ']';

      initObjectsFromJSON(objectData);

  }
  */

</script>

<main>
  <TopBar uploadDataFunction="{uploadData}" addObjectFunction="{addObject}"/>
  <MainBody bind:this="{mainBody}"/>
</main>

<style>

</style>
