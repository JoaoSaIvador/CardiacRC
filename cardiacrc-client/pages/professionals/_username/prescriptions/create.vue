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
        description: null,
        professionalUsername: this.$auth.user.sub,
      },
    };
  },
  methods: {
    createPrescription(prescription) {
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
