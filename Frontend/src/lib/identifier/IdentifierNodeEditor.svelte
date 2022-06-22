<script lang="ts">

    import type { Node, Option } from './identifierStore';
    import { nodes } from './identifierStore';

    import IdentifierConnectionModal from './IdentifierConnectionModal.svelte';
    import IdentifierOptionConnectionModal from './IdentifierOptionConnectionModal.svelte';
    import { onMount } from 'svelte';

    export let nodeIndex: number;
    export let closeEditor;

    let nodeType: string = $nodes[nodeIndex].type;
    let goalType: string = getGoalType();

    let currentSingleLink;
    let currentOptionIndex;

    let connectionModal;
    let optionConnectionModal;

    function openOptionConnectionModal(optionIndex: number) {
        currentOptionIndex = optionIndex;
        optionConnectionModal.openModal();
    }

    function openConnectionModal() {
        connectionModal.openModal();
    }

    function createNewOption() {
        const newArray = $nodes[nodeIndex].options;
        newArray.push({name: "", goal: undefined});
        $nodes[nodeIndex].options = Array(0);
        $nodes[nodeIndex].options = newArray;
    }

    function removeOption(id: number) {
        $nodes[nodeIndex].options.splice(id, 1);
        const newArray = $nodes[nodeIndex].options;
        $nodes[nodeIndex].options = Array(0);
        $nodes[nodeIndex].options = newArray;
    }

    function onTypeChange(event) {
        nodeType = event.currentTarget.value;
        $nodes[nodeIndex].type = event.currentTarget.value;
    }

    function getGoalType(): string {
        return $nodes[nodeIndex].oneGoal.toString();
    }

    function onGoalChange(event) {
        let value = event.currentTarget.value;
        if (value === "true") {
            $nodes[nodeIndex].oneGoal = true;
        } else {
            $nodes[nodeIndex].oneGoal = false;
        }
    }

    function closeNodeEditor() {

        if (!$nodes[nodeIndex].oneGoal) {
            if ($nodes[nodeIndex].goal == undefined) {
                $nodes[nodeIndex].goal = 0;
            }
        } else {
            for (let i = 0; i < $nodes[nodeIndex].options.length; i++) {
                if ($nodes[nodeIndex].options[i].goal == undefined) {
                    $nodes[nodeIndex].options[i].goal = 0;
                }
            }
        }

        if ($nodes[nodeIndex].type == "end") {
            $nodes[nodeIndex].goal = 0;
            for (let i = 0; i < $nodes[nodeIndex].options.length; i++) {
                if ($nodes[nodeIndex].options[i].goal == undefined) {
                    $nodes[nodeIndex].options[i].goal = 0;
                }
            }
        }

        closeEditor();
    
    }

    onMount(async () => {
        if ($nodes[nodeIndex].goal == undefined) {
            
        }
    });

</script>

