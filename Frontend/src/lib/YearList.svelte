<script lang="ts">
    
    import Year from './Year.svelte'

    export let yearListId: string;

    let years = [];
    let yearIDList = []

    let lastID = 0;

    export function getYears() {
        return years;
    }

    export function createYear(): void {
        yearIDList = [...yearIDList, {id: lastID++}];
    }

    function deleteYear(event) {
        years.splice(Number(event.detail.text), 1);
        years = years;
    }

    const handleRemove = (i: number) => {
        yearIDList = [
            ...yearIDList.slice(0, i),
            ...yearIDList.slice(i + 1, yearIDList.length)
        ];
    };

    $: years = years.filter(el => el);

</script>

<div class="accordion" id="{yearListId}">
    {#each yearIDList as year, i (year.id)}
        <Year bind:this={years[i]} on:message={deleteYear} removeFunction={() => handleRemove(i)} yearId={year.id} accordionId={yearListId}/>
    {/each}
</div>

<style>

</style>