<template>
  <div class="h-100">
    <SetProgramDetails
      v-if="patients"
      :program="program"
      @submit="createProgram"
      group="prescription"
      mode="create"
      :professionalPatients="patients"
      :prescriptions="prescriptions"
    />
    <LoadingPage v-else />
  </div>
</template>

<script>
export default {
  middleware: "professional",
  data() {
    return {
      username: this.$auth.user.sub,
      patients: null,
      prescriptions: null,
      program: {
        duration: null,
        patientUsername: null,
        professionalUsername: this.$auth.user.sub,
        prescriptionIds: [],
      },
    };
  },
  created() {
    this.$axios
      .$get(`/api/professionals/${this.username}/patients`)
      .then((patients) => {
        this.patients = patients;
      });

    this.$axios
      .$get(`/api/professionals/${this.username}/prescriptions`)
      .then((prescriptions) => {
        this.prescriptions = prescriptions;
      });
  },
  methods: {
    createProgram(program) {
      this.$axios
        .$post("/api/programs", program)
        .then(() => {
          this.$router.push("/professionals/dashboard");
        })
        .catch((error) => {
          //this.errorMsg = error.response.data;
          //Notification
        });
    },
  },
};
</script>
