<script lang="ts">

    import AddButton from './AddButton.svelte';
    import YearList from './YearList.svelte'

    export let listID: string;

    export let objectName: string = "Object";
    export let headerId: string;
    export let collapseId: string;
    export let accordionId: string;

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
                <AddButton text="Jahr hinzufügen"/>
                <YearList yearListId="{listID}"/>
            </ul>
        </div>
    </div>
</div>

<style>

</style>