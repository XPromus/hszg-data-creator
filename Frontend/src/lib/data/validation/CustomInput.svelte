<script lang="ts">

    import { textValidator, requiredValidator } from './validators';
    import { createFieldValidator } from './validation';

    const [ validity, validate ] = createFieldValidator(requiredValidator(), textValidator());
    
    let value;

    export let placeholder;

    export function getValue(): string {
        return value;
    }

    export function setValue(text: string) {
        value = text;
    }

</script>

<input bind:value="{value}" class:is-danger={!$validity.valid} class:is-success={$validity.valid} use:validate={value} class="input" type="text" placeholder="{placeholder}">

<style>

</style>