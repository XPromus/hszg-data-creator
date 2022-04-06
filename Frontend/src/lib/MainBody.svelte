<script lang="ts">

    import Object from './Object.svelte';

    let numberOfObjects: number = 3;
    let accordionId: string = "objectAccordion";

    let objects = [];
    let objectIDList = [];
    let lastID = 0;

    export function getObjects(): Object[] {
        return objects;
    }

    const handleRemove = (i: number) => {
        objectIDList = [
            ...objectIDList.slice(0, i),
            ...objectIDList.slice(i + 1, objectIDList.length)
        ];
    };

    export function handleAdd() {
        objectIDList = [...objectIDList, {id: lastID++}];
    }

    $: objects = objects.filter(el => el);

</script>

<div class="accordion" id="{accordionId}">
    {#each objectIDList as object, i (object.id)}
        <Object bind:this={objects[i]} removeFunction={() => handleRemove(i)} objectID={object.id} accordionId="{accordionId}"/>
    {/each}
</div>

<style>

</style>