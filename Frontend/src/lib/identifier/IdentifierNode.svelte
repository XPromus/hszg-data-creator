<script lang="ts">
    
    import { onMount } from 'svelte';
    import type { Node, Option } from './data/identifierStore';
    import { nodes } from './data/identifierStore';

    export let node: Node;
    export let openEditor;
    export let deleteNode;

    let linkedNode: Node;
    let optionsNodes: string[] = [];

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

    function getOptionNodes() {
        let newArray: string[] = [];
        optionsNodes = Array(0);
        for (let i = 0; i < node.options.length; i++) {
            newArray.push(getNodeTitle(node.options[i].goal));
        }
        optionsNodes = newArray;        
    }

    function getNodeTitle(id: number): string {
        for (let i = 0; i < $nodes.length; i++) {
            if ($nodes[i].id == id) {
                return $nodes[i].title;
            }
        }
    }

    onMount(async () => {
        getOptionNodes();
        if (node.goal != undefined) {
            linkedNode = getGoalNode();
        }
    });

    function debug() {
        alert(optionsNodes);
    }

</script>

<div class="card">
    <div class="card-content">
        <div class="content">
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
            <p>
                {#if node.type !== "end"}
                    {#if node.oneGoal}
                        {#if linkedNode != undefined}
                            <span class="tag is-success">Link vorhanden</span>
                            <span class="tag is-info">
                                <i class="fa-solid fa-link" style="margin-right: 5px;"></i>
                                {linkedNode.title}
                            </span>
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
                {/if}
            </p>
            {#if node.options.length > 0}
                <table class="table is-bordered is-striped is-narrow" style="width: auto;">
                    <thead>
                        <tr>
                            <th>Nr.</th>
                            <th>Optionsname</th>
                            <th>Zielknoten</th>
                        </tr>
                    </thead>
                    <tbody>
                        {#each node.options as option, i}
                            <tr>
                                <td>{i + 1}</td>
                                <td>{option.name}</td>
                                {#if !node.oneGoal}
                                    <td>{optionsNodes[i]}</td>
                                {:else}
                                    <td>-</td>
                                {/if}
                            </tr>
                        {/each}
                    </tbody>
                </table>
            {/if}
            <p>
                <button on:click="{openEditor}" class="button is-info">
                    <i class="fa-solid fa-pen-to-square"></i>
                </button>
                <button on:click="{deleteNode}" class="button is-danger">
                    Löschen
                </button>
            </p>
        </div>
    </div>
</div>

<style>

    .card {
        width: 25%;
        margin-bottom: 10px;
        margin-left: 10px;
        margin-right: 10px;
    }

</style>