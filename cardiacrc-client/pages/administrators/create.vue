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
