<template>
  <div class="primary-div">
    <UserTable
      :items="patients"
      :fields="fields"
      group="patients"
      @delete="deletePatient"
    />
  </div>
</template>

<script>
import UserTable from "../../components/UserTable.vue";

export default {
  components: {
    UserTable,
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
