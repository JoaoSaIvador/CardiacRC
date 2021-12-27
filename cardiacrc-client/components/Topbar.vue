<template>
  <b-navbar toggleable="lg" type="light" variant="light" class="customNavbar">
    <b-navbar-brand @click="goToDashboard"
      ><span class="longBrand">Cardiac Rehabilitation Centre</span>
      <span class="shortBrand">CardiacRC</span></b-navbar-brand
    >
    <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

    <b-collapse id="nav-collapse" is-nav>
      <b-navbar-nav class="ml-auto">
        <b-nav-item @click="goToDashboard">Home</b-nav-item>
        <b-nav-item @click.prevent="signOut">Logout</b-nav-item>
      </b-navbar-nav>
    </b-collapse>
  </b-navbar>
</template>

<script>
import * as auxiliary from "../utils/auxiliary.js";

export default {
  methods: {
    goToDashboard() {
      auxiliary.goToDashboard(this.$auth.user, this.$router);
    },
    signOut() {
      this.$auth.logout();
      this.$router.push("/");
    },
  },
};
</script>

<style scoped>
.customNavbar {
  padding-right: 10%;
  padding-left: 10%;
  box-shadow: rgba(0, 0, 0, 0.02) 0px 14px 28px,
    rgba(0, 0, 0, 0.02) 0px 10px 10px;
}

@media screen and (max-width: 411px) {
  .shortBrand {
    display: block;
  }

  .longBrand {
    display: none;
  }
}

@media screen and (min-width: 411px) {
  .shortBrand {
    display: none;
  }

  .longBrand {
    display: block;
  }
}
</style>
