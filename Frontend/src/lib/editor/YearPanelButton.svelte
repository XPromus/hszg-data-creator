<script lang="ts">

    import { onMount } from 'svelte';
    import { setYearId } from '../data/selectedData';
    import * as Year from '../api/years';

    export let yearId: number;
    export let openYearEditorFunction = () => {};

    let year: number;

    onMount(async () => {
        let yearData = await Year.getYearById(yearId);
        year = yearData.year;
    });
    
    function openYearEditor() {
        setYearId(yearId);
        openYearEditorFunction();
    }

</script>

<!-- svelte-ignore a11y-missing-attribute -->
<a on:click="{openYearEditor}" class="panel-block">
    <div class="level">
        <div class="level-left">
            <div class="level-item">
                <span class="panel-icon">
                    <i class="fa-solid fa-calendar" aria-hidden="true"></i>
                </span>
                {year}
            </div>
        </div>
        <div class="level-right">
            <div class="level-item">
                <span class="level-item panel-icon">
                    <i class="fa-solid fa-arrow-right" aria-hidden="true"></i>
                </span> 
            </div>
        </div>
    </div>
</a>

<style>
    
    .level {
        width: 100%;
    }

</style>