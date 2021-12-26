<template>
  <UserTable
    :items="prescriptions"
    :fields="fields"
    sortBy="id"
    group="prescriptions"
    @delete="deletePrescription"
  />
</template>

<script>
export default {
  middleware: "professional",
  data() {
    return {
      fields: [
        { key: "id", sortable: true },
        { key: "name", sortable: true },
        { key: "duration", sortable: true },
        { key: "actions", sortable: false },
      ],
      prescriptions: [],
    };
  },
  created() {
    this.$axios.$get("/api/prescriptions").then((prescriptions) => {
      this.prescriptions = prescriptions;
    });
  },
  methods: {
    deletePrescription(id) {
      this.$axios.$delete(`/api/prescriptions/${id}`).then(() => {
        window.location.reload();
      });
    },
  },
};
</script>
