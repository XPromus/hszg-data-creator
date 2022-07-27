<script lang="ts">
    
    import type { Node, Option } from './data/identifierStore';
    import { nodes } from './data/identifierStore';

    export let currentNodeId;
    let connectionModal;

    export function openModal() {
        connectionModal.classList.add("is-active");
    }

    function closeModal() {
        connectionModal.classList.remove("is-active");
    }

    function setGoal(id: number): void {
        for (let i = 0; i < $nodes.length; i++) {
            if ($nodes[i].id == currentNodeId) {
                $nodes[i].goal = id;
            }
        }

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
                    {#if node.id != currentNodeId}
                        <button on:click="{() => setGoal(node.id)}" class="button is-small is-fullwidth">{node.title}</button>
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