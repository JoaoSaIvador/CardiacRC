<template>
  <UpdateUserDetails
    :name="name"
    :email="email"
    @update="updateAdministrator"
    to="administrator"
  />
</template>

<script>
export default {
  data() {
    return {
      administrator: {},
      username: this.$route.params.username,
      password: null,
      name: null,
      email: null,
      errorMsg: false,
    };
  },
  created() {
    this.$axios
      .$get(`/api/administrators/${this.username}`)
      .then((administrator) => {
        this.administrator = administrator || {};
        this.name = administrator.name;
        this.email = administrator.email;
      });
  },
  methods: {
    updateAdministrator(user) {
      if (this.administrator.name == user.name) {
        user.name = null;
      }
      if (this.administrator.email == user.email) {
        user.email = null;
      }

      this.$axios
        .$put(`/api/administrators/${this.username}`, {
          ...(user.password ? { password: user.password } : {}),
          ...(user.name ? { name: user.name } : {}),
          ...(user.email ? { email: user.email } : {}),
          passwordConfirmation: user.passwordConfirmation,
        })
        .then(() => {
          this.$router.push("/administrators/dashboard");
        })
        .catch((error) => {
          this.errorMsg = error.response.data;
        });
    },
  },
};
</script>
