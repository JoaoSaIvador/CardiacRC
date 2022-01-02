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
