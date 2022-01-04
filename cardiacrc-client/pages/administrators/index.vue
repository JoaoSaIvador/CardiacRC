<template>
  <div class="h-100">
    <UserTable
      v-if="administrators"
      :items="administrators"
      :fields="fields"
      sortBy="username"
      group="administrators"
      @delete="deleteAdministrator"
      @export="exportAdministrators"
    />
    <LoadingPage v-else />
  </div>
</template>

<script>
export default {
  middleware: "admin",
  data() {
    return {
      fields: [
        { key: "username", sortable: true },
        { key: "name", sortable: true },
        { key: "email", sortable: true },
        { key: "actions", sortable: false },
      ],
      administrators: null,
    };
  },
  created() {
    this.$axios.$get("/api/administrators").then((administrators) => {
      this.administrators = administrators.filter(
        (a) => a.username !== this.$auth.user.sub
      );
    });
  },
  methods: {
    deleteAdministrator(username) {
      this.$axios
        .$delete(`/api/administrators/${username}`)
        .then((response) => {
          this.$toast.success(response).goAway(3000);
          window.location.reload();
        })
        .catch((error) => {
          this.$toast.error(error.response.data).goAway(3000);
        });
    },
    exportAdministrators() {
      this.$axios.$get("/api/administrators/export");
    },
  },
};
</script>
