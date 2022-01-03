<template>
  <div class="h-100">
    <UserTable
      v-if="professionals"
      :items="professionals"
      :fields="fields"
      sortBy="username"
      group="professionals"
      @delete="deleteProfessional"
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
        { key: "licenseNumber", sortable: true },
        { key: "typeName", sortable: true },
        { key: "deleted", sortable: true },
        { key: "actions", sortable: false },
      ],
      professionals: null,
    };
  },
  created() {
    this.$axios.$get("/api/professionals").then((professionals) => {
      this.professionals = professionals;
    });
  },
  methods: {
    deleteProfessional(username) {
      this.$axios
        .$delete(`/api/professionals/${username}`)
        .then((response) => {
          this.$toast.success(response).goAway(3000);
          window.location.reload();
        })
        .catch((error) => {
          this.$toast.error(error.response.data).goAway(3000);
        });
    },
  },
};
</script>