<div id="editor" class="card">
    <div class="card-header">
        <p class="card-header-title">
            Node
            {#if $nodes[nodeIndex].oneGoal && $nodes[nodeIndex].type !== "end"}
                {#if $nodes[nodeIndex].goal != undefined && $nodes[nodeIndex].oneGoal}
                    <span class="tag is-success" style="margin-left: 10px;">Link ist vorhanden</span>
                {:else}
                    <span class="tag is-warning" style="margin-left: 10px;">Kein Link vorhanden</span>
                {/if}
            {/if}
        </p>
    </div>
    <div class="card-content">
        <div class="content">
            <div class="box">
                <nav class="level">
                    <div class="level-left">
                        <div class="level-item">
                            <p>Titel</p>
                        </div>
                    </div>
                    <div class="level-right" style="width: 75%">
                        <input bind:value="{$nodes[nodeIndex].title}" class="input" type="text">
                    </div>
                </nav>
            </div>
            <div class="box">
                <textarea bind:value="{$nodes[nodeIndex].description}" class="textarea" placeholder="Beschreibung" name="description"></textarea>
            </div>
            <div class="box">
                <div class="columns">
                    <div class="column is-one-quarter">
                        <p>Knotenart</p>
                    </div>
                    <div class="column">
                        <div class="control">
                            <label class="radio">
                                <input checked="{nodeType==="start"}" on:change="{onTypeChange}" type="radio" name="nodeType" value="start">
                                Anfangsknoten
                            </label>
                            <label class="radio">
                                <input checked="{nodeType==="normal"}" on:change="{onTypeChange}" type="radio" name="nodeType" value="normal">
                                Normal
                            </label>
                            <label class="radio">
                                <input checked="{nodeType==="end"}" on:change="{onTypeChange}" type="radio" name="nodeType" value="end">
                                Endknoten
                            </label>
                        </div>
                    </div>
                </div>
            </div>
            <div class="box">
                {#if $nodes[nodeIndex].type !== "end"}
                <div class="columns">
                    <div class="column is-one-quarter">
                        <p>Soll jede Option zum gleichen nächsten Knoten führen?</p>
                    </div>
                    <div class="column">
                        <div class="control">
                            <label class="radio">
                                <input checked="{goalType==="true"}" on:change="{onGoalChange}" type="radio" name="choiceButtonGroup" value="true">
                                Ja
                            </label>
                            <label class="radio">
                                <input checked="{goalType==="false"}" on:change="{onGoalChange}" type="radio" name="choiceButtonGroup" value="false">
                                Nein
                            </label>
                        </div>
                    </div>
                </div>
                {/if}
                <div class="columns">
                    <div class="column is-full">
                        <button on:click="{createNewOption}" class="button is-primary" style="width: 100%;">
                            Option Erstellen
                        </button>
                    </div>
                </div>
                {#each $nodes[nodeIndex].options as option, i }
                    <div class="columns">
                        <div class="column is-half">
                            <input bind:value="{$nodes[nodeIndex].options[i].name}" class="input" type="text" placeholder="Optionsname">
                        </div>
                        <div class="column">
                            <button on:click="{() => removeOption(i)}" class="button is-danger">
                                <i class="fa-solid fa-xmark"></i>
                            </button>
                            {#if !$nodes[nodeIndex].oneGoal}
                                <button on:click="{() => openOptionConnectionModal(i)}" class="button is-info">
                                    <i class="fa-solid fa-link"></i>
                                </button>
                                {#if option.goal != undefined}
                                    <span class="tag is-success">Link ist vorhanden</span>
                                {:else}
                                    <span class="tag is-warning">Kein Link vorhanden</span>
                                {/if}
                            {:else}
                                <button class="button is-info" disabled>
                                    <i class="fa-solid fa-link"></i>
                                </button>
                            {/if}
                            {#if $nodes[nodeIndex].options[i].name === "" || $nodes[nodeIndex].options[i].name == undefined}
                                <span class="tag is-warning">Kein Name eingetragen</span>
                            {/if}
                        </div>
                    </div>
                {/each}
            </div>
            <div class="box">
                <div class="columns">
                    <div class="column is-full" style="text-align: center;">
                        {#if $nodes[nodeIndex].oneGoal && $nodes[nodeIndex].type !== "end"}
                            <button on:click="{openConnectionModal}" class="button is-info">
                                <i class="fa-solid fa-link"></i>
                            </button>
                            
                        {:else}
                            <button class="button is-info" disabled>
                                <i class="fa-solid fa-link"></i>
                            </button>
                        {/if}
                        <button on:click="{closeNodeEditor}" class="button is-danger">
                            Schließen
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<IdentifierConnectionModal bind:this="{connectionModal}" currentNodeId="{$nodes[nodeIndex].id}" />
<IdentifierOptionConnectionModal bind:this="{optionConnectionModal}" currentNode="{$nodes[nodeIndex]}" nodeIndex="{nodeIndex}" optionIndex="{currentOptionIndex}"/>

<style>

    #editor {
        top: 0;
        left: 0;
        position: absolute;
        width: 100%;
    }

</style>