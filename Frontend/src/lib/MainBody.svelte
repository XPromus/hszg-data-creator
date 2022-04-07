<script lang="ts">

    import Object from './Object.svelte';

    let accordionId: string = "objectAccordion";

    let objects = [];
    let objectIDList = [];
    let lastID = 0;

    export function getObjects() {
        return objects;
    }

    export const handleAdd = () => {
        objectIDList = [...objectIDList, {id: lastID++}];
    }


    const handleRemove = (i: number) => {
        objectIDList = [
            ...objectIDList.slice(0, i),
            ...objectIDList.slice(i + 1, objectIDList.length)
        ];
    };

    
    $: objects = objects.filter(el => el);

</script>

<div class="accordion" id="{accordionId}">
    {#each objectIDList as object, i (object.id)}
        <Object bind:this={objects[i]} removeFunction={() => handleRemove(i)} objectID={object.id} accordionId="{accordionId}"/>
    {/each}
</div>

<style>

</style>