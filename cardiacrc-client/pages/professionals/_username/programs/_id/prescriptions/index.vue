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
  middleware: "professionalSelf",
  head() {
    return {
      title: "Manage Prescriptions",
    };
  },
  data() {
    return {
      fields: [
        { key: "id", sortable: true },
        { key: "name", sortable: true },
        { key: "frequency", sortable: true },
        { key: "actions", sortable: false },
      ],
      prescriptions: null,
      username: this.$auth.user.sub,
    };
  },
  created() {
    this.$axios
      .$get(`/api/professionals/${this.username}/prescriptions`)
      .then((prescriptions) => {
        this.prescriptions = prescriptions;
      });
  },
  methods: {
    deletePrescription(id) {
      this.$axios
        .$delete(`/api/prescriptions/${id}`)
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
