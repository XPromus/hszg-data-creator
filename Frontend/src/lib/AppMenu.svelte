<script lang="ts">

    import IdentifierEditor from "./identifier/IdentifierEditor.svelte";
    import IdentifierUser from "./identifier/use/IdentifierUser.svelte";
    import Map from './map/Map.svelte';

    let mapState: boolean = false;
    let identifierEditorState: boolean = false;
    let identifierUserState: boolean = false;

    function openIdentifierEditor() {
        identifierEditorState = true;
        identifierUserState = false;
        mapState = false;
    }

    function openIdentifierUser() {
        identifierEditorState = false;
        identifierUserState = true;
        mapState = false;
    }

    function openMap() {
        identifierEditorState = false;
        identifierUserState = false;
        mapState = true;
    }

    function closeWindow() {
        identifierEditorState = false;
        identifierUserState = false;
        mapState = false;
    }

</script>

<div>
    {#if identifierEditorState}
        <IdentifierEditor closeFunction="{closeWindow}" />
    {:else if identifierUserState}
        <IdentifierUser closeFunction="{closeWindow}" />
    {:else if mapState}
        <Map closeFunction="{closeWindow}" />
    {:else}
        <div id="menuButtons" class="columns" style="width: 100%;">
            <div class="column is-one-third">
                <button on:click="{openMap}" class="button is-success" style="width: 100%;">
                    Karte
                </button>
            </div>
            <div class="column is-one-third">
                <button on:click="{openIdentifierEditor}" class="button is-success" style="width: 100%;">
                    Fragebogen
                </button>
            </div>
            <div class="column is-one-third">
                <button on:click="{openIdentifierUser}" class="button is-success" style="width: 100%;">
                    Fragebogen Editor
                </button>
            </div>
        </div>
    {/if}
</div>

<style>

    #menuButtons {
        margin-top: 10px;
        margin-left: 10px;
        margin-right: 10px;
    }

</style>