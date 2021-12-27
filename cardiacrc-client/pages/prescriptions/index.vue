<template>
  <div class="h-100">
    <UserTable
      v-if="prescriptions"
      :items="prescriptions"
      :fields="fields"
      sortBy="id"
      group="prescriptions"
      @delete="deletePrescription"
    />
    <LoadingPage v-else />
  </div>
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
      prescriptions: null,
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
