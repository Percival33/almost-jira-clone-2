import { createStore } from "vuex";

export default createStore({
  state: {
    loggedIn: false,
    userId: "",
  },
  mutations: {
    setLoggedIn(state, value) {
      state.loggedIn = value;
    },
    setUserId(state, userId) {
      state.userId = userId;
    },
  },
});
