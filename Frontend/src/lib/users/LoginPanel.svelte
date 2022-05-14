<script lang="ts">

    import * as UserAPI from '../api/users';
    import { slide } from 'svelte/transition';
    import { minPasswordLength, comparePassword } from '../api/passwords';

    type createAccountStateType = {
        none: number,
        success: number,
        failure: number
    }

    const createAccountStateEnum: createAccountStateType = {
        none: 0,
        success: 1,
        failure: 2
    };

    let createAccountState: number = createAccountStateEnum.none;
    let createAccountButton;
    let accountDropdown;
    let emailInput: string;
    let passwordInput: string;

    async function login(): Promise<void> {

        if (!checkEmailPasswordInput()) {
            return;
        }

        const response: Response = await UserAPI.getUserByEmail(emailInput);
        if (response.status == 404) {
            alert("User mit der Email " + emailInput + " existiert nicht");
            return;
        }

        if (response.status == 200) {
            
            const data: UserAPI.User = await response.json();
            const compare: boolean = await comparePassword(passwordInput, data.password);

            if (!compare) {
                alert("Das Passwort stimmt nicht!");
                return;
            }

            alert("Login");

        }

    }

    async function createNewAccount(): Promise<void> {

        if (!checkEmailPasswordInput()) {
            return;
        }

        const response: Response = await UserAPI.createUser(emailInput, passwordInput);
        if (response.status == 200) {
            createAccountState = createAccountStateEnum.success;
        } else if (response.status == 226) {
            createAccountState = createAccountStateEnum.failure;
            createAccountButton.classList.add("is-danger");
        }
    
    }

    function changeDropdownState(): void {
        
        const classList = accountDropdown.classList;

        if (classList.contains("is-active")) {
            emailInput = "";
            passwordInput = "";
            classList.remove("is-active");
            resetDropdown();
        } else {
            classList.add("is-active");
        }

    }

    function resetDropdown(): void {
        createAccountState = createAccountStateEnum.none;
        createAccountButton.classList.remove("is-danger");
        createAccountButton.classList.add("is-info");
    }

    function checkEmailPasswordInput(): boolean {

        if (emailInput == null || emailInput == "" || emailInput == undefined) {
            alert("Es muss eine Email angegeben werden");
            return false;
        }

        if (passwordInput == null || passwordInput == "" || passwordInput == undefined) {
            alert("Es muss ein Passwort eingetragen werden");
            return false;
        }

        if (passwordInput.length < minPasswordLength) {
            alert("Das Passwort muss mindestens " + minPasswordLength + " Zeichen enthalten");
            return false;
        }

        return true;

    }

</script>

<div class="hero">
    <div class="hero-body">
        <div bind:this="{accountDropdown}" id="accountButton" class="dropdown is-right">
            <div class="dropdown-trigger">
                <button on:click="{changeDropdownState}" class="button is-primary is-light" aria-haspopup="true" aria-controls="account-dropdown">
                    <span class="icon has-text-primary">
                        <i class="fa-solid fa-user" aria-hidden="true"></i>
                    </span>
                </button>
            </div>
            <div class="dropdown-menu" id="account-dropdown" role="menu">
                <div class="dropdown-content">
                    <div class="dropdown-item">
                        <input on:input="{resetDropdown}" bind:value="{emailInput}" class="input" type="text" placeholder="Email">
                    </div>
                    <div class="dropdown-item">
                        <input bind:value="{passwordInput}" class="input" type="text" placeholder="Passwort">
                    </div>
                    <div class="dropdown-item">
                        <button on:click="{login}" class="button is-primary" style="width: 100%;">Login</button>
                        <button bind:this="{createAccountButton}" on:click="{createNewAccount}" class="button is-info" style="width: 100%; margin-top: 5px">
                            {#if createAccountState == createAccountStateEnum.none}
                                <span>Account erstellen</span>
                            {:else if createAccountState == createAccountStateEnum.success}
                                <i transition:slide="{{duration: 250}}" class="fa-solid fa-check"></i>
                            {:else if createAccountState == createAccountStateEnum.failure}
                                <span transition:slide="{{duration: 250}}" class="icon-text">
                                    <span class="icon">
                                        <i class="fa-solid fa-xmark"></i>
                                    </span>
                                    <span>Nutzer existiert schon</span>
                                </span>
                            {/if}
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<style>

    .hero {
        top: 0px;
        position: fixed;
        width: 100%;
        z-index: 400;
    }

    #accountButton {
        top: 0px;
        right: 0px;
        margin-top: 10px;
        margin-right: 10px;
        position: absolute;
    }
    
    #account-dropdown {
        width: 20rem;
    }

</style>