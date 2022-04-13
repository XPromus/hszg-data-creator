<script lang="ts">

    import YearList from "../YearList.svelte";

    export let house;

    let houseName: string = house.name;

    const headerId = 0;
    const objectName = "Test Haus";
    const listID = "yearList_test";

    let yearList: YearList;
    const yearListKey: string = "years";

</script>

<div id="editWindow" class="card" style="width: 35rem;">
    <div class="card-body">

        <div class="container" style="margin-bottom: 10px;">
            <div class="row">
                <div class="col" style="text-align: left;">
                    <h5>{houseName}</h5>
                </div>
                <div class="col" style="text-align: right;">
                    <button type="button" class="btn btn-secondary">
                        <span>X</span>
                    </button>
                </div>
            </div>
            <button on:click="{yearList.handleAdd}" type="button" class="btn btn-success" data-toggle="tooltip" data-placement="bottom" title="Jahr erstellen">
                <!--<i class="fa-solid fa-plus"></i>-->
                <span>Jahr erstellen</span>
            </button>
            <button type="button" class="btn btn-danger" data-toggle="tooltip" data-placement="bottom" title="Objekt löschen">
                <i class="fa-solid fa-trash-can"></i>
            </button>
        </div>
        
        <div id="yearList">
            <YearList bind:this={yearList} yearListId="{listID}" />
        </div>

    </div>
</div>

<style>

    #editWindow {

        width: 33%;
        height: 75%;
        position: absolute;
        top: 0;
        left: 0;

        margin-top: 85px;
        margin-left: 10px;

        z-index: 500;

    }

</style>