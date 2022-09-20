<script lang="ts">
    import { clsAlphaNoOnly } from "./textFilter";

    export let placeholder: string = "";
    export let value: string;
    let inputClass: string = "input";

    export function getValue(): string {
        return value;
    }

    function asessInput() {
        inputClass = ((clsAlphaNoOnly(value)) ? "input" : "input is-danger");
    }

</script>

<div>
    <input on:keyup="{asessInput}" bind:value="{value}" class="{inputClass}" type="text" placeholder="{placeholder}">
</div>

<style>

</style>