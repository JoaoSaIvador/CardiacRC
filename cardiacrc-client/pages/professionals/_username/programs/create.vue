<template>
  <div class="h-100">
    <SetProgramDetails
      v-if="patients"
      :program="program"
      @submit="createProgram"
      group="prescription"
      mode="create"
      :professionalPatients="patients"
    />
    <LoadingPage v-else />
  </div>
</template>

<script>
export default {
  middleware: "professionalSelf",
  data() {
    return {
      username: this.$auth.user.sub,
      patients: null,
      program: {
        duration: null,
        patientUsername: null,
        professionalUsername: this.$auth.user.sub,
      },
    };
  },
  created() {
    this.$axios
      .$get(`/api/professionals/${this.username}/patients`)
      .then((patients) => {
        this.patients = patients;
      });
  },
  methods: {
    createProgram(program) {
      this.$axios
        .$post("/api/programs", program)
        .then(() => {
          this.$router.back();
        })
        .catch((error) => {
          //this.errorMsg = error.response.data;
          //Notification
        });
    },
  },
};
</script>
