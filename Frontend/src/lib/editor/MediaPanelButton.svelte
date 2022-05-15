<script lang="ts">

    import { onDestroy, onMount } from "svelte";
    import * as MediaAPI from '../api/media';

    export let deleteFunction = () => {};
    export let mediaId: number;

    let deleted: boolean = false;
    let urlInput;
    
    async function deleteMedia() {
        deleted = true;
        await MediaAPI.deleteMedia(mediaId);
        deleteFunction();
    }

    export async function saveData() {
        await MediaAPI.editMedia(mediaId, urlInput);
    }

    onMount(async () => {
        const response = await MediaAPI.getMediaById(mediaId);
        const data: MediaAPI.Media = await response.json();
        urlInput = data.url;
    });

    onDestroy(async () => {
        if (!deleted) {
            saveData();
        }
    });

</script>

<div class="panel-block">
    <p class="control has-icons-left">
        <input bind:value="{urlInput}" class="input" type="text" placeholder="Link zu Google Drive">
        <span class="icon is-left">
            <i class="fa-brands fa-google-drive"></i>
        </span>
    </p>
    <button on:click="{deleteMedia}" class="button is-danger" style="margin-left: 5px;">
        <i class="fa-solid fa-trash-can"></i>
    </button>
</div>

<style>

</style>