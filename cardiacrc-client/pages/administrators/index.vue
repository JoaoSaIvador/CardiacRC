<template>
  <div class="primary-div">
    <Topbar />
    <ManageUserTable
      :items="administrators"
      :fields="fields"
      group="administrators"
      @delete="deleteAdministrator"
    />
  </div>
</template>

<script>
import ManageUserTable from "../../components/ManageUserTable.vue";

export default {
  components: {
    ManageUserTable,
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
      this.administrators = administrators;
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
