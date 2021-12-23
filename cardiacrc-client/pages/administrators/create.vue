<template>
  <UpdateUserDetails
    :name="name"
    :email="email"
    @submit="createAdministrator"
    to="administrator"
    mode="create"
  />
</template>

<script>
export default {
  data() {
    return {
      name: null,
      email: null,
      errorMsg: false,
    };
  },
  methods: {
    createAdministrator(user) {
      this.$axios
        .$post("/api/administrators", {
          username: user.username,
          password: user.password,
          name: user.name,
          email: user.email,
        })
        .then(() => {
          this.$router.push("/administrators/dashboard");
        })
        .catch((error) => {
          this.errorMsg = error.response.data;
        });
    },
    reset() {
      this.errorMsg = false;
    },
  },
};
</script>

<style scoped></style>
