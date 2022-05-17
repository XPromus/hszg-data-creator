<script lang="ts">
    import Map from './lib/map/Map.svelte';
    import LoginPanel from './lib/users/LoginPanel.svelte';
    import AppLoginWindow from './lib/users/AppLoginWindow.svelte';

    let loginState: boolean = false;

    function openApp() {
        loginState = true;
    }

</script>
  
<main>
    {#if loginState}
        <Map />
        <!--<LoginPanel />-->
    {:else}
        <AppLoginWindow openApp="{openApp}" />
    {/if}
</main>
  
<style>
    @import 'bulma/css/bulma.css';
</style>