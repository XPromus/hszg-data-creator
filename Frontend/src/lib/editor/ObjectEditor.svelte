<script lang="ts">
    
    import * as Object from '../api/objects';
    import * as Year from '../api/years';
    import * as MediaAPI from '../api/media';
    import { onMount } from "svelte";
    import { fade } from 'svelte/transition';
    
    import SaveButton from './SaveButton.svelte';
    import YearPanelButton from './YearPanelButton.svelte';
    import MediaPanelButton from './MediaPanelButton.svelte';

    export let objectId: number;
    export let closeFunction = () => {};
    export let deleteFunction = () => {};
    export let openYearEditorFunction = () => {};

    let objectData;
    let deleteObjectModal;
    let objectName: string;

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
        const data = { "newName": objectName }
        const responseStatus = await Object.editObject(objectId, data);

        if (responseStatus == 200) {
            saveButton.setSaveState(saveButton.saveStateEnum.success);
        } else {
            saveButton.setSaveState(saveButton.saveStateEnum.failure);
        }

    }

    async function closeEditor(): Promise<void> {
        closeFunction();
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
        await reloadYearList();
        await reloadMediaList();
        console.log(objectData);
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
            <a bind:this="{tabs[3]}" href="#" on:click="{() => changeMenuState(3)}" >Optionen</a>
        </p>
        {#if menuState == 0}
            <div class="panel-block">
                <p class="control has-icons-left">
                    <input bind:value="{objectName}" class="input" type="text" placeholder="Name des Objekts">
                    <span class="icon is-left">
                        <i class="fa-solid fa-house" aria-hidden="true"></i>
                    </span>
                </p>
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
            <div class="panel-block">
                <div class="columns is-gapless" style="width: 100%;">
                    <div class="column is-half">
                        <button on:click="{addMedia}" class="button is-success is-fullwidth" style="margin-right: 2.5px;">
                            Medium Erstellen
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
        {:else if menuState == 3}
            <div class="panel-block">
                <button on:click="{openObjectDeleteModal}" class="button is-danger is-fullwidth">
                    Löschen
                </button>
            </div>
            <div class="panel-block">
                <SaveButton bind:this="{saveButton}" saveFuntion="{saveEditorData}"/>
            </div>
        {/if}
    </article>
</div>

<style>

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