<script lang="ts">
    
    import IdentifierNode from './IdentifierNode.svelte';
    import IdentifierNodeEditor from './IdentifierNodeEditor.svelte';
    import type { Node, Option } from './identifierStore';
    import { nodes } from './identifierStore';

    let showEditor: boolean = false;
    let currentNode: Node;
    let currentNodeIndex: number;

    function addNodeToStore(): void {

        let id = 0;
        if ($nodes.length > 0) {
            id = $nodes[$nodes.length - 1].id + 1;
        }

        let nodeTitle = "New Node " + id;
        let element: Node = {
            id: id,
            title: nodeTitle,
            oneGoal: false,
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
                break;
            }
        }

        if (index != undefined) {
            $nodes = Array(0);
            nodeArray.splice(index, 1);
            $nodes = nodeArray;
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
    }

</script>

<div>
    {#if showEditor}
        <IdentifierNodeEditor nodeIndex="{currentNodeIndex}" closeEditor="{closeEditor}" />
    {:else}
        <div>
            <button id="createNodeButton" on:click="{addNodeToStore}" class="button is-primary">
                Add Node
            </button>
            {#each $nodes as node }
                <IdentifierNode node="{node}" openEditor="{() => openEditor(node)}" deleteNode="{() => deleteNode(node)}" />
            {/each}
        </div>
    {/if}
</div>

<style>

    #createNodeButton {
        width: 100%;
        margin-top: 5px;
        margin-bottom: 5px;
        margin-right: 5px;
        margin-left: 5px;
    }

</style>