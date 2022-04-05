<script lang="ts">
    import Year from './Year.svelte'
    export let yearListId: string;
</script>

<div class="accordion" id="{yearListId}">
    <Year headerId="year_header_1" collapseId="year_collapse_1" accordionId={yearListId}/>
    <Year headerId="year_header_2" collapseId="year_collapse_2" accordionId={yearListId}/>
    <Year headerId="year_header_3" collapseId="year_collapse_3" accordionId={yearListId}/>
</div>

<style>

</style>