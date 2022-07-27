<script lang="ts">

    import { onMount } from "svelte";
    import type { Node, Option } from "../data/identifierStore";
    import { nodes, nodeResults } from "../data/identifierStore";

    import * as identifierAPI from "../../api/identifier";
    import * as objectAPI from "../../api/objects";

    import FinishedNode from "./FinishedNode.svelte";

    let identifierId: number;
    let identifierName: string;

    export let closeFunction;
    export let objectId;

    let dropdown;
    let dropdownActive: boolean = false;
    let dropdownContent: identifierAPI.Identifier[] = [];
    
    type FinishedNode = {
        node: Node,
        selectedOptionIndex: number
    };

    let nodes: Node[];
    let finishedNodes: FinishedNode[] = [];
    let activeNode: Node;
    let activeNodeSelectedOptionIndex: number;

    function changeDropdown() {
        if (dropdownActive) {
            closeDropdown();
        } else {
            openDropdown();
        }
    }

    async function openDropdown() {
        dropdownContent = Array(0);
        const allIdentifiers: identifierAPI.Identifier[] = await identifierAPI.getAllIdentifiers();
        for (let i = 0; i < allIdentifiers.length; i++) {
            dropdownContent.push(allIdentifiers[i]);
        }
        dropdownActive = true;
        dropdown.classList.add("is-active");
    }

    function closeDropdown() {
        dropdownActive = false;
        dropdown.classList.remove("is-active");
    }

    async function openIdentifier(id: number) {
        const json: string = await identifierAPI.getJSON(id);
        identifierId = id;
        nodes = JSON.parse(json);
        $nodeResults = Array(0);
        activeNode = getStartNode();
        closeDropdown();
    }

    function goBackOneNode() {

        let finishedNodeArray: FinishedNode[] = finishedNodes;
        finishedNodes = Array(0);

        try {
            const previousNode: Node = finishedNodeArray.pop().node;
            $nodeResults.pop();
            activeNode = previousNode;
        } catch (err) {

        }

        finishedNodes = finishedNodeArray;

    }

    function finishNode(optionIndex: number) {

        activeNodeSelectedOptionIndex = optionIndex;

        const newFinishedNode: FinishedNode = {
            "node": activeNode,
            "selectedOptionIndex": activeNodeSelectedOptionIndex 
        };

        let finishedArray = finishedNodes;
        finishedNodes = Array(0);
        finishedArray.push(newFinishedNode);
        $nodeResults.push(activeNodeSelectedOptionIndex);
        finishedNodes = finishedArray;

        console.log(finishedNodes.length);

        if (activeNode.type != "end") {
            if (activeNode.oneGoal) {
                const newActiveNode = findNode(activeNode.goal);
                activeNode = newActiveNode;
            } else {
                const newActiveNode = findNode(activeNode.options[activeNodeSelectedOptionIndex].goal);
                activeNode = newActiveNode;
            }
        } else {
            activeNode = undefined;
            activeNodeSelectedOptionIndex = undefined;
        }

    }

    function findNode(id: number): Node {
        for (let i = 0; i < nodes.length; i++) {
            if (nodes[i].id == id) {
                return nodes[i];
            }
        }
    }

    function getStartNode(): Node {
        const nodeArray: Node[] = nodes;
        for (let i = 0; i < nodeArray.length; i++) {
            if (nodeArray[i].type == "start") {
                return nodeArray[i];
            }
        }
    }

    function saveProgress(): void {
        let results: number[] = $nodeResults;
        console.log("Results: " + results.length);
        identifierAPI.setObjectIdentifierData(objectId, identifierId, results);
    }

    async function loadProgress() {
        //TODO: Implement better loading
        const object = await objectAPI.getObjectById(objectId);
        const identifier: identifierAPI.Identifier = await identifierAPI.getIdentifierById(object.identifierId);
        if (identifier.id != undefined) {
            identifierName = identifier.identifierName;
            await openIdentifier(identifier.id);
            const savedProgress: number[] = (await identifierAPI.getIdentifierResultsFromObject(objectId)).result;
            savedProgress.forEach(result => {
                finishNode(result)
            });
        }
    }

    onMount(async () => {
        await loadProgress();
    });

</script>

<div class="box" id="use">
    <div id="content">
        <nav class="level">
            <div class="level-left">
                <div class="level-item">
                    <p class="subtitle is-5">
                        Fragebogen: {identifierName}
                    </p>
                </div>
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
                                        <a on:click="{() => openIdentifier(content.id)}" href="#" class="dropdown-item">{content.filename}</a>
                                    {/each}
                                {/if}
                                
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="level-right">
                <div class="level-item">
                    <button on:click="{goBackOneNode}" class="button">
                        <i class="fa-solid fa-left-long"></i>
                    </button>
                </div>
                <div class="level-item">
                    <button on:click="{saveProgress}" class="button is-success">
                        <i class="fa-solid fa-floppy-disk"></i>
                    </button>
                </div>
                <div class="level-item">
                    <button on:click="{closeFunction}" class="delete is-large" />
                </div>
            </div>
        </nav>
        {#if activeNode != undefined}
            <div id="node" style="margin-bottom: 5px;">
                <div class="card">
                    <div class="card-header">
                        <p class="card-header-title">
                            {activeNode.title}
                        </p>
                    </div>
                    <div class="card-content">
                        <div class="content">
                            {activeNode.description}
                        </div>
                    </div>
                    <footer class="card-footer">
                        {#each activeNode.options as option, i }
                            <a on:click="{() => finishNode(i)}" href="#" class="is-info card-footer-item">{option.name}</a>
                        {/each}
                    </footer>
                </div>
            </div>
        {:else}
            <div class="box" />
        {/if}
        <div id="finishedNodes" class="box">
            <h6 class="title is-6">Fertige Knoten</h6>
            {#each finishedNodes as node, i }
                {#if node.node.type == "start"}
                    <FinishedNode classType="is-info"    title="{node.node.title}" optionName="{node.node.options[node.selectedOptionIndex].name}" />
                {:else if node.node.type == "normal"}
                    <FinishedNode classType="is-success" title="{node.node.title}" optionName="{node.node.options[node.selectedOptionIndex].name}" />
                {:else if node.node.type == "end"}
                    <FinishedNode classType="is-danger"  title="{node.node.title}" optionName="{node.node.options[node.selectedOptionIndex].name}" />
                {/if}
            {/each}
        </div>
    </div>
</div>

<style>

    #use {
        margin: 10px 10px 10px 10px;
        z-index: 400;
        background-color: white;
        width: 33%;
    }

    .level {
        margin-bottom: 10px;
    }

    #finishedNodes {
        margin-top: 10px;
    }

</style>