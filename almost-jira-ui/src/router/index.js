import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
import UsersView from "../views/UsersView.vue";
import ProjectsView from "@/views/ProjectsView";
import LoginView from "@/views/LoginView.vue";
import store from "../store";

const routes = [
  {
    path: "/",
    name: "home",
    component: HomeView,
  },
  {
    path: "/login",
    name: "login",
    component: LoginView,
  },
  {
    path: "/users",
    name: "users",
    component: UsersView,
    meta: { requiresAuth: true },
  },
  {
    path: "/projects",
    name: "projects",
    component: ProjectsView,
    meta: { requiresAuth: true },
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

router.beforeEach((to) => {
  if (to.meta.requiresAuth && !store.state.loggedIn) {
    return {
      path: "/login",
      query: { redirect: to.fullPath },
    };
  }
});

// function checkAuthorization() {
//   fetch("http://localhost:8080/users/login")
//     .then((response) => response.json())
//     .then((data) => {
//       return data;
//     })
//     .catch((error) => {
//       console.log(error);
//     });
// }

export default router;
