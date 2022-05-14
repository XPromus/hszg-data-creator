<script lang="ts">
    
    import * as Password from '../api/passwords';
    import * as UserAPI from '../api/users';

    export let openApp;

    let userNameInput: string;
    let passwordInput: string;

    async function login(): Promise<void> {

        let userResponse: Response = await UserAPI.getUserByEmail(userNameInput);
        if (userResponse.status != 200) {
            alert("Benutzer existiert nicht");
            return;
        }

        let userData: UserAPI.User = await userResponse.json();

        if (await Password.comparePassword(passwordInput, userData.password)) {
            userNameInput = "";
            passwordInput = "";
            openApp();
        } else {
            alert("Falsches Passwort");
            return;
        }

    }

</script>

<div id="loginMenu" class="notification is-primary">
    <div class="columns is-mobile">
        <div class="column is-half is-offset-one-quarter">
          <h1 class="title is-1">HSZG Data Creator</h1>
        </div>
    </div>
    <div class="columns is-mobile">
        <div class="column is-half is-offset-one-quarter">
            <div class="field">
                <p class="control has-icons-left">
                    <input bind:value="{userNameInput}" type="text" class="input" placeholder="Benutzername">
                    <span class="icon is-small is-left">
                        <i class="fa-solid fa-user"></i>
                    </span>
                </p>
            </div>
        </div>
    </div>
    <div class="columns is-mobile">
        <div class="column is-half is-offset-one-quarter">
            <div class="field">
                <p class="control has-icons-left">
                    <input bind:value="{passwordInput}" type="password" class="input" placeholder="Passwort">
                    <span class="icon is-small is-left">
                        <i class="fas fa-lock"></i>
                    </span>
                </p>
            </div>
        </div>
    </div>
    <div class="columns is-mobile">
        <div class="column is-half is-offset-one-quarter">
            <button on:click="{login}" class="button is-info" style="width: 100%;">
                Login
            </button>
        </div>
    </div>
</div>

<style>

    #loginMenu {
        margin-top: 150px;
    }

</style>