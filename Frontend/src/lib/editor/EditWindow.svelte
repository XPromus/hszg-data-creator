<script lang="ts">

    import YearList from "./years/YearList.svelte";
    import ImageView from "./images/ImageView.svelte";
    import ImageUploadModal from "./images/ImageUpload.svelte";

    export let house;

    let houseName: string = house.name;

    const headerId = 0;
    const objectName = "Test Haus";
    const listID = "yearList_test";

    let yearList: YearList;
    const yearListKey: string = "years";

</script>

<div id="editWindow" class="card" style="width: 40rem;">
    <div class="card-body">
        <div class="container">
            <div class="row">
                <div class="col-9" style="text-align: left;">
                    <h5>{houseName}</h5>
                </div>
                <div class="col-3" style="text-align: right;">
                    <button type="button" class="btn btn-secondary">
                        <span>X</span>
                    </button>
                    <button type="button" class="btn btn-danger" data-toggle="tooltip" data-placement="bottom" title="Objekt löschen">
                        <i class="fa-solid fa-trash-can"></i>
                    </button>
                </div>
            </div>
            <div class="row">
                <div class="accordion" id="editWindowAccordion">
                    <div class="accordion-item">
                        <h2 class="accordion-header" id="yearHeader">
                            <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#yearCollapse" aria-expanded="false" aria-controls="yearCollapse">
                                Jahre
                            </button>
                        </h2>
                        <div id="yearCollapse" class="accordion-collapse collapse" aria-labelledby="yearHeading" data-bs-parent="#editWindowAccordion">
                            <div class="accordion-body">
                                <div id="objectButtons" class="row">
                                    <div class="col-12">
                                        <button on:click="{yearList.handleAdd}" type="button" class="btn btn-success" data-toggle="tooltip" data-placement="bottom" title="Jahr erstellen" style="width: 100%;">
                                            <!--<i class="fa-solid fa-plus"></i>-->
                                            <span>Jahr erstellen</span>
                                        </button>
                                    </div>
                                </div>
                                <div id="yearList">
                                    <YearList bind:this={yearList} yearListId="{listID}" />
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="accordion-item">
                        <h2 class="accordion-header" id="imageHeader">
                            <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#imageCollapse" aria-expanded="false" aria-controls="imageCollapse">
                                Bilder
                            </button>
                        </h2>
                        <div id="imageCollapse" class="accordion-collapse collapse" aria-labelledby="imageHeading" data-bs-parent="#editWindowAccordion">
                            <div class="accordion-body">
                                <ImageView />
                                <div id="imageUploadModal" class="container-fluid">
                                    <ImageUploadModal />
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<style>

    #editWindowAccordion {
        margin-top: 10px;
    }

    #editWindow {

        width: 33%;
        height: 90%;
        position: absolute;
        top: 0;
        left: 0;

        margin-top: 85px;
        margin-left: 10px;

        z-index: 500;

        overflow: auto;

    }

    #objectButtons {
        margin-top: 10px;
    }

    #yearList {
        margin-top: 5px;
    }

</style>