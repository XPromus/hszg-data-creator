<script lang="ts">
    
    import * as Object from '../api/objects';
    import * as Year from '../api/years';
    import * as MediaAPI from '../api/media';
    import { onMount } from "svelte";
    import { fade } from 'svelte/transition';

    import SaveButton from './SaveButton.svelte';
    import YearPanelButton from './YearPanelButton.svelte';
    import MediaPanelButton from './MediaPanelButton.svelte';
    import ImageUploadField from './ImageUploadField.svelte';

    export let objectId: number;
    export let closeFunction = () => {};
    export let deleteFunction = () => {};
    export let openYearEditorFunction = () => {};
    export let changeMarkerPosition = () => {};

    let objectData;
    let deleteObjectModal;
    let objectName: string;
    let objectLat: number;
    let objectLong: number;

    let years = [];
    let createYearModal;
    let createYearModalValue;

    let medias = [];
    let mediaObjects = [];

    let tabs = new Array(4);
    let menuState: number = 0;

    let saveButton;

    async function addMedia(): Promise<void> {
        await MediaAPI.createMedia(objectId, "");
        await reloadMediaList();
    }

    async function addYear(): Promise<void> {
        let year = createYearModalValue;
        if (isNaN(year)) {
            createYearModalValue = "Es muss eine Zahl eingegeben werden";
        } else {
            await Year.createYear(objectId, year);
            await reloadYearList();
            closeModal();
        }        
    }

    async function saveMediaData() {
        for (let i = 0; i < mediaObjects.length; i++) {
            mediaObjects[i].saveData();
        }
    }

    async function saveEditorData() {
        await saveMediaData();
        const data = { "newName": objectName, "newLatitude": objectLat, "newLongitude": objectLong };
        const responseStatus = await Object.editObject(objectId, data);

        if (responseStatus == 200) {
            saveButton.setSaveState(saveButton.saveStateEnum.success);
            changeMarkerPosition();
        } else {
            saveButton.setSaveState(saveButton.saveStateEnum.failure);
        }

    }

    async function closeEditor(): Promise<void> {
        closeFunction();
    }

    export function changeSavedPosition(lat, lng) {
        objectLat = lat;
        objectLong = lng;
    }

    function changeMenuState(state: number) {
        tabs[menuState].classList.remove("is-active");
        menuState = state;
        tabs[menuState].classList.add("is-active");
    }

    function openModal(): void {
        createYearModal.classList.add("is-active");
    }

    function closeModal(): void {
        createYearModal.classList.remove("is-active");
    }

    function openObjectDeleteModal(): void {
        deleteObjectModal.classList.add("is-active");
    }

    function closeObjectDeleteModal(): void {
        deleteObjectModal.classList.remove("is-active");
    }

    function deleteMedia() {
        reloadMediaList();
    }

    async function deleteObject(): Promise<void> {
        deleteObjectModal.classList.remove("is-active");
        await Object.deleteObject(objectId);
        deleteFunction();
    }

    async function reloadMediaList(): Promise<void> {
        medias = Array(0);
        let mediaData = await (await MediaAPI.getMediaByObjectId(objectId)).json();
        let mediaIdArray = [];
        for (let i = 0; i < mediaData.length; i++) {
            let media: MediaAPI.Media = mediaData[i];
            mediaIdArray.push(media.id);
        }
        mediaObjects = Array(mediaIdArray.length);
        medias = mediaIdArray;
    }

    export async function reloadYearList(): Promise<void> {
        years = Array(0);
        let yearData = await Year.getAllYearsFromObject(objectId);
        let yearIdArray = [];
        for (let i = 0; i < yearData.length; i++) {
            let year = yearData[i];
            yearIdArray.push(year.id);
        }
        years = yearIdArray;
    }

    export async function reloadEditor(id: number): Promise<void> {
        objectId = id;
        let dataPromise = Promise.resolve(Object.getObjectById(objectId));
        objectData = await dataPromise;
        objectName = objectData.name;
        objectLat = objectData.latitude;
        objectLong = objectData.longitude;
        await reloadYearList();
        await reloadMediaList();
        console.log(objectData);
    }

    export function setDefaultEditorPage() {
        changeMenuState(0);
    }

    onMount(async () => {
        await reloadEditor(objectId);
    });

</script>

<div bind:this="{createYearModal}" class="modal is-clipped">
    <div class="modal-background"></div>
    <div class="modal-card">
        <header class="modal-card-head">
            <p class="modal-card-title">Jahr Erstellen</p>
            <button on:click="{closeModal}" class="delete" aria-label="close"></button>
        </header>
        <section class="modal-card-body">
            <input bind:value="{createYearModalValue}" class="input is-primary" type="text" placeholder="Jahr Format: yyyy">
        </section>
        <footer class="modal-card-foot">
            <button on:click="{addYear}" class="button is-success">Jahr erstellen</button>
            <button on:click="{closeModal}" class="button">Abbrechen</button>
        </footer>
    </div>
</div>

