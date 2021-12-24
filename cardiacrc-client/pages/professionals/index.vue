<template>
  <UserTable
    :items="professionals"
    :fields="fields"
    sortBy="username"
    group="professionals"
    @delete="deleteProfessional"
  />
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
        { key: "actions", sortable: false },
      ],
      professionals: [],
    };
  },
  created() {
    this.$axios.$get("/api/professionals").then((professionals) => {
      this.professionals = professionals;
    });
  },
  methods: {
    deleteProfessional(username) {
      this.$axios.$delete(`/api/professionals/${username}`).then(() => {
        window.location.reload();
      });
    },
  },
};
</script>
