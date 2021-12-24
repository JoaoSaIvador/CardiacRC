<template>
  <UpdateUserDetails
    :user="patient"
    @submit="createPatient"
    to="patient"
    mode="create"
  />
</template>

<script>
import * as auxiliary from "../../utils/auxiliary.js";

export default {
  data() {
    return {
      patient: {
        username: null,
        name: null,
        email: null,
        password: null,
        healthNumber: null,
      },
    };
  },
  methods: {
    createPatient(user) {
      console.log(user.username);
      console.log(user.name);
      console.log(user.email);
      console.log(user.password);
      console.log(user.healthNumber);

      this.$axios
        .$post("/api/patients", user)
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
