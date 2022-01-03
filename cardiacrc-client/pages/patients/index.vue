<template>
  <div class="h-100">
    <UserTable
      v-if="patients && associatedPatients"
      :items="patients"
      :fields="fields"
      sortBy="username"
      group="patients"
      @delete="deletePatient"
      :associatedPatients="associatedPatients"
    />
    <LoadingPage v-else />
  </div>
</template>

<script>
export default {
  middleware: "adminOrProfessional",
  data() {
    return {
      fields: [
        { key: "username", sortable: true },
        { key: "name", sortable: true },
        { key: "email", sortable: true },
        { key: "healthNumber", sortable: true },
        { key: "actions", sortable: false },
      ],
      patients: null,
      associatedPatients: null,
    };
  },
  created() {
    this.$axios.$get("/api/patients").then((patients) => {
      this.patients = patients;
    });

    if (this.$auth.user.groups.includes("Professional")) {
      this.$axios
        .$get(`/api/professionals/${this.$auth.user.sub}/patients`)
        .then((associatedPatients) => {
          this.associatedPatients = associatedPatients;
        });
    } else {
      this.associatedPatients = [];
    }
  },
  methods: {
    deletePatient(username) {
      this.$axios
        .$delete(`/api/patients/${username}`)
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
