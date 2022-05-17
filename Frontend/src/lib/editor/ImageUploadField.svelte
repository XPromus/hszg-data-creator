<script lang="ts">
    
    import { get } from "svelte/store";
    import { Base64 } from 'js-base64';
    import * as ImageAPI from "../api/images";
    import { onMount } from "svelte";

    export let objectId: number;

    let objectImages: ImageAPI.Image[] = [];
    let files: FileList;

    $: if (files) {
        console.log(files);
        for (const file of files) {
            console.log(`${file.name}: ${file.size} bytes`);
        }
    }

    async function getAllImages() {
        objectImages = Array(0);
        objectImages = await ImageAPI.getImagesFromObject(objectId);
    }

    async function getImageById(id: number) {
        let image = await ImageAPI.getImage(id);
        return image;
    }
    
    async function deleteImage(imageId: number) {
        await ImageAPI.deleteImage(imageId);
        await getAllImages();
    }

    function imageFromObject(image) {
        const data = getImageById(image.id);
        return binaryToBase64(data);
    }

    function binaryToBase64(str) {

        let reader = new FileReader();
        reader.readAsDataURL(str);
        reader.onloadend = function() {
            let base64Data = reader.result;
            console.log(base64Data);
        }

    }

    async function uploadFiles(): Promise<void> {
        const id: number = await ImageAPI.uploadImage(objectId, files[0]);
        getAllImages();
    }

    function clearFiles(): void {
        files = undefined;
    }

    onMount(async () => {
        await getAllImages();
    })

</script>

<div class="panel-block">
    <div class="level" style="width: 100%;">
        <div class="level-left">
            <div class="level-item">
                <div class="file has-name">
                    <label class="file-label">
                        <input bind:files class="file-input" accept="image/png, image/jpg" type="file" multiple>
                        <span class="file-cta">
                            <span class="file-icon">
                                <i class="fas fa-upload"></i>
                            </span>
                            <span class="file-label">
                                Bild wählen
                            </span>
                        </span>
                        <span class="file-name" style="width: 100%;">
                            {#if files}
                                {#if Array.from(files).length > 1}
                                    {Array.from(files).length} Dateien
                                {:else}
                                    <p>{Array.from(files)[0].name}</p>
                                {/if}
                            {/if}
                        </span>
                    </label>
                </div>
            </div>
        </div>
        <div class="level-right">
            <div class="level-item">
                <button on:click="{clearFiles}" class="button is-warning" title="Dateien aus der Auswahl entfernen">
                    <i class="fa-solid fa-xmark"></i>
                </button>
            </div>
            <div class="level-item">
                <button on:click="{uploadFiles}" class="button is-success" title="Dateien hochladen">
                    <i class="fa-solid fa-upload"></i>
                </button>
            </div>
        </div>
    </div>
</div>
{#each objectImages as image, i}
    <div class="panel-block">
        <div class="level" style="width: 100%;">
            <div class="level-left">
                <div class="level-item">
                    <span class="panel-icon">
                        <i class="fa-solid fa-image" aria-hidden="true"></i>
                    </span>
                    {image.filename}
                </div>
            </div>
            <div class="level-right">
                <div class="level-item">
                    <button on:click="{() => deleteImage(image.id)}" class="button is-danger">
                        <i class="fa-solid fa-trash-can"></i>
                    </button>
                </div>
            </div>
        </div>
        
    </div>
{/each}


<style>

</style>