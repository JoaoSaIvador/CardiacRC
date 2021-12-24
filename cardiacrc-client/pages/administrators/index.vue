<template>
  <UserTable
    :items="administrators"
    :fields="fields"
    sortBy="username"
    group="administrators"
    @delete="deleteAdministrator"
  />
</template>

<script>
import UserTable from "../../components/UserTable.vue";

export default {
  middleware: "admin",
  components: {
    UserTable,
  },
  data() {
    return {
      fields: [
        { key: "username", sortable: true },
        { key: "name", sortable: true },
        { key: "email", sortable: true },
        { key: "actions", sortable: false },
      ],
      administrators: [],
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
      this.$axios.$delete(`/api/administrators/${username}`).then(() => {
        window.location.reload();
      });
    },
  },
};
</script>
