<script lang="ts">

    import Object from './Object.svelte';

    let numberOfObjects: number = 3;
    let accordionId: string = "objectAccordion";

</script>

<div class="accordion" id="{accordionId}">
    {#each Array(numberOfObjects) as _, i}
        <Object objectID={i} accordionId="{accordionId}"/>
    {/each}
</div>

<style>

</style>