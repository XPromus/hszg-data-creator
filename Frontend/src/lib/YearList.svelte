<script lang="ts">
    import Year from './Year.svelte'

    export let numberOfYears: number;
    export let yearListId: string;

    function getIdElement(prefix: string, counter: number): string {
        return yearListId + prefix + (counter + 1).toString();
    }

</script>

<div class="accordion" id="{yearListId}">
    {#each Array(numberOfYears) as _, i}
        <Year headerId="{getIdElement("year_header_", i)}" collapseId="{getIdElement("year_collapse_", i)}" accordionId={yearListId}/>
    {/each}
</div>

<style>

</style>