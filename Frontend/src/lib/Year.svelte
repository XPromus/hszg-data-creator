<script lang="ts">

    var year = "yyyy";
    var objectName = "name";

    export let headerId;
    export let collapseId;
    export let accordionId;

</script>

<div class="accordion-item">
    <h2 class="accordion-header" id="{headerId}">
        <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#{collapseId}" aria-expanded="false" aria-controls="#{collapseId}">
            {year}
        </button>
    </h2>
    <div id="{collapseId}" class="accordion-collapse collapse" aria-labelledby="{headerId}" data-bs-parent="#{accordionId}">
        <div class="accordion-body">
            <div class="input-group mb-3">
                <span class="input-group-text">Jahr</span>
                <input bind:value={year} type="text" class="form-control" placeholder="yyyy-mm-dd" aria-label="Jahr">
            </div>
            <div class="input-group mb-3">
                <span class="input-group-text">Name des Objekts</span>
                <input bind:value={objectName} type="text" class="form-control" placeholder="Name" aria-label="Name">
            </div>
        </div>
    </div>
</div>

<!--
<li class="list-group-item">
    <h6>{year}</h6>
    <div class="input-group mb-3">
        <span class="input-group-text">Jahr</span>
        <input bind:value={year} type="text" class="form-control" placeholder="yyyy-mm-dd" aria-label="Jahr">
    </div>
    <div class="input-group mb-3">
        <span class="input-group-text">Name des Objekts</span>
        <input bind:value={objectName} type="text" class="form-control" placeholder="Name" aria-label="Name">
    </div>
</li>
-->

<style>
    
</style>