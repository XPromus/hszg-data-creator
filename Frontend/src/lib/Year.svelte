<script lang="ts">

    import { createEventDispatcher } from "svelte";

    export let yearId: number;
    export let accordionId: string;
    export let removeFunction;

    const headerId: string = "year_header_" + yearId;
    const collapseId: string = "year_collapse_" + yearId;
    const existCheckId: string = headerId + accordionId;

    let year: string;
    let objectName: string;
    let exist: boolean = true;
    let modelId: string;
    let textureId: string;
    let streetName: string;
    let streetNumber: number;
    let latitude: number;
    let longitude: number;

    let existButtonMargin: number = 10;

    export function toJSON(): string {

        const ret: string = '{'
            + '"year" : ' + year + ','
            + '"objectName" : ' + objectName + ','
            + '"exists" : ' + exist + ','
            + '"modelId" : ' + modelId + ','
            + '"textureId" : ' + textureId + ','
            + '"streetName" : ' + streetName + ','
            + '"sreetNumber" : ' + streetNumber + ','
            + '"latitude" : ' + latitude + ','
            + '"longitude" : ' + longitude
            + '}';

        return ret;

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
                        <input bind:checked="{exist}" value="" class="form-check-input" type="checkbox" id="{existCheckId}">
                        <label class="form-check-label" for="{existCheckId}">
                            Objekt existiert
                        </label>
                    </div>
                </div>

                <div class="col-12">
                    <div class="input-group mb-3">
                        <span class="input-group-text">Jahr</span>
                        <input bind:value={year} type="text" class="form-control" placeholder="yyyy" aria-label="Jahr">
                    </div>
                </div>

                {#if exist == true}
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
                            <input bind:value={streetNumber} type="text" class="form-control" placeholder="" aria-label="house-number">
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
                    <button on:click="{removeFunction}" id="deleteButton" type="button" class="btn btn-danger" data-toggle="tooltip" data-placement="bottom" title="Jahr löschen">
                        <i class="fa-solid fa-trash-can"></i>
                    </button>
                    <button on:click="{toJSON}" id="saveButton" type="button" class="btn btn-primary" data-toggle="tooltip" data-placement="bottom" title="Debug: Speichern">
                        <i class="fa-solid fa-floppy-disk"></i>
                    </button>
                </div>

            </form>
        </div>
    </div>
</div>

<style>

</style>