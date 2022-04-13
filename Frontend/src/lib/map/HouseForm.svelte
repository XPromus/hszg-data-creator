<script lang="ts">

    export let height: number;
    export let width: number;

    export let custom: string;
    let override: boolean;

    if (custom != "") {
        override = true;
    } else {
        override = false;
    }

    const color: string = "#4b6584";

</script>

<svg width="{width}" height="{height}">

    {#if override == false}
        <rect width="{width}" height="{height}" style="fill: {color}; stroke-width: 1; stroke: black;"/>
    {/if}

</svg>

<style>

</style>