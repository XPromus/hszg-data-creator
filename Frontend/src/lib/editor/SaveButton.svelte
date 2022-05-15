<script lang="ts">
    
    type saveStateType = {
        none: number,
        success: number,
        failure: number
    }

    export const saveStateEnum: saveStateType = {
        none: 0,
        success: 1,
        failure: 2
    };

    export let saveFuntion;
    let saveState: number = saveStateEnum.none;

    export function setSaveState(state: number) {
        saveState = state;
    }

</script>

<button on:click="{saveFuntion}" class="button is-success is-fullwidth">
    {#if saveState == saveStateEnum.none}
        <span>Speichern</span>
    {:else if saveState == saveStateEnum.success}
        <i class="fa-solid fa-check"></i>
    {:else if saveState == saveStateEnum.failure}
        <span class="icon-text">
            <span class="icon">
                <i class="fa-solid fa-xmark"></i>
            </span>
            <span>Daten konnten nicht gespeichert werden</span>
        </span>
    {/if}
</button>

<style>

</style>