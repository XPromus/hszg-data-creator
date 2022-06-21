<script lang="ts">
    
    import type { Node, Option } from './identifierStore';
    import { nodes } from './identifierStore';

    export let currentNode: Node;
    export let nodeIndex: number;
    export let optionIndex: number;

    let connectionModal;

    export function openModal() {
        connectionModal.classList.add("is-active");
    }

    function closeModal() {
        connectionModal.classList.remove("is-active");
    }

    function setGoal(id: number): void {
        $nodes[nodeIndex].options[optionIndex].goal = id;
        closeModal();
    }

</script>

<div bind:this="{connectionModal}" class="modal">
    <div class="modal-background"></div>
    <div class="modal-card">
        <header class="modal-card-head">
            <p class="modal-card-title">Knoten zum Verbinden auswählen</p>
            <button on:click="{closeModal}" class="delete" aria-label="close"></button>
        </header>
        <section class="modal-card-body">
            {#if $nodes.length <= 1}
                <p>Keine Knoten zum Verknüpfen vorhanden.</p>
            {:else}
                {#each $nodes as node, i }
                    {#if node.id != currentNode.id}
                        <button on:click="{() => setGoal(node.id)}" class="button is-small is-fullwidth">
                            <div class="icon-text" style="margin-bottom: 10px;">
                                {#if node.type == "normal"}
                                    <span class="icon has-text-info">
                                        <i class="fa-solid fa-location-pin"></i>
                                    </span>
                                {:else if node.type == "start"}
                                    <span class="icon has-text-success">
                                        <i class="fa-solid fa-flag-checkered"></i>
                                    </span>
                                {:else if node.type == "end"}
                                    <span class="icon has-text-danger">
                                        <i class="fa-solid fa-ban"></i>
                                    </span>
                                {/if}
                                <span>{node.title}</span>
                            </div>
                        </button>
                    {/if}
                {/each}
            {/if}
        </section>
        <footer class="modal-card-foot">
            <button on:click="{closeModal}" class="button">Abbrechen</button>
        </footer>
    </div>
</div>

<style>

</style>