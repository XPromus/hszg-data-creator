<script lang="ts">

    import type Year from './Year.svelte';
    import YearList from './YearList.svelte'
    
    export let objectID: number;
    export let accordionId: string;

    export let removeFunction;

    const listID: string = "yearList_" + objectID;
    const objectName: string = "Object " + objectID;
    const headerId: string = "header_" + objectID;
    const collapseId: string = "collapse_" + objectID;
    
    let yearList: YearList;
    
    export function toJSON(): string {
        
        let ret = '{'
            + '"objectName" : ' + objectName + ','
            + '"years" : [';

        const years: Year[] = yearList.getYears();
        for (let i = 0; i < years.length; i++) {
             ret += years[i].toJSON() + ',';
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
            <div>
                <button on:click="{removeFunction}" type="button" class="btn btn-danger" data-toggle="tooltip" data-placement="bottom" title="Objekt löschen" style="margin-bottom: 10px;">
                    <i class="fa-solid fa-trash-can"></i>
                </button>
                <ul class="card card-body">
                    <p>
                        Jahre
                    </p>
                    <button on:click="{yearList.handleAdd}" type="button" class="btn btn-success" style="margin-bottom: 10px;" data-toggle="tooltip" data-placement="bottom" title="Jahr erstellen">
                        <i class="fa-solid fa-plus"></i>
                    </button>
                    <YearList bind:this={yearList} yearListId="{listID}" />
                </ul>
            </div>
        </div>
    </div>
</div>

<style>

</style>