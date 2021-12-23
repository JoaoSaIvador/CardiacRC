<template>
  <UpdateUserDetails
    :name="name"
    :email="email"
    :licenseNumber="licenseNumber"
    @update="updateProfessional"
    to="professional"
  />
</template>

<script>
export default {
  data() {
    return {
      professional: {},
      username: this.$route.params.username,
      password: null,
      name: null,
      email: null,
      licenseNumber: null,
      errorMsg: false,
    };
  },
  created() {
    this.$axios
      .$get(`/api/professionals/${this.username}`)
      .then((professional) => {
        this.professional = professional || {};
        this.name = professional.name;
        this.email = professional.email;
        this.licenseNumber = String(professional.licenseNumber);
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
        .$put(`/api/professionals/${this.username}`, {
          ...(user.password ? { password: user.password } : {}),
          ...(user.name ? { name: user.name } : {}),
          ...(user.email ? { email: user.email } : {}),
          ...(user.licenseNumber ? { licenseNumber: user.licenseNumber } : {}),
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

<style scoped></style>
