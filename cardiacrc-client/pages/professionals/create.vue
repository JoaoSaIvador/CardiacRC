<template>
  <UpdateUserDetails
    :user="professional"
    @submit="createProfessional"
    to="professional"
    mode="create"
  />
</template>

<script>
import * as auxiliary from "../../utils/auxiliary.js";

export default {
  data() {
    return {
      professional: {
        username: null,
        name: null,
        email: null,
        password: null,
        licenseNumber: null,
      },
    };
  },
  methods: {
    createProfessional(user) {
      user.type = 1;

      this.$axios
        .$post("/api/professionals", user)
        .then(() => {
          auxiliary.goToDashboard(this.$auth.user, this.$router);
        })
        .catch((error) => {
          //this.errorMsg = error.response.data;
          //Notification
        });
    },
  },
};
</script>

<style scoped></style>
