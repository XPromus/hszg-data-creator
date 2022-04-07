<script lang="ts">
    
    import { onMount } from 'svelte';
    import type Year from './Year.svelte';
    import YearList from './YearList.svelte'
    
    export let objectID: number;
    const objectIDKey: string = "id";

    export let accordionId: string;
    export let removeFunction;

    const listID: string = "yearList_" + objectID;
    const headerId: string = "header_" + objectID;
    const collapseId: string = "collapse_" + objectID;

    let objectName: string = "Object " + objectID;
    const objectNameKey: string = "objectName";

    let yearList: YearList;
    const yearListKey: string = "years";
    
    onMount(async ()=> {
        //TODO: Fetch object data
    })

    export function fromJSONObj(obj): void {
        objectName = obj.objectName;
        
        const years = obj.years;
        for (let i = 0; i < years.length; i++) {
            const yearData = years[i];
            yearList.handleAdd();
            const year: Year = yearList.getYears()[i];
            year.fromJSONObj(yearData);
        }
    }

    export function fromJSON(data: string): void {
        const obj = JSON.parse(data);
        fromJSONObj(obj);
    }

    export function toJSON(): string {
        
        let ret = '{'
            + '"' + objectIDKey + '" : ' + objectID + ','
            + '"' + objectNameKey + '" : "' + objectName + '",'
            + '"' + yearListKey + '" : [';

        const years: Year[] = yearList.getYears();
        for (let i = 0; i < years.length; i++) {
             ret += years[i].toJSON();
             if (i < years.length - 1) {
                ret += ',';
             }
        }

        ret += ']}';

        return ret;

    }

</script>

<div class="accordion-item">
    <h2 class="accordion-header" id="{headerId}">
        <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#{collapseId}" aria-expanded="false" aria-controls="#{collapseId}">
            {objectName}
        </button>    
    </h2>
    <div id="{collapseId}" class="accordion-collapse collapse" aria-labelledby="{headerId}" data-bs-parent="#{accordionId}">
        <div class="accordion-body">
            <ul class="card card-body">
                <p>
                    Jahre
                </p>
                <button on:click="{yearList.handleAdd}" type="button" class="btn btn-success" style="margin-bottom: 10px;" data-toggle="tooltip" data-placement="bottom" title="Jahr erstellen">
                    <i class="fa-solid fa-plus"></i>
                </button>
                <YearList bind:this={yearList} yearListId="{listID}" />
            </ul>
            <div>
                <button on:click="{removeFunction}" type="button" class="btn btn-danger" data-toggle="tooltip" data-placement="bottom" title="Objekt löschen">
                    <i class="fa-solid fa-trash-can"></i>
                </button>
            </div>
        </div>
    </div>
</div>

<style>

</style>