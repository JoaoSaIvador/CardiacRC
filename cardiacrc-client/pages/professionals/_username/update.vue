<template>
  <SetUserDetails
    v-if="professional"
    :user="professional"
    @submit="updateProfessional"
    to="professional"
    mode="update"
  />
</template>

<script>
import * as auxiliary from "../../../utils/auxiliary.js";

export default {
  data() {
    return {
      professional: null,
      username: this.$route.params.username,
    };
  },
  created() {
    this.$axios
      .$get(`/api/professionals/${this.username}`)
      .then((professional) => {
        this.professional = professional || {};
        this.professional.password = null;
      });
  },
  methods: {
    updateProfessional(user) {
      if (this.professional.name == user.name) {
        user.name = null;
      }
      if (this.professional.email == user.email) {
        user.email = null;
      }
      if (this.professional.licenseNumber == user.licenseNumber) {
        user.licenseNumber = "0";
      }

      this.$axios
        .$put(`/api/professionals/${this.username}`, user)
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
