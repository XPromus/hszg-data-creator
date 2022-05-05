<script lang="ts">

    import { onMount } from "svelte";
    import { getDataMarkerByObjectID } from '../data/marker'
    import { createYear, editYearData } from '../data/years';
    import { uploadObjectData } from '../data/objects';

    import type Year from "./years/Year.svelte";
    import YearList from "./years/YearList.svelte";
    import ImageView from "./images/ImageView.svelte";
    
    export let data;

    let objectName: string = "Neues Haus";

    const listID = "yearList";
    let yearList: YearList;
    
    async function saveData() {

        const dataMarker = getDataMarkerByObjectID(data.id);
        data.name = objectName;
        console.log(data.name);
        await uploadObjectData(dataMarker, data.name);

        const years = yearList.getYears();
        for (let i = 0; i < years.length; i++) {
            const year = years[i].toJSON();
            await editYearData(year.id, year);
        }

    }

    export function getData() {
        return yearList;
    }

    export function getYearDataAsJSON() {

        let years: Year[] = yearList.getYears();
        let yearString: string = "";
        
        for (let i = 0; i < years.length; i++) {
            
            const year: Year = years[i];

            if (i == years.length - 1) {
                yearString += year.toJSON();
            } else {
                yearString += year.toJSON() + ",";
            }

        }

    }

    onMount(async () => {
        
        if (data != undefined) {
            objectName = data.name;
        }

    });

    export let closeFunction;
    export let deleteObject;

</script>

<div id="editWindow" class="card" style="width: 40rem;">
    <div class="card-body">
        <div class="container">
            <div class="row">
                <div class="col-9" style="text-align: left;">
                    <input bind:value="{objectName}" type="text" class="form-control" id="objectIdentifierInput" placeholder="">
                </div>
                <div class="col-3" style="text-align: right;">
                    <button on:click="{deleteObject}" type="button" class="btn btn-danger" data-toggle="tooltip" data-placement="bottom" title="Objekt löschen">
                        <i class="fa-solid fa-trash-can"></i>
                    </button>
                    <button on:click="{saveData}" type="button" class="btn btn-success" data-toggle="tooltip" data-placement="bottom" title="Daten speichern">
                        <i class="fa-solid fa-check"></i>
                    </button>
                    <button on:click="{closeFunction}" type="button" class="btn btn-dark" data-toggle="tooltip" data-placement="bottom" title="Fenster schließen">
                        <i class="fa-solid fa-x"></i>
                    </button>
                </div>
            </div>
            <div class="row">
                <div class="accordion" id="editWindowAccordion">
                    <div class="accordion-item">
                        <h2 class="accordion-header" id="yearHeader">
                            <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#yearCollapse" aria-expanded="false" aria-controls="yearCollapse">
                                Jahre
                            </button>
                        </h2>
                        <div id="yearCollapse" class="accordion-collapse collapse" aria-labelledby="yearHeading" data-bs-parent="#editWindowAccordion">
                            <div class="accordion-body">
                                <div id="objectButtons" class="row">
                                    <div class="col-12">
                                        <button on:click="{yearList.handleNewAdd}" type="button" class="btn btn-success" data-toggle="tooltip" data-placement="bottom" title="Jahr erstellen" style="width: 100%;">
                                            <span>Jahr erstellen</span>
                                        </button>
                                    </div>
                                </div>
                                <div id="yearList">
                                    <YearList objectID="{data.id}" bind:this={yearList} data="{data.years}" yearListId="{listID}" />
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="accordion-item">
                        <h2 class="accordion-header" id="imageHeader">
                            <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#imageCollapse" aria-expanded="false" aria-controls="imageCollapse">
                                Bilder (In Entwicklung)
                            </button>
                        </h2>
                        <div id="imageCollapse" class="accordion-collapse collapse" aria-labelledby="imageHeading" data-bs-parent="#editWindowAccordion">
                            <div class="accordion-body">
                                <!--
                                <ImageView />
                                -->
                                <!--
                                <ImageView />
                                <div id="imageUploadModal" class="container-fluid">
                                    <ImageUploadModal />
                                </div>
                                -->
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<style>

    #editWindowAccordion {
        margin-top: 10px;
    }

    #editWindow {

        width: 33%;
        height: 90%;
        position: absolute;
        top: 0;
        left: 0;

        margin-top: 85px;
        margin-left: 10px;

        z-index: 500;

        overflow: auto;

    }

    #objectButtons {
        margin-top: 10px;
    }

    #yearList {
        margin-top: 5px;
    }

</style>