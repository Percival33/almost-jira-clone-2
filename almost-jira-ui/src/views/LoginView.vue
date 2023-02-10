<template>
  <div class="loginView">
    <from v-if="!store.state.loggedIn" class="loginstuff">
      <input v-model="login" type="text" placeholder="Login" />
      <input v-model="password" type="password" placeholder="Hasło" />
      <button class="button" @click="loginFunction(login, password)">
        Zaloguj się
      </button>
    </from>
    <div v-if="store.state.loggedIn" class="loginstuff">
      <button class="button" @click="logoutFunction()">Wyloguj się</button>
    </div>
  </div>
</template>

<script>
import store from "../store.js";
const API = "http://localhost:8080";

export default {
  name: "LoginView",
  data() {
    return {
      results: [],
      showUsers: false,
      singleUser: false,
      msg: "",
      title: "",
      type: "",
      store,
    };
  },
  methods: {
    loginFunction(login, password) {
      if (login == null || password == null) {
        this.msg = "Aby się zalogować, podaj wartość dla każdego pola";
      } else {
        fetch(`${API}/users?login=${login}&password=${password}`, {
          method: "Put",
        })
          .then((response) => response.json())
          .then((data) => {
            this.msg = "";
            if (data.message === "success") {
              this.msg = "Zalogowano!";
              this.type = "success";
              this.title = "Sukces!";
              store.commit("setLoggedIn", true);
              store.commit("setUserId", data.data.id);
            } else {
              this.msg = "Logowanie nieudane";
              this.type = "error";
              this.title = "Wystąpił błąd!";
              store.commit("setLoggedIn", false);
              store.commit("setUserId", "");
            }
          })
          .then(() => {
            this.$notify({
              type: this.type,
              title: this.title,
              text: this.msg,
              duration: 1000 * 3,
            });
          })
          .catch((error) => {
            console.log(error);
          });
      }
    },
    logoutFunction() {
      store.commit("setLoggedIn", false);
      store.commit("setUserId", "");
      this.msg = "Zostałeś wylogowany";
      this.$notify({
        type: "info",
        title: "Wylogowano pomyślnie",
        text: this.msg,
        duration: 1000 * 3,
      });
    },
  },
};
</script>

<style>
.button {
  color: yellow;
  background: #6a4aff;
  border-color: yellow;
}
.list {
  list-style-type: none;
  margin: 0;
  padding: 0;
  /* border-bottom: 0; */
}
.alert {
  background-color: #2196f3;
}
</style>