<div bind:this="{deleteObjectModal}" class="modal is-clipped">
    <div class="modal-background"></div>
    <div class="modal-card">
        <header class="modal-card-head">
            <p class="modal-card-title">Das Objekt wirklich löschen</p>
            <button on:click="{closeModal}" class="delete" aria-label="close"></button>
        </header>
        <footer class="modal-card-foot">
            <button on:click="{deleteObject}" class="button is-danger">Löschen</button>
            <button on:click="{closeObjectDeleteModal}" class="button">Abbrechen</button>
        </footer>
    </div>
</div>

<div transition:fade="{{duration: 250}}" id="editWindow" class="column is-one-third">
    <article class="panel is-success">
        <nav class="panel-heading level">
            <div class="level-left">
                <div class="level-item">
                    Objekt Editieren
                </div>
            </div>
            <div class="panel-right">
                <div class="columns">
                    <div class="column is-full">
                        <button on:click="{closeEditor}" class="button is-success">
                            <i class="fa-solid fa-xmark"></i>
                        </button>
                    </div>
                </div>
            </div>
        </nav>
        <p class="panel-tabs">
            <!-- svelte-ignore a11y-invalid-attribute -->
            <a bind:this="{tabs[0]}" href="#" on:click="{() => changeMenuState(0)}" class="is-active">Infos</a>
            <!-- svelte-ignore a11y-invalid-attribute -->
            <a bind:this="{tabs[1]}" href="#" on:click="{() => changeMenuState(1)}" >Jahre</a>
            <!-- svelte-ignore a11y-invalid-attribute -->
            <a bind:this="{tabs[2]}" href="#" on:click="{() => changeMenuState(2)}" >Medien</a>
            <!-- svelte-ignore a11y-invalid-attribute -->
            <a bind:this="{tabs[3]}" href="#" on:click="{() => changeMenuState(3)}" >Cloud Medien</a>
            <!-- svelte-ignore a11y-invalid-attribute -->
            <a bind:this="{tabs[4]}" href="#" on:click="{() => changeMenuState(4)}" >Optionen</a>
        </p>
        <div id="panelContent">
            {#if menuState == 0}
                <div class="panel-block">
                    <nav class="level">
                        <div class="level-left">
                            <div class="level-item">
                                <p>Objektname</p>
                            </div>
                        </div>
                        <div class="level-right" style="width: 80%;">
                            <input bind:value="{objectName}" class="input" type="text" placeholder="Name des Objekts">
                        </div>
                    </nav>
                </div>
                <div class="panel-block">
                    <nav class="level">
                        <div class="level-left">
                            <div class="level-item">
                                <p>Breitengrad</p>
                            </div>
                        </div>
                        <div class="level-right" style="width: 80%;">
                            <input bind:value="{objectLat}" class="input" type="text">
                        </div>
                    </nav>
                </div>
                <div class="panel-block">
                    <nav class="level">
                        <div class="level-left">
                            <div class="level-item">
                                <p>Längengrad</p>
                            </div>
                        </div>
                        <div class="level-right" style="width: 80%;">
                            <input bind:value="{objectLong}" class="input" type="text">
                        </div>
                    </nav>
                </div>
                <div class="panel-block">
                    <SaveButton bind:this="{saveButton}" saveFuntion="{saveEditorData}"/>
                </div>
            {:else if menuState == 1}
                <div class="panel-block">
                    <button on:click="{openModal}" class="button is-success is-fullwidth">
                        Jahr Erstellen
                    </button>
                </div>
                {#each years as year}
                    <YearPanelButton openYearEditorFunction={openYearEditorFunction} yearId="{year}" />
                {/each}
            {:else if menuState == 2}
                <ImageUploadField objectId="{objectId}"/>
            {:else if menuState == 3}
                <div class="panel-block">
                    <div class="columns is-gapless" style="width: 100%;">
                        <div class="column is-half">
                            <button on:click="{addMedia}" class="button is-success is-fullwidth" style="margin-right: 2.5px;">
                                Cloud Medium Erstellen
                            </button>
                        </div>
                        <div class="column is-half">
                            <button on:click="{saveMediaData}" class="button is-primary is-fullwidth" style="margin-left: 2.5px;">
                                Speichern
                            </button>
                        </div>
                    </div>
                </div>
                {#each medias as media, i}
                    <MediaPanelButton bind:this="{mediaObjects[i]}" deleteFunction="{deleteMedia}" mediaId="{media}" />
                {/each}
            {:else if menuState == 4}
                <div class="panel-block">
                    <button on:click="{openObjectDeleteModal}" class="button is-danger is-fullwidth">
                        Löschen
                    </button>
                </div>
            {/if}
        </div>
        
    </article>
</div>

<style>

    #panelContent {
        max-height: 700px;
        overflow: auto;
    }

    .level {
        width: 100%;
    }

    #editWindow {
        z-index: 400;
        overflow: auto;
    }

    .modal {
        z-index: 500;
    }

    .panel {
        background-color: white;
    }

</style>