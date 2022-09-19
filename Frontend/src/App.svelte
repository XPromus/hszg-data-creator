<script lang="ts">

    import AppMenu from './lib/AppMenu.svelte';
    import AppLoginWindow from './lib/users/AppLoginWindow.svelte';

    let loginState: boolean = false;

    function openApp() {
        loginState = true;
    }

</script>
  
<main>
    {#if loginState}
        <AppMenu />
    {:else}
        <AppLoginWindow openApp="{openApp}" />
    {/if}
</main>
  
<style>
    @import 'bulma/css/bulma.css';
</style>