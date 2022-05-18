<script lang="ts">
    
    import * as ImageAPI from "../api/images";
    import { onMount } from "svelte";

    export let objectId: number;

    let objectImages: ImageAPI.Image[] = [];
    let objectImageBase = [];
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
        objectImageBase = Array(objectImages.length);

        for (let i = 0; i < objectImages.length; i++) {
            let imageId: number = objectImages[i].id;
            let base64 = await getImageAsBase64(imageId);
            objectImageBase[i] = base64;
        }

    }

    async function getImageAsBase64(imageId: number) {
        const data = await getImageBlob(imageId);
        const base64 = await convertBlobToBase64(data);
        return base64;
    }

    async function getImageBlob(imageId: number) {
        const imageBlob = await ImageAPI.getImage(imageId);
        return imageBlob;
    }
    
    async function deleteImage(imageId: number) {
        await ImageAPI.deleteImage(imageId);
        await getAllImages();
    }

    const convertBlobToBase64 = async (blob) => {
        return await blobToBase64(blob);
    }

    const blobToBase64 = blob => new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.readAsDataURL(blob);
        reader.onload = () => resolve(reader.result);
        reader.onerror = error => reject(error);
    });

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
                    <img style="margin-right: 5px;" width="25px" src="{objectImageBase[i]}" alt="">
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