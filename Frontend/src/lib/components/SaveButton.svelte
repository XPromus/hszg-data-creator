<script lang="ts">

    export let saveFunction;
    export let iconChangeTime: number;

    const defaultButtonIcon: string = "fa-solid fa-floppy-disk";
    const successButtonIcon: string = "fa-solid fa-check";
    const failureButtonIcon: string = "fa-solid fa-xmark";
    let buttonIcon: string = defaultButtonIcon;

    async function save() {
        let status: number = await saveFunction();
        console.log("Status: " + status);
        if (status == 200) {
            buttonIcon = successButtonIcon;
        } else {
            buttonIcon = failureButtonIcon;
        }
        setTimeout(function () {
            buttonIcon = defaultButtonIcon;
        }, iconChangeTime);
    }

</script>

<button on:click="{save}" class="button is-success">
    <i class={buttonIcon}></i>
</button>

<style>

</style>