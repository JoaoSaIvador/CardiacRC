<template>
  <SetPrescriptionDetails
    :prescription="prescription"
    @submit="createPrescription"
    group="prescription"
    mode="create"
  />
</template>

<script>
export default {
  middleware: "professionalSelf",
  head() {
    return {
      title: "Prescription Details",
    };
  },
  data() {
    return {
      prescription: {
        name: null,
        frequency: null,
        frequencyText: "Per day",
        description: null,
        programId: this.$route.params.id,
      },
    };
  },
  methods: {
    createPrescription(prescription) {
      prescription.frequency =
        prescription.frequency + " " + prescription.frequencyText;

      this.$axios
        .$post("/api/prescriptions", prescription)
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
