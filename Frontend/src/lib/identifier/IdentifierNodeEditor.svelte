<script lang="ts">

    import type { Node, Option } from './identifierStore';
    import { nodes } from './identifierStore';

    import IdentifierConnectionModal from './IdentifierConnectionModal.svelte';
    import IdentifierOptionConnectionModal from './IdentifierOptionConnectionModal.svelte';

    export let nodeIndex: number;
    export let closeEditor;

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

</script>

<div id="editor" class="card">
    <div class="card-header">
        <p class="card-header-title">
            Node
        </p>
    </div>
    <div class="card-content">
        <div class="content">
            <div class="field is-horizontal">
                <div class="field-label is-normal">
                     <!-- svelte-ignore a11y-label-has-associated-control -->
                     <label class="label">Titel</label>
                </div>
                <div class="field-body">
                    <div class="field">
                        <p class="control">
                            <input bind:value="{$nodes[nodeIndex].title}" class="input" type="text">
                        </p>
                    </div>
                </div>
            </div>
            <div class="columns">
                <div class="column is-one-quarter">
                    <p>Soll jede Option zum gleichen nächsten Knoten führen?</p>
                </div>
                <div class="column">
                    <div class="control">
                        <label class="radio">
                            <input bind:group="{$nodes[nodeIndex].oneGoal}" type="radio" name="choiceButtonGroup" value="{false}">
                            Ja
                        </label>
                        <label class="radio">
                            <input bind:group="{$nodes[nodeIndex].oneGoal}" type="radio" name="choiceButtonGroup" value="{true}">
                            Nein
                        </label>
                    </div>
                </div>
            </div>
            <div>
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
                            {#if $nodes[nodeIndex].oneGoal}
                                <button on:click="{() => openOptionConnectionModal(i)}" class="button is-info">
                                    <i class="fa-solid fa-link"></i>
                                </button>
                            {:else}
                                <button class="button is-info" disabled>
                                    <i class="fa-solid fa-link"></i>
                                </button>
                            {/if}
                            {#if option.goal != undefined}
                                <span class="tag is-success">Link ist vorhanden</span>
                            {:else}
                                <span class="tag is-warning">Kein Link vorhanden</span>
                            {/if}
                        </div>
                    </div>
                {/each}
                <div class="columns">
                    <div class="column is-full" style="text-align: center;">
                        {#if !$nodes[nodeIndex].oneGoal}
                            <button on:click="{openConnectionModal}" class="button is-info">
                                <i class="fa-solid fa-link"></i>
                            </button>
                        {:else}
                            <button class="button is-info" disabled>
                                <i class="fa-solid fa-link"></i>
                            </button>
                        {/if}
                        <button on:click="{closeEditor}" class="button is-danger">
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