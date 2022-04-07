<script lang="ts">

    export let addObjectFunction;
    export let uploadDataFunction;

</script>

<nav class="navbar navbar-light bg-light">
    <div class="container-fluid">
        <a id="titleLink" href="https://www.youtube.com/watch?v=uRSAatLI2QY" class="navbar-brand">Data Creator</a>
        <form class="d-flex">
            <input class="form-control me-2" type="text" placeholder="Neues Objekt" aria-label="Create">
            <button on:click="{addObjectFunction}" type="button" class="btn btn-success me-2" data-toggle="tooltip" data-placement="bottom" title="Neues Objekt erstellen">
                <i class="fa-solid fa-plus"></i>
            </button>
            <button on:click="{uploadDataFunction}" type="button" class="btn btn-primary" data-toggle="tooltip" data-placement="bottom" title="Daten Speichern">
                <i class="fa-solid fa-cloud-arrow-up"></i>
            </button>
        </form>
    </div>
</nav>

<style>

    #titleLink {
        cursor: default;
    }

</style>