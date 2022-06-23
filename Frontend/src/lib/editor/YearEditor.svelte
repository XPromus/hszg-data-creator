<script lang="ts">

    import { currentYearId } from '../data/selectedData';
    import { fade } from 'svelte/transition';
    import { onMount } from 'svelte';
    import * as Year from '../api/years';

    const yearId: number = currentYearId;
    export let closeFunction = () => {};

    let deleteYearModal;

    let year: number;
    let exists: boolean;
    let name: string;
    let street: string;
    let streetNumber: string;
    let modelId: string;
    let textureId: string;
    
    export async function reloadYearEditor() {
        let yearData = await Year.getYearById(yearId);
        applyData(yearData);
    }

    export function callCloseEditor() {
        closeEditor();
    }

    async function saveData() {
        
        let data = {
            "newYear": year,
            "newObjectName": name,
            "newExists": exists,
            "newModelID": modelId,
            "newTextureID": textureId,
            "newStreetName": street,
            "newStreetNumber": streetNumber
        };

        await Year.editYear(yearId, data);

    }

    async function closeEditor() {
        //await saveData();
        closeFunction();
    }

    function openDeleteYearModal() {
        deleteYearModal.classList.add("is-active");
    }

    function closeDeleteYearModal() {
        deleteYearModal.classList.remove("is-active");
    }

    async function deleteYear() {
        closeDeleteYearModal();
        await Year.deleteYear(yearId);
        closeFunction();
    }

    function applyData(data) {
        year = data.year;
        exists = data.exists
        name = data.objectName;
        street = data.streetName;
        streetNumber = data.streetNumber;
        modelId = data.modelID;
        textureId = data.textureID;
    }

    onMount(async () => {
        let yearData = await Year.getYearById(yearId);
        applyData(yearData);
        console.log(yearData);
    });

</script>

<div bind:this="{deleteYearModal}" class="modal is-clipped">
    <div class="modal-background"></div>
    <div class="modal-card">
        <header class="modal-card-head">
            <p class="modal-card-title">Das Jahr wirklich löschen</p>
            <button on:click="{closeDeleteYearModal}" class="delete" aria-label="close"></button>
        </header>
        <footer class="modal-card-foot">
            <button on:click="{deleteYear}" class="button is-danger">Löschen</button>
            <button on:click="{closeDeleteYearModal}" class="button">Abbrechen</button>
        </footer>
    </div>
</div>

<div id="yearEditWindow" transition:fade="{{duration: 250}}" class="column is-one-third">
    <article class="panel is-primary">
        <nav class="panel-heading level">
            <div class="level-left">
                <div class="level-item">
                    Jahr Editieren
                </div>
            </div>
            <div class="level-right">
                <div class="columns">
                    <div class="column is-full">
                        <button on:click="{closeEditor}" class="button is-primary">
                            <i class="fa-solid fa-xmark"></i>
                        </button>
                    </div>
                </div>
            </div>
        </nav>
        <label href="#" class="panel-block">
            <input bind:checked="{exists}" type="checkbox">
            Objekt existiert
        </label>
        <div class="panel-block">
            <nav class="level">
                <div class="level-left">
                    <div class="level-item">
                        <p>Jahr</p>
                    </div>
                </div>
                <div class="level-right" style="width: 80%">
                    <input bind:value="{year}" class="input" type="text" placeholder="Jahr Format: yyyy">
                </div>
            </nav>
        </div>
        <div class="panel-block">
            <nav class="level">
                <div class="level-left">
                    <div class="level-item">
                        <p>Name</p> 
                    </div>
                </div>
                <div class="level-right" style="width: 80%">
                    <input bind:value="{name}" class="input" type="text">
                </div>
            </nav>
        </div>
        <div class="panel-block">
            <nav class="level">
                <div class="level-left">
                    <div class="level-item">
                        <p>Straße</p> 
                    </div>
                </div>
                <div class="level-right" style="width: 80%">
                    <input bind:value="{street}" class="input" type="text">
                </div>
            </nav>  
        </div>
        <div class="panel-block">
            <nav class="level">
                <div class="level-left">
                    <div class="level-item">
                        <p>Hausnummer</p> 
                    </div>
                </div>
                <div class="level-right" style="width: 80%">
                    <input bind:value="{streetNumber}" class="input" type="text">
                </div>
            </nav>
        </div>
        <div class="panel-block">
            <nav class="level">
                <div class="level-left">
                    <div class="level-item">
                        <p>Modell Upload</p>
                    </div>
                </div>
                <div class="level-right">
                    <div class="level-item">
                        <div class="file has-name" style="margin-right: 15px;">
                            <label class="file-label">
                                <input class="file-input" type="file" multiple>
                                <span class="file-icon">
                                    <i class="fas fa-upload"></i>
                                </span>
                                <span class="file-label">
                                    Modell wählen
                                </span>
                            </label>
                        </div>
                        <button class="button is-warning" title="Dateien aus der Auswahl entfernen" style="margin-right: 5px;">
                            <i class="fa-solid fa-xmark"></i>
                        </button>
                        <button class="button is-success" title="Dateien hochladen">
                            <i class="fa-solid fa-upload"></i>
                        </button>
                    </div>
                </div>
            </nav>
        </div>
        <!--
        <div class="panel-block">
            <nav class="level">
                <div class="level-left">
                    <div class="level-item">
                        <p>Modell-ID</p> 
                    </div>
                </div>
                <div class="level-right" style="width: 80%">
                    <input bind:value="{modelId}" class="input" type="text">
                </div>
            </nav>  
        </div>
        <div class="panel-block">
            <nav class="level">
                <div class="level-left">
                    <div class="level-item">
                        <p>Textur-ID</p> 
                    </div>
                </div>
                <div class="level-right" style="width: 80%">
                    <input bind:value="{textureId}" class="input" type="text">
                </div>
            </nav>
        </div>
        -->
        <div class="panel-block">
            <div class="level" style="width: 100%;">
                <div class="level-center" style="width: 100%;">
                    <div class="columns">
                        <div class="column is-half">
                            <button on:click="{openDeleteYearModal}" class="button is-danger" style="width: 100%;">
                                Löschen
                            </button>
                        </div>
                        <div class="column is-half">
                            <button on:click="{saveData}" class="button is-primary " style="width: 100%;">
                                Speichern
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </article>        
</div>

<style>

    #yearEditWindow {
        z-index: 400;
    }

    .level {
        width: 100%;
    }

    .modal {
        z-index: 500;
    }

    .panel {
        background-color: white;
    }

</style>

