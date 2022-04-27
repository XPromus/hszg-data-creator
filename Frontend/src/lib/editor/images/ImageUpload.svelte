<script lang="ts">

    import ImageModal from './ImageUploadModal.svelte';
    let showModal = false;

    export function onShowModal() {
        showModal =true;
    }

</script>

<div class="container">
    <div class="row">
        <button on:click="{onShowModal}" type="button" class="btn btn-success">
            Bild hochladen
        </button>
    </div>
</div>

{#if showModal}
    <ImageModal on:close="{() => showModal = false}">
        <h2 slot="header">
            Bild Hochladen
        </h2>
        <ol class="modal-content">
            Lorem ipsum dolor sit amet consectetur adipisicing elit. Voluptates, minus magnam facere nihil odio eum eligendi libero quos in soluta harum inventore excepturi molestias quibusdam adipisci assumenda ullam, corporis at.
        </ol>
    </ImageModal>
{/if}

<style>

</style>