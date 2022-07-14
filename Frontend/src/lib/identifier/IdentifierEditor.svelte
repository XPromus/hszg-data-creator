<script lang="ts">
    
    import { onMount } from 'svelte';
    import type { Node } from './identifierStore';
    import { nodes } from './identifierStore';

    import * as identifierAPI from '../api/identifier';

    import IdentifierNode from './IdentifierNode.svelte';
    import IdentifierNodeEditor from './IdentifierNodeEditor.svelte';

    export let closeFunction;

    let showEditor: boolean = false;

    let currentIdentifierId: number;
    let currentNode: Node;
    let currentNodeIndex: number;
    let identifierName: string;

    let numberOfStartNodes: number;
    let numberOfEndNodes: number;
    let numberOfNormalNodes: number;

    let dropdown;
    let dropdownActive: boolean = false;
    let dropdownContent: identifierAPI.Identifier[] = [];
    let dropdownContentId: number[] = [];

    function changeDropdown() {
        if (dropdownActive) {
            closeDropdown();
        } else {
            openDropdown();
        }
    }

    async function openDropdown() {
        dropdownContent = Array(0);
        dropdownContentId = Array(0);
        dropdownContent = await identifierAPI.getAllIdentifiers();
        dropdownActive = true;
        dropdown.classList.add("is-active");
    }

    function closeDropdown() {
        dropdownActive = false;
        dropdown.classList.remove("is-active");
    }

    function dropdownClickedOnLink(identifier: identifierAPI.Identifier) {
        closeDropdown();
        loadExistingIdentifier(identifier);
    }

    async function saveData(): Promise<void> {

        const oldIdentifier = await identifierAPI.getIdentifierByName(identifierName);
        //@ts-ignore
        if (oldIdentifier.status == 404) {
            const nodeArray: Node[] = $nodes;
            const data = JSON.stringify(nodeArray);
            const responseData = await identifierAPI.uploadJSON(identifierName, data);
            currentIdentifierId = responseData.id;
        } else {
            const newName: string = oldIdentifier.identifierName + "(alt)";
            const oldJSON = await identifierAPI.getJSON(oldIdentifier.id);
            await identifierAPI.editIdentifier(oldIdentifier.id, newName);
            await identifierAPI.uploadJSON(newName, oldJSON);
        }
        
    }

    async function deleteData() {

        if (currentIdentifierId != undefined) {
            identifierAPI.deleteIdentifier(currentIdentifierId);
        }

        $nodes = Array(0);
        identifierName = undefined;

    }

    function addNodeToStore(): void {

        let id = 0;
        if ($nodes.length > 0) {
            id = $nodes[$nodes.length - 1].id + 1;
        }

        let nodeTitle = "New Node " + id;
        let element: Node = {
            id: id,
            title: nodeTitle,
            description: undefined,
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

    function clearNodes() {
        $nodes = Array(0);
        identifierName = undefined;
        updateNodeCount();
    }

    async function loadExistingIdentifier(identifier: identifierAPI.Identifier) {
        clearNodes();
        const identifierData: string = await identifierAPI.getJSON(identifier.id);
        const parsedNodes: Node[] = JSON.parse(identifierData);
        identifierName = identifier.identifierName;
        $nodes = parsedNodes;
        updateNodeCount();
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
            <div class="box">
                <div class="level">
                    <div class="level-left">
                        {#if $nodes.length == 0}
                            <div class="level-item">
                                <span class="tag is-danger">Keine Knoten</span>
                            </div>
                        {/if}
                        {#key numberOfStartNodes}
                            {#if numberOfStartNodes == 0}
                                <div class="level-item">
                                    <span class="tag is-danger">Kein Startknoten</span>
                                </div>
                            {:else if numberOfStartNodes > 1}
                                <div class="level-item">
                                    <span class="tag is-danger">Mehr als ein Startknoten</span>
                                </div>
                            {/if}
                        {/key}
                        {#key numberOfEndNodes}
                            {#if numberOfEndNodes == 0}
                                <div class="level-item">
                                    <span class="tag is-warning">Keine Endknoten</span>
                                </div>
                            {/if}
                        {/key}
                    </div>
                    <div class="level-right">
                        <div class="level-item">
                            <div bind:this="{dropdown}" class="dropdown">
                                <div class="dropdown-trigger">
                                    <button on:click="{changeDropdown}" class="button" aria-haspopup="true" aria-controls="dropdown-menu">
                                        <span>Auswahl</span>
                                        <span class="icon is-small">
                                            {#if dropdownActive}
                                                <i class="fas fa-angle-down" aria-hidden="true"></i>
                                            {:else}
                                                <i class="fas fa-angle-up" aria-hidden="true"></i>
                                            {/if}
                                        </span>
                                    </button>
                                </div>
                                <div class="dropdown-menu" id="dropdown-menu" role="menu">
                                    <div class="dropdown-content">
                                        {#if dropdownContent.length == 0}
                                            <div class="dropdown-item">
                                                <span>Noch keine Einträge vorhanden</span>
                                            </div>
                                        {:else}
                                            {#each dropdownContent as content, i}
                                                <a on:click="{() => dropdownClickedOnLink(content)}" href="#" class="dropdown-item">{content.identifierName}</a>
                                            {/each}
                                        {/if}
                                        
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="level-item">
                            <button on:click="{saveData}" class="button is-success" title="Fragebogen Speichern">
                                <i class="fa-solid fa-floppy-disk"></i>
                            </button>
                        </div>
                        <div class="level-item">
                            <button on:click="{clearNodes}" class="button is-warning" title="Clear Editor">
                                <i class="fa-solid fa-arrow-rotate-left"></i>
                            </button>
                        </div>
                        <div class="level-item">
                            <button on:click="{deleteData}" class="button is-danger" title="Fragebogen löschen">
                                <i class="fa-solid fa-trash-can"></i>
                            </button>
                        </div>
                        <div class="level-item">
                            <button on:click="{closeFunction}" class="delete is-large" />
                        </div>
                    </div>
                </div>
            </div>
            <div class="box">
                <input bind:value="{identifierName}" class="input" type="text" placeholder="Name">
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