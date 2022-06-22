<script lang="ts">

    export let classType: string;
    export let title: string;
    export let optionName: string;

</script>

<div class="notification {classType}">
    <div class="level">
        <div class="level-left">
            <div class="level-item">
                <strong>{title}</strong>
            </div>
        </div>
        <div class="level-right">
            <div class="level-item">
                Ausgewählte Option: {optionName}
            </div>
        </div>
    </div>
</div>

<style>

</style>