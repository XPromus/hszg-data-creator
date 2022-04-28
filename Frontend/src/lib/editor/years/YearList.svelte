<script lang="ts">
import { onMount } from 'svelte';

    
    import Year from '../years/Year.svelte';

    export let data = [];
    export let yearListId: string;

    let years = [];
    let yearIDList = [];
    let lastID = 0;

    export function getYears() {
        return years;
    }

    export const handleAdd = () => {
        yearIDList = [...yearIDList, {id: lastID++}];
    };

    const handleRemove = (i: number) => {
        yearIDList = [
            ...yearIDList.slice(0, i),
            ...yearIDList.slice(i + 1, yearIDList.length)
        ];
    };

    $: years = years.filter(el => el);

    onMount(async () => {

        if (data != undefined) {
            const yearNum: number = data.length;
            for (let i = 0; i < yearNum; i++) {
                handleAdd();   
            }
        }

    })

</script>

<div class="accordion overflow-auto" id="{yearListId}">
    {#each yearIDList as year, i (year.id)}
        <Year bind:this={years[i]} data="{data[i]}" removeFunction={() => handleRemove(i)} yearId={year.id} accordionId={yearListId}/>
    {/each}
</div>

<style>

</style>