<script lang="ts">
import Object from "./Object.svelte";

    let year: string;
    let objectName: string;
    let modelId: string;
    let textureId: string;
    let streetName: string;
    let houseNumber: number;
    let latitude: number;
    let longitude: number;

    export let headerId: string;
    export let collapseId: string;
    export let accordionId: string;
    let existCheckId: string = headerId + accordionId;

    let showContent: boolean = true;
    let existButtonMargin: number = 10;

    function validate(): void {
        //@ts-ignore
        if (document.getElementById(existCheckId).checked) {
            showContent = true;
            existButtonMargin = 10;
        } else {
            showContent = false;
            existButtonMargin = 0;
        }
    }

    function toJSON() {

    }

</script>

<div class="accordion-item">
    <h2 class="accordion-header" id="{headerId}">
        <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#{collapseId}" aria-expanded="false" aria-controls="#{collapseId}">
            {objectName} ({year})
        </button>
    </h2>
    <div id="{collapseId}" class="accordion-collapse collapse" aria-labelledby="{headerId}" data-bs-parent="#{accordionId}">
        <div class="accordion-body">
            <form class="row">

                <div class="col-12" style="margin-bottom: {existButtonMargin}px;">
                    <div class="form-check">
                        <input on:click={validate} class="form-check-input" type="checkbox" value="" id="{existCheckId}" checked>
                        <label class="form-check-label" for="{existCheckId}">
                            Objekt existiert
                        </label>
                    </div>
                </div>

                {#if showContent == true}
                    <div class="col-12">
                        <div class="input-group mb-3">
                            <span class="input-group-text">Jahr</span>
                            <input bind:value={year} type="text" class="form-control" placeholder="yyyy" aria-label="Jahr">
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group mb-3">
                            <span class="input-group-text">Name des Objekts</span>
                            <input bind:value={objectName} type="text" class="form-control" placeholder="" aria-label="Name">
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="input-group mb-3">
                            <span class="input-group-text">Modell ID</span>
                            <input bind:value={modelId} type="text" class="form-control" placeholder="" aria-label="model-id">
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="input-group mb-3">
                            <span class="input-group-text">Texturen ID</span>
                            <input bind:value={textureId} type="text" class="form-control" placeholder="" aria-label="texture-id">
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="input-group mb-3">
                            <span class="input-group-text">Straße</span>
                            <input bind:value={streetName} type="text" class="form-control" placeholder="" aria-label="street-name">
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="input-group mb-3">
                            <span class="input-group-text">Hausnummer</span>
                            <input bind:value={houseNumber} type="text" class="form-control" placeholder="" aria-label="house-number">
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="input-group mb-3">
                            <span class="input-group-text">Latitude</span>
                            <input bind:value={latitude} type="text" class="form-control" placeholder="" aria-label="latitude">
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="input-group mb-3">
                            <span class="input-group-text">Longitude</span>
                            <input bind:value={longitude} type="text" class="form-control" placeholder="" aria-label="longitude">
                        </div>
                    </div>
                {/if}

                <div class="col-12">
                    <button id="deleteButton" type="button" class="btn btn-danger">
                        <i class="fa-solid fa-trash-can"></i>
                    </button>
                </div>

            </form>
        </div>
    </div>
</div>

<style>

</style>