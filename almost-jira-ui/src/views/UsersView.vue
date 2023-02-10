<template>
  <div class="usersView">
    <div class="users">
      <button class="button" @click="toggleUsers">
        {{ showUsers ? "Ukryj użytkowników" : "Pokaż użytkowników" }}
      </button>
      <ul class="list" v-if="showUsers">
        <li class="list" v-for="user in results" :key="user.id">
          <article>
            <header>
              <h3>
                <kbd
                  role="button"
                  @click="copyToClipboard(user.id)"
                  class="secondary"
                >
                  {{ user.id }}
                </kbd>
              </h3>
            </header>
            <p>
              Imię i Nazwisko:
              <strong style="text-transform: capitalize">
                {{ user.firstName }}
              </strong>
              &nbsp;
              <strong style="text-transform: capitalize">
                {{ user.lastName }}
              </strong>
            </p>
            <ul v-if="user" class="list">
              <li
                class="list"
                v-for="project in user.projects"
                :key="project.id"
              >
                <p>Nazwa projektu: project.projectName</p>
              </li>
            </ul>
            <footer>
              <p>Data dodania: {{ user.createdAt }}</p>
              <p>Data ostatniej edycji: {{ user.lastModified }}</p>
            </footer>
          </article>
        </li>
      </ul>
    </div>
    <details>
      <summary>Pokaż szczegóły o użytkowniku</summary>
      <input v-model="Id" type="text" placeholder="Podaj ID użytkownika" />
      <button class="button" @click="getUser(Id)">Pokaż użytkownika</button>
      <article v-if="singleUser">
        <header>
          <h3>
            <kbd
              role="button"
              @click="copyToClipboard(singleUser.id)"
              class="secondary"
            >
              {{ singleUser.id }}
            </kbd>
          </h3>
        </header>
        <p>
          Imię i Nazwisko:
          <strong style="text-transform: capitalize">
            {{ singleUser.firstName }}
          </strong>
          &nbsp;
          <strong style="text-transform: capitalize">
            {{ singleUser.lastName }}
          </strong>
        </p>
        <ul v-if="singleUser" class="list">
          <li
            class="list"
            v-for="project in singleUser.projects"
            :key="project.id"
          >
            <p>Nazwa projektu: project.projectName</p>
          </li>
        </ul>
        <footer>
          <p>Data dodania: {{ singleUser.createdAt }}</p>
          <p>Data ostatniej edycji: {{ singleUser.lastModified }}</p>
        </footer>
      </article>
    </details>
    <details>
      <summary>Usuń użytkownika</summary>
      <input
        v-model="deleteId"
        type="text"
        placeholder="Podaj ID użytkownika do usunięcia"
      />
      <button class="button" @click="deleteUser(deleteId)">
        Usuń użytkownika
      </button>
    </details>
    <details>
      <summary>Utwórz nowego użytkownika</summary>
      <input v-model="firstName" type="text" placeholder="Imie użytkownika" />
      <input
        v-model="lastName"
        type="text"
        placeholder="Nazwisko użytkownika"
      />
      <input v-model="login" type="text" placeholder="Login uzytkownika" />
      <input v-model="password" type="text" placeholder="Hasło użytkownika" />
      <button
        class="button"
        @click="addUser(firstName, lastName, login, password)"
      >
        Dodaj Użytkownika
      </button>
    </details>
    <details>
      <summary>Zmień dane użytkownika</summary>
      <input
        v-model="changeId"
        type="text"
        placeholder="Id użytkownika do zmiany"
      />
      <input v-model="newFirstName" type="text" placeholder="Nowe imie" />
      <input v-model="newLastName" type="text" placeholder="Nowe nazwisko" />
      <input v-model="newLogin" type="text" placeholder="Nowy login" />
      <input v-model="newPassword" type="text" placeholder="Nowe hasło" />
      <button
        class="button"
        @click="
          changeUser(changeId, newFirstName, newLastName, newLogin, newPassword)
        "
      >
        Edytuj Użytkownika
      </button>
    </details>
  </div>
</template>

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
</style>

<script>
const API = "http://localhost:8080";

export default {
  name: "UsersView",
  data() {
    return {
      results: [],
      showUsers: false,
      singleUser: false,
      msg: "",
      title: "",
      type: "",
    };
  },
  methods: {
    toggleUsers() {
      this.showUsers = !this.showUsers;
      if (this.showUsers) {
        this.getUsers();
      }
    },
    getUsers() {
      fetch(`${API}/users`)
        .then((response) => response.json())
        .then((data) => {
          this.results = data.data;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    getUser(Id) {
      fetch(`${API}/users/${Id}`)
        .then((response) => response.json())
        .then((data) => {
          if (Id === "" || data.data === null) {
            this.type = "error";
            this.title = "Błąd!";
            this.text = "Nie ma użytkownika o takim id";
          }
          this.singleUser = data.data;
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
    },
    deleteUser(deleteId) {
      fetch(`${API}/users/${deleteId}`, { method: "DELETE" })
        .then((response) => response.json())
        .then((data) => {
          if (data.status === 404) {
            this.type = "error";
            this.title = "Błąd!";
            this.text = "Nie można usunąć użytkownika o nieistniejącym id";
          } else {
            this.type = "success";
            this.title = "Sukces!";
            this.text = "Użytkownik został usunięty";
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
    },
    copyToClipboard(text) {
      navigator.clipboard.writeText(text);
    },
    addUser(firstName, lastName, login, password) {
      if (
        firstName == null ||
        lastName == null ||
        login == null ||
        password == null
      ) {
        this.msg = "Aby dodać użytkownika podaj wartość dla każdego pola";
      } else {
        fetch(`${API}/users`, {
          method: "Post",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            firstName: firstName,
            lastName: lastName,
            login: login,
            password: password,
          }),
        })
          .then((response) => response.json())
          .then((data) => {
            if (data.message === "success") {
              this.type = "success";
              this.title = "Sukces!";
              this.text = "Użytkownik został dodany";
            } else {
              this.type = "error";
              this.title = "Wystąpił błąd!";
              this.text = "Nie udało się dodać użytkownika";
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
    changeUser(changeId, newFirstName, newLastName, newLogin, newPassword) {
      fetch(`${API}/users/${changeId}`, {
        method: "Put",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          firstName: newFirstName,
          lastName: newLastName,
          login: newLogin,
          password: newPassword,
        }),
      })
        .then((response) => response.json())
        .then((data) => {
          if (data.message === "success") {
            this.type = "success";
            this.title = "Sukces!";
            this.text = "Użytkownik został edytowany";
          } else {
            this.type = "error";
            this.title = "Wystąpił błąd!";
            this.text =
              "Nie udało się edytować użytkownika. Sprawdź poprawność Id";
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
    },
  },
};
</script>
