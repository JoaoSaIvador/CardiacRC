<template>
  <div class="h-100">
    <SetUserDetails
      v-if="administrator"
      :user="administrator"
      @submit="updateAdministrator"
      to="administrator"
      mode="update"
    />
    <LoadingPage v-else />
  </div>
</template>

<script>
export default {
  middleware: "adminSelf",
  data() {
    return {
      administrator: null,
      username: this.$route.params.username,
      errorMsg: false,
    };
  },
  created() {
    this.$axios
      .$get(`/api/administrators/${this.username}`)
      .then((administrator) => {
        this.administrator = administrator || {};
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
        .$put(`/api/administrators/${this.username}`, user)
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
