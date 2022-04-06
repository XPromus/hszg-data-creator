<script lang="ts">

    import type Year from './Year.svelte';
    import YearList from './YearList.svelte'
    
    export let objectID: number;
    export let accordionId: string;

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

        alert(ret);
        return ret;

    }

    function addYear(): void  {
        yearList.createYear();
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
                <button on:click="{addYear}" type="button" class="btn btn-success" style="margin-bottom: 10px;" data-toggle="tooltip" data-placement="bottom" title="Jahr erstellen">
                    <i class="fa-solid fa-plus"></i>
                </button>
                <YearList bind:this={yearList} yearListId="{listID}" />
            </ul>
        </div>
    </div>
</div>

<style>

</style>