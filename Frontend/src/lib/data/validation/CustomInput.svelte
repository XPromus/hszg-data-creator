<script lang="ts">

    export let value: string;
    export let placeholder: string;
    export let regex: RegExp;

    export let emptyAllowed: boolean;

    let inputClass: string = "input";

    function asessInput(): void {

        let result: boolean = regex.test(value);
        if (emptyAllowed) {
            if (value == "" || value == null) {
                result = true;
            }
        }
        
        inputClass = (result) ? "input" : "input is-danger";

    }

</script>

<input on:keyup="{asessInput}" bind:value="{value}" class="{inputClass}" type="text" placeholder="{placeholder}">

<style>

</style>