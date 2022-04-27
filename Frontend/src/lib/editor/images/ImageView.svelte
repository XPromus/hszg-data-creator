<script lang="ts">

    import Image from "./Image.svelte";
    import ImageModal from "./ImageUpload.svelte";

    let images: string[] = [
        "test_house.jpg",
        "test_house.jpg",
        "test_house.jpg",
        "test_house.jpg",
        "test_house.jpg"
    ];
    let rowWidth: number = 3;
    let rowNumber: number = Math.floor(images.length / rowWidth); 
    let lastImages: number = images.length % rowWidth;

    export function getImages() {
        return images;
    }

    export function addImage(path: string) {
        images = [...images, path];
    }

    export function removeImage(index: number) {
        images = [
            ...images.slice(0, index),
            ...images.slice(index + 1, images.length)
        ];
    }

</script>

<div id="imageCarouselControls" class="carousel slide" data-bs-ride="carousel">
    <div class="container" id="carouselButtons">
        <div class="row justify-content-center">
            <div class="col-6">
                <button class="btn btn-secondary btn-carousel" type="button" data-bs-target="#imageCarouselControls" data-bs-slide="prev">
                    Previous
                </button>
            </div>

            <!--
            {#each images as image, i}
                {#if i == images.length - 1}
                    <button class=" col-1 btn btn-secondary" type="button" data-bs-target="#imageCarouselControls" data-bs-slide-to="{i}" aria-current="true" style="marginleft: 5px;">
                        {i + 1}
                    </button>
                {:else}
                    <button class=" col-1 btn btn-secondary" type="button" data-bs-target="#imageCarouselControls" data-bs-slide-to="{i}" style="margin-right: 5px;">
                        {i + 1}
                    </button>
                {/if}
            {/each}
            -->

            <div class="col-6">
                <button class="btn btn-secondary btn-carousel" type="button" data-bs-target="#imageCarouselControls" data-bs-slide="next">
                    Next
                </button>
            </div>
        </div>
    </div>

    <div class="carousel-inner">
        {#each images as image, i}
            {#if i == 0}
                <div class="carousel-item active">
                    <Image source="{image}" />
                </div>
            {:else}
                <div class="carousel-item">
                    <Image source="{image}" />
                </div>
            {/if}
        {/each}
    </div>
    
</div>

<style>

    .btn-carousel {
        /*width: 100px;*/
        width: 100%;
        margin-bottom: 10px;
    }

</style>