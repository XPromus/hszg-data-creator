<script lang="ts">
    
    import Year from './Year.svelte'

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

</script>

<div class="accordion overflow-auto" id="{yearListId}">
    {#each yearIDList as year, i (year.id)}
        <Year bind:this={years[i]} removeFunction={() => handleRemove(i)} yearId={year.id} accordionId={yearListId}/>
    {/each}
</div>

<style>

    .accordion {
        max-height: 650px;
    }

</style>