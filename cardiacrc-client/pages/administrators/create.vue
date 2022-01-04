<template>
  <SetUserDetails
    :user="administrator"
    @submit="createAdministrator"
    to="administrator"
    mode="create"
  />
</template>

<script>
export default {
  middleware: "admin",
  head() {
    return {
      title: "Administrator Details",
    };
  },
  data() {
    return {
      administrator: {
        username: null,
        name: null,
        email: null,
        password: null,
      },
    };
  },
  methods: {
    createAdministrator(user) {
      this.$axios
        .$post("/api/administrators", user)
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
