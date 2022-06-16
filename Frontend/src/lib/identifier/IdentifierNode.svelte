<script lang="ts">
    
    import { onMount } from 'svelte';
    import type { Node, Option } from './identifierStore';
    import { nodes } from './identifierStore';

    export let node: Node;
    export let openEditor;
    export let deleteNode;

    let linkedNode;

    function getGoalNode(): Node {
        for (let i = 0; i < $nodes.length; i++) {
            if (node.goal == $nodes[i].id) {
                return $nodes[i];
            }
        }
    }

    function checkOptionConnections(): boolean {
        let result = true;
        node.options.forEach(function(value) {
            if (value.goal == undefined) {
                result = false;
            }
        });
        return result;
    }

    onMount(async () => {
        if (node.goal != undefined) {
            linkedNode = getGoalNode();
        }
    })

</script>

<div class="card">
    <div class="card-content">
        <div class="content">
            <p>{node.title}</p>
            <p>
                {#if !node.oneGoal}
                    {#if linkedNode != undefined}
                        <span class="tag is-success">Link vorhanden</span>
                    {:else}
                        <span class="tag is-warning">Kein Link vorhanden</span>
                    {/if}
                {:else}
                    {#if checkOptionConnections()}
                        <span class="tag is-success">Link vorhanden</span>
                    {:else}
                        <span class="tag is-warning">Manche Optionen haben noch keinen Link</span>
                    {/if}
                {/if}
                
            </p>
            <div class="columns">
                <div class="column is-one-fifth">
                    <button on:click="{openEditor}" class="button is-info">
                        <i class="fa-solid fa-pen-to-square"></i>
                    </button>
                    <button on:click="{deleteNode}" class="button is-danger">
                        Löschen
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>

<style>

</style>