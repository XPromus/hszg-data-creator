<script lang="ts">
    
    import { onMount } from 'svelte';
import { each } from 'svelte/internal';
    import IdentifierNode from './IdentifierNode.svelte';
    import IdentifierNodeEditor from './IdentifierNodeEditor.svelte';
    import type { Node, Option } from './identifierStore';
    import { nodes } from './identifierStore';

    let showEditor: boolean = false;
    let currentNode: Node;
    let currentNodeIndex: number;

    let numberOfStartNodes: number;
    let numberOfEndNodes: number;
    let numberOfNormalNodes: number;

    function addNodeToStore(): void {

        let id = 0;
        if ($nodes.length > 0) {
            id = $nodes[$nodes.length - 1].id + 1;
        }

        let nodeTitle = "New Node " + id;
        let element: Node = {
            id: id,
            title: nodeTitle,
            type: "normal",
            oneGoal: true,
            goal: undefined,
            options: []
        }

        $nodes = [...$nodes, element];

    }

    function deleteNode(node: Node) {

        let nodeArray = $nodes;
        let index;
        
        for (let i = 0; i < nodeArray.length; i++) {
            if (node.id == nodeArray[i].id) {
                index = i;
            } else {
                if (!nodeArray[i].oneGoal) {
                    if (nodeArray[i].goal == node.id) {
                        nodeArray[i].goal = undefined;
                    }
                } else {
                    for (let u = 0; u < nodeArray[i].options.length; u++) {
                        if (nodeArray[i].options[u].goal == node.id) {
                            nodeArray[i].options[u].goal = undefined;
                        }
                    }
                }
            }
        }

        if (index != undefined) {

            $nodes = Array(0);
            nodeArray.splice(index, 1);
            $nodes = nodeArray;

            updateNodeCount();

        }

    }

    function openEditor(node: Node) {
        for (let i = 0; i < $nodes.length; i++) {
            if (node.id == $nodes[i].id) {
                currentNodeIndex = i;
                break;
            }
        }
        currentNode = node;
        showEditor = true;
    }

    function closeEditor() {
        showEditor = false;
        currentNode = undefined;
        updateNodeCount();
    }

    function countNodeTypes(type: string): number {
        let count = 0;
        for (let i = 0; i < $nodes.length; i++) {
            if ($nodes[i].type === type) {
                count++;
            }
        }

        return count;
    }

    function updateNodeCount() {
        numberOfStartNodes = countNodeTypes("start");
        numberOfEndNodes = countNodeTypes("end");
        numberOfNormalNodes = countNodeTypes("normal");
    }

    onMount(async () => {
        updateNodeCount();
    });

    function debug() {
        console.log($nodes);
    }

</script>

<div>
    {#if showEditor}
        <IdentifierNodeEditor nodeIndex="{currentNodeIndex}" closeEditor="{closeEditor}" />
    {:else}
        <div>
            <div class="box" on:click="{debug}">
                {#if $nodes.length == 0}
                    <span class="tag is-danger">Keine Knoten</span>
                {/if}
                {#key numberOfStartNodes}
                    {#if numberOfStartNodes == 0}
                        <span class="tag is-danger">Kein Startknoten</span>
                    {:else if numberOfStartNodes > 1}
                        <span class="tag is-danger">Mehr als ein Startknoten</span>
                    {/if}
                {/key}
                {#key numberOfEndNodes}
                    {#if numberOfEndNodes == 0}
                        <span class="tag is-warning">Keine Endknoten</span>
                    {/if}
                {/key}
            </div>
            <button id="createNodeButton" on:click="{addNodeToStore}" class="button is-primary">
                Knoten hinzufügen
            </button>
            {#key $nodes}
                {#each $nodes as node }
                    <IdentifierNode node="{node}" openEditor="{() => openEditor(node)}" deleteNode="{() => deleteNode(node)}" />
                {/each}
            {/key}
        </div>
    {/if}
</div>

<style>

    .box {
        margin-top: 10px;
        margin-right: 10px;
        margin-left: 10px;
    }

    #createNodeButton {
        width: 25%;
        margin-bottom: 10px;
        margin-right: 10px;
        margin-left: 10px;
    }

</style>