<script lang="ts">
    
    import Map from './lib/map/Map.svelte';
    import AppLoginWindow from './lib/users/AppLoginWindow.svelte';
    //import CommitOptions from './lib/editor/CommitOptions.svelte';

    let loginState: boolean = false;

    function openApp() {
        loginState = true;
    }

</script>
  
<main>
    {#if loginState}
        <Map />
        <!--<CommitOptions />-->
    {:else}
        <AppLoginWindow openApp="{openApp}" />
    {/if}
</main>
  
<style>
    @import 'bulma/css/bulma.css';
</style>