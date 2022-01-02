<template>
  <SetUserDetails
    :user="patient"
    @submit="createPatient"
    to="patient"
    mode="create"
  />
</template>

<script>
import * as auxiliary from "../../utils/auxiliary.js";

export default {
  middleware: "professional",
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
        .then((response) => {
          this.$toast.success(response).goAway(3000);
          this.$router.back();
        })
        .catch((error) => {
          this.$toast.error(error.response.data).goAway(3000);
        });
    },
  },
};
</script>
