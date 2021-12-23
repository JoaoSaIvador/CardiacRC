<template>
  <UpdateUserDetails
    :name="name"
    :email="email"
    :healthNumber="String(healthNumber)"
    @submit="updatePatient"
    to="patient"
    mode="update"
  />
</template>

<script>
import * as auxiliary from "../../../utils/auxiliary.js";

export default {
  data() {
    return {
      patient: {},
      username: this.$route.params.username,
      password: null,
      name: null,
      email: null,
      healthNumber: null,
      errorMsg: false,
    };
  },
  created() {
    this.$axios.$get(`/api/patients/${this.username}`).then((patient) => {
      this.patient = patient || {};
      this.name = patient.name;
      this.email = patient.email;
      this.healthNumber = patient.healthNumber;
    });
  },
  methods: {
    updatePatient(user) {
      if (this.patient.name == user.name) {
        user.name = null;
      }
      if (this.patient.email == user.email) {
        user.email = null;
      }
      if (this.patient.healthNumber == user.healthNumber) {
        user.healthNumber = "0";
      }

      this.$axios
        .$put(`/api/patients/${this.username}`, {
          ...(user.password ? { password: user.password } : {}),
          ...(user.name ? { name: user.name } : {}),
          ...(user.email ? { email: user.email } : {}),
          ...(user.healthNumber ? { healthNumber: user.healthNumber } : {}),
          passwordConfirmation: user.passwordConfirmation,
        })
        .then(() => {
          auxiliary.goToDashboard(this.$auth.user, this.$router);
        })
        .catch((error) => {
          this.errorMsg = error.response.data;
        });
    },
  },
};
</script>
