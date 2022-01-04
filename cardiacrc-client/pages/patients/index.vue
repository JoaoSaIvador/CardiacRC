<template>
  <div class="h-100">
    <UserTable
      v-if="patients && associatedPatients"
      :items="patients"
      :fields="fields"
      sortBy="username"
      group="patients"
      @delete="deletePatient"
      @export="exportPatients"
      :associatedPatients="associatedPatients"
    />
    <LoadingPage v-else />
  </div>
</template>

<script>
export default {
  middleware: "adminOrProfessional",
  head() {
    return {
      title: "Manage Patients",
    };
  },
  data() {
    return {
      patients: null,
      associatedPatients: null,
    };
  },
  computed: {
    isPatient() {
      if (this.$auth.user ?? false) {
        return this.$auth.user.groups.includes("Patient");
      }
      return false;
    },

    isProfessional() {
      if (this.$auth.user ?? false) {
        return this.$auth.user.groups.includes("Professional");
      }
      return false;
    },

    isAdministrator() {
      if (this.$auth.user ?? false) {
        return this.$auth.user.groups.includes("Administrator");
      }
      return false;
    },

    fields() {
      if (this.isAdministrator) {
        return [
          { key: "username", sortable: true },
          { key: "name", sortable: true },
          { key: "email", sortable: true },
          { key: "healthNumber", sortable: true },
          { key: "deleted", sortable: true },
          { key: "actions", sortable: false },
        ];
      } else {
        return [
          { key: "username", sortable: true },
          { key: "name", sortable: true },
          { key: "email", sortable: true },
          { key: "healthNumber", sortable: true },
          { key: "actions", sortable: false },
        ];
      }
    },
  },
  created() {
    this.$axios.$get("/api/patients").then((patients) => {
      this.patients = patients;
    });

    if (this.isProfessional) {
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
    exportPatients() {
      this.$axios
        .$get("/api/patients/export", { responseType: "arraybuffer" })
        .then((file) => {
          const url = window.URL.createObjectURL(
            new Blob([file], {
              type: "application/vnd.ms-excel",
            })
          );
          const link = document.createElement("a");
          link.href = url;
          link.setAttribute("download", "patients.xlsx");
          document.body.appendChild(link);
          link.click();
        });
    },
  },
};
</script>
