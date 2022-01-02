<template>
  <SetObservationDetails
    v-if="dataTypes"
    :observation="observation"
    :dataTypes="dataTypes"
    @submit="createObservation"
    mode="create"
  />
  <LoadingPage v-else />
</template>

<script>
export default {
  middleware: "patientSelfOrProfessional",
  data() {
    return {
      dataTypes: null,
      observation: {
        value: null,
        dataTypeId: null,
        patientUsername: this.$route.params.username,
      },
    };
  },
  created() {
    this.$axios.$get("/api/dataTypes").then((dataTypes) => {
      this.dataTypes = dataTypes;
    });
  },
  methods: {
    createObservation(observation) {
      this.$axios
        .$post("/api/observations", observation)
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
