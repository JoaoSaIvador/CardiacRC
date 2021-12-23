<template>
  <UserTable
    :items="administrators"
    :fields="fields"
    group="administrators"
    @delete="deleteAdministrator"
  />
</template>

<script>
import UserTable from "../../components/UserTable.vue";

export default {
  components: {
    UserTable,
  },
  data() {
    return {
      currentPage: 1,
      perPage: 10,
      sortBy: "username",
      sortDesc: false,
      filter: null,
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

<style scoped></style>
