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
  middleware: "patientSelf",
  data() {
    return {
      dataTypes: null,
      observation: {
        duration: null,
        dataTypeId: null,
        patientUsername: this.$auth.user.sub,
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
        .$post("/api/observation", observation)
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
