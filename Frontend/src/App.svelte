<script lang="ts">
  import TopBar from './lib/TopBar.svelte'
  import MainBody from './lib/MainBody.svelte'

  let mainBody;

  function addObject() {
    mainBody.handleAdd();
  }

</script>

<main>
  <TopBar addObjectFunction="{addObject}"/>
  <MainBody bind:this="{mainBody}"/>
</main>

<style>

</style>
