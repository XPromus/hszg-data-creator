<script lang="ts">

    import Object from './Object.svelte';

    let numberOfObjects: number = 3;
    let accordionId: string = "objectAccordion";

    function getIdElement(prefix: string, counter: number): string {
        return prefix + (counter + 1).toString();
    }

</script>

<div class="accordion" id="{accordionId}">
    {#each Array(numberOfObjects) as _, i}
        <Object listID="{getIdElement("yearList_", i)}" objectName="{getIdElement("Object ", i)}" headerId="{getIdElement("header_", i)}" collapseId="{getIdElement("collapse_", i)}" accordionId="{accordionId}"/>
    {/each}
</div>

<style>

</style>