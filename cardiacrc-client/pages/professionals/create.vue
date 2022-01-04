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
  head() {
    return {
      title: "Professional Details",
    };
  },
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
