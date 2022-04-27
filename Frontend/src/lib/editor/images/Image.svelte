<script lang="ts">
    export let source: string;
</script>

<div class="card" style="width: 100%;">
    <img class="card-img-top" src="{source}" alt="">
    <div class="card-body">
        <div class="input-group mb-3">
            <span class="input-group-text" id="basic-addon1">Titel</span>
            <input type="text" class="form-control" placeholder="Bild Titel" aria-label="Bild Titel" aria-describedby="basic-addon1">
        </div>
        <div class="input-group">
            <span class="input-group-text">Beschreibung</span>
            <textarea class="form-control" aria-label="Bild Beschreibung"></textarea>
        </div>
        <button type="button" class="btn btn-danger">
            <i class="fa-solid fa-trash-can"></i>
        </button>
    </div>
</div>

<style>

    div {
        margin-bottom: 10px;
    }

</style>