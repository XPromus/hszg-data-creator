<script lang="ts">

    import { onMount } from "svelte";
    import type { Node, Option } from "../identifierStore";
    import { nodes, nodeResults } from "../identifierStore";

    import FinishedNode from "./FinishedNode.svelte";

    const testObject: Node[] = [
        {
            "id": 0,
            "title": "Start",
            "description": "Beschreibung 1",
            "type": "start",
            "oneGoal": false,
            "goal": 0,
            "options": [
            {
                "name": "Ja",
                "goal": 1
            },
            {
                "name": "Nein",
                "goal": 2
            }
            ]
        },
        {
            "id": 1,
            "title": "Ja Node",
            "description": "Beschreibung 2",
            "type": "normal",
            "oneGoal": true,
            "goal": 3,
            "options": [
            {
                "name": "Option 1",
                "goal": 0
            },
            {
                "name": "Option 2",
                "goal": 0
            },
            {
                "name": "Option 3",
                "goal": 0
            }
            ]
        },
        {
            "id": 2,
            "title": "Nein Node",
            "description": "Beschreibung 3",
            "type": "normal",
            "oneGoal": true,
            "goal": 3,
            "options": [
            {
                "name": "Option 1",
                "goal": 0
            },
            {
                "name": "Option 2",
                "goal": 0
            },
            {
                "name": "Option 3",
                "goal": 0
            }
            ]
        },
        {
            "id": 3,
            "title": "Ende",
            "description": "Beschreibung 4",
            "type": "end",
            "oneGoal": true,
            "goal": 0,
            "options": [
            {
                "name": "Option 1",
                "goal": 0
            },
            {
                "name": "Option 2",
                "goal": 0
            }
            ]
        }
    ];

    let dropdownActive: boolean = false;
    
    type FinishedNode = {
        node: Node,
        selectedOptionIndex: number
    };

    let finishedNodes: FinishedNode[] = [];
    let activeNode: Node = testObject[0];
    let activeNodeSelectedOptionIndex: number;

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
        for (let i = 0; i < testObject.length; i++) {
            if (testObject[i].id == id) {
                return testObject[i];
            }
        }
    }

    function getStartNode(): Node {
        const nodeArray: Node[] = testObject;
        for (let i = 0; i < nodeArray.length; i++) {
            if (nodeArray[i].type == "start") {
                return nodeArray[i];
            }
        }
    }

    function saveProgress(): void {
        alert($nodeResults);
    }

    onMount(async () => {
        $nodeResults = Array(0);
        activeNode = getStartNode();
    });

</script>

<div id="use">
    <nav class="level">
        <div class="level-left">
            <div class="level-item">
                <p class="subtitle is-5">
                    Fragebogen
                </p>
            </div>
            <div class="level-item">
                <div class="dropdown">
                    <div class="dropdown-trigger">
                        <button class="button" aria-haspopup="true" aria-controls="dropdown-menu">
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
                            <!--Content-->
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

<style>

    #use {
        margin: 10px 10px 10px 10px;
    }

    .level {
        margin-bottom: 10px;
    }

    #finishedNodes {
        margin-top: 10px;
    }

</style>