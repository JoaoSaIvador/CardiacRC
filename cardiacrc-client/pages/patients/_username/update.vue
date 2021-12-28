<template>
  <div class="h-100">
    <SetUserDetails
      v-if="patient"
      :user="patient"
      @submit="updatePatient"
      to="patient"
      mode="update"
    />
    <LoadingPage v-else />
  </div>
</template>

<script>
import * as auxiliary from "../../../utils/auxiliary.js";

export default {
  middleware: "patientUpdate",
  data() {
    return {
      patient: null,
      username: this.$route.params.username,
    };
  },
  created() {
    this.$axios.$get(`/api/patients/${this.username}`).then((patient) => {
      this.patient = patient || {};
      this.patient.healthNumber = String(this.patient.healthNumber);
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
        .$put(`/api/patients/${this.username}`, user)
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
