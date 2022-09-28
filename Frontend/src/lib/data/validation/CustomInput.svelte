<script lang="ts">

    export let value;
    export let placeholder: string;
    export let regex: RegExp;

    export let emptyAllowed: boolean;

    let inputClass: string = "input";

    function asessInput(): void {

        let result: boolean = regex.test(value);
        if (value == "" || value == null) {
            result = emptyAllowed;
        }
        
        inputClass = (result) ? "input" : "input is-danger";

    }

</script>

<input on:keyup="{asessInput}" on:click="{asessInput}" bind:value="{value}" class="{inputClass}" type="text" placeholder="{placeholder}">

<style>

</style>