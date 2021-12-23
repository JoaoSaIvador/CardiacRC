<template>
  <UpdateUserDetails
    :username="username"
    :name="name"
    :email="email"
    @update="updatePatient"
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
    updatePatient() {
      if (this.administrator.username == this.username) {
        this.username = null;
      }
      if (this.administrator.name == this.name) {
        this.name = null;
      }
      if (this.administrator.email == this.email) {
        this.email = null;
      }

      this.$axios
        .$put(`/api/administrators/${this.username}`, {
          ...(this.username ? { username: this.username } : {}),
          ...(this.password ? { password: this.password } : {}),
          ...(this.name ? { name: this.name } : {}),
          ...(this.email ? { email: this.email } : {}),
          passwordConfirmation: this.passwordConfirmation,
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
