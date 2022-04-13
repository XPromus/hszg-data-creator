<script lang="ts">

    import TopBar from './TopBar.svelte'
    import MainBody from './MainBody.svelte'
    import type Object from './Object.svelte';

    let mainBody;

    function addObject() {
        mainBody.handleAdd();
    }

    function uploadData() {

        const objects: Object[] = mainBody.getObjects();
        let data: string = '[';

        for (let i = 0; i < objects.length; i++) {
            data += objects[i].toJSON();
            if (i < objects.length - 1) {
                data += ','
            }
        }  

        data += ']';
        alert(data);

        //TODO: Upload data

    }

    function initObjectsFromJSON(data: string) {

        const objects = JSON.parse(data);
        for (let i = 0; i < objects.length; i++) {
        const objectData = objects[i];
        addObject();
        let object: Object = mainBody.getObjects()[i];
        object.fromJSONObj(objectData);

        }

    }

</script>

<div>
    <TopBar uploadDataFunction="{uploadData}" addObjectFunction="{addObject}"/>
    <MainBody bind:this="{mainBody}"/>
</div>

<style>
    
</style>