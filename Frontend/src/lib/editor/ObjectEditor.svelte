<script lang="ts">
    
    import * as Object from '../api/objects';
    import * as Year from '../api/years';
    import { onMount } from "svelte";
    import { fade } from 'svelte/transition';

    import YearPanelButton from './YearPanelButton.svelte';

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

    let tabs = new Array(4);
    let menuState: number = 0;

    async function addYear() {
        let year = createYearModalValue;
        if (isNaN(year)) {
            createYearModalValue = "Es muss eine Zahl eingegeben werden";
        } else {
            await Year.createYear(objectId, year);
            await reloadYearList();
            closeModal();
        }        
    }

    async function closeEditor() {
        
        let data = {
            "newName": objectName
        }
        await Object.editObject(objectId, data);

        closeFunction();
    }

    function changeMenuState(state: number) {
        tabs[menuState].classList.remove("is-active");
        menuState = state;
        tabs[menuState].classList.add("is-active");
    }

    function openModal() {
        createYearModal.classList.add("is-active");
    }

    function closeModal() {
        createYearModal.classList.remove("is-active");
    }

    function openObjectDeleteModal() {
        deleteObjectModal.classList.add("is-active");
    }

    function closeObjectDeleteModal() {
        deleteObjectModal.classList.remove("is-active");
    }

    async function deleteObject() {
        deleteObjectModal.classList.remove("is-active");
        await Object.deleteObject(objectId);
        deleteFunction();
    }

    export async function reloadYearList() {
        let yearData = await Year.getAllYearsFromObject(objectId);
        let yearIdArray = [];
        for (let i = 0; i < yearData.length; i++) {
            let year = yearData[i];
            console.log(year);
            yearIdArray.push(year.id);
        }
        years = yearIdArray;
    }

    export async function reloadEditor(id: number) {
        objectId = id;
        let dataPromise = Promise.resolve(Object.getObjectById(objectId));
        objectData = await dataPromise;
        objectName = objectData.name;
        await reloadYearList();
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
            <a bind:this="{tabs[2]}" href="#" on:click="{() => changeMenuState(2)}" >Bilder</a>
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
                <span>In Entwicklung</span>
            </div>
        {:else if menuState == 3}
            <div class="panel-block">
                <button on:click="{openObjectDeleteModal}" class="button is-danger is-fullwidth">
                    Löschen
                </button>
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