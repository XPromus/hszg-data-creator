<script lang="ts">

    import { onMount } from 'svelte';
    import { createNewYear, deleteYear } from '../../data/years';
    
    import Year from '../years/Year.svelte';

    export let objectID;
    export let data = [];
    export let yearListId: string;

    let years = [];
    let yearIDList = [];

    export function getYears() {
        return years;
    }

    export async function handleNewAdd() {
        const newYear = await createNewYear(objectID, 0);
        yearIDList = [...yearIDList, newYear.id];
    }

    function handleExistingAdd(id) {
        yearIDList = [...yearIDList, id];
    }

    async function handleRemoveYear(i: number) {
        const yearID = yearIDList[i];
        deleteYear(yearID);
        yearIDList = [
            ...yearIDList.slice(0, i),
            ...yearIDList.slice(i + 1, yearIDList.length)
        ];
    }

    $: years = years.filter(el => el);

    onMount(async () => {

        if (data != undefined) {
            const yearNum: number = data.length;
            for (let i = 0; i < yearNum; i++) {
                handleExistingAdd(data[i].id);   
            }
        }

    })

</script>

<div class="accordion overflow-auto" id="{yearListId}">
    {#each yearIDList as yearID, i}
        <Year bind:this={years[i]} data="{data[i]}" removeFunction={() => handleRemoveYear(i)} yearId={yearID} accordionId={yearListId}/>
    {/each}
</div>

<style>

</style>