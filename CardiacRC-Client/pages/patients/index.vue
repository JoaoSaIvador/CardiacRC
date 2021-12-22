<template>
  <div class="primary-div">
    <Topbar />
    <ManageUserTable
      :items="patients"
      :fields="fields"
      group="patients"
      @delete="deletePatient"
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
      fields: [
        { key: "username", sortable: true },
        { key: "name", sortable: true },
        { key: "email", sortable: true },
        { key: "healthNumber", sortable: true },
        { key: "actions", sortable: false },
      ],
      patients: [],
    };
  },
  created() {
    this.$axios.$get("/api/patients").then((patients) => {
      this.patients = patients;
    });
  },
  methods: {
    deletePatient(username) {
      this.$axios.$delete(`/api/patients/${username}`).then(() => {
        window.location.reload();
      });
    },
  },
};
</script>

<style scoped></style>
