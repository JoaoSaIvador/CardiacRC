<template>
  <div class="primary-div">
    <UserTable
      :items="professionals"
      :fields="fields"
      group="professionals"
      @delete="deleteProfessional"
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
        { key: "licenseNumber", sortable: true },
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

<style scoped></style>
