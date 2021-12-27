<template>
  <section class="vh-100">
    <b-container class="h-100 d-flex align-items-center">
      <b-container
        class="
          page-content
          d-flex
          flex-column
          align-items-center
          justify-content-center
          bg-light
        "
      >
        <h2 class="main-title">Sign in</h2>
        <b-form @submit.prevent="onSubmit">
          <div class="main-input">
            <b-form-group
              id="username-label"
              label="Username:"
              label-for="username"
            >
              <b-input
                id="username"
                v-model.trim="username"
                placeholder="Enter your username"
              ></b-input>
            </b-form-group>
          </div>

          <div class="main-input">
            <b-form-group
              id="password-label"
              label="Password:"
              label-for="username"
            >
              <b-input
                id="password"
                v-model.trim="password"
                type="password"
                placeholder="Enter your password"
              ></b-input>
            </b-form-group>
          </div>

          <div class="d-flex flex-row justify-content-center">
            <b-button class="main-button" variant="dark" type="submit">
              Login
            </b-button>
          </div>
        </b-form>
      </b-container>
    </b-container>
  </section>
</template>
<script>
import * as auxiliary from "../../utils/auxiliary.js";

export default {
  layout: "login",
  auth: false,
  data() {
    return {
      username: null,
      password: null,
    };
  },
  methods: {
    onSubmit() {
      let promise = this.$auth.loginWith("local", {
        data: {
          username: this.username,
          password: this.password,
        },
      });
      promise.then(() => {
        this.$toast.success("You are logged in!").goAway(3000);
        auxiliary.goToDashboard(this.$auth.user, this.$router);
      });
      promise.catch(() => {
        this.$toast
          .error("Sorry, you cant login. Ensure your credentials are correct")
          .goAway(3000);
      });
    },
    onReset() {
      this.username = null;
      this.password = null;
    },
  },
};
</script>
