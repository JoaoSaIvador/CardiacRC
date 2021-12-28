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
  middleware: "professional",
  data() {
    return {
      prescription: {
        name: null,
        frequency: null,
        description: null,
      },
    };
  },
  methods: {
    createPrescription(prescription) {
      this.$axios
        .$post("/api/prescriptions", prescription)
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
