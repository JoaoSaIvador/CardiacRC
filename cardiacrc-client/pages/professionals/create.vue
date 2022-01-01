<template>
  <SetUserDetails
    :user="professional"
    @submit="createProfessional"
    to="professional"
    mode="create"
  />
</template>

<script>
export default {
  middleware: "admin",
  data() {
    return {
      professional: {
        username: null,
        name: null,
        email: null,
        password: null,
        licenseNumber: null,
        type: null,
      },
    };
  },
  methods: {
    createProfessional(user) {
      this.$axios
        .$post("/api/professionals", user)
        .then(() => {
          this.$router.back();
        })
        .catch((error) => {
          //this.errorMsg = error.response.data;
          //Notification
        });
    },
  },
};
</script>
