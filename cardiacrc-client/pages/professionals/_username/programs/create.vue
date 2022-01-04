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
  head() {
    return {
      title: "Program Details",
    };
  },
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
        .then((response) => {
          this.$toast.success(response).goAway(3000);
          this.$router.back();
        })
        .catch((error) => {
          this.$toast.error(error.response.data).goAway(3000);
        });
    },
  },
};
</script>
