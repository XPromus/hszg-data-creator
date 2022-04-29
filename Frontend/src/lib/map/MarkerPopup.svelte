<script lang="ts">

    import { createEventDispatcher } from 'svelte';
    const dispatch = createEventDispatcher();

    let name: string = "Neues Objekt";

</script>

<div class="container">
    <div class="row">
        <div class="col justify-content-center">
            <p>{name}</p>
        </div>
    </div>
    <div class="row">
        <div class="col-6">
            <button class="btn btn-success">
                Erstellen
            </button>
        </div>
        <div class="col-6">
            <button class="btn btn-danger">
                Abbrechen
            </button>
        </div>
    </div>    
</div>

<style>

</style>