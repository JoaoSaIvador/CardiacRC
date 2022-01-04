<template>
  <div class="h-100">
    <SetPrescriptionDetails
      v-if="prescription"
      :prescription="prescription"
      @submit="updatePrescription"
      group="prescription"
      mode="update"
    />
    <LoadingPage v-else />
  </div>
</template>

<script>
export default {
  middleware: "professional",
  head() {
    return {
      title: "Prescription Details",
    };
  },
  data() {
    return {
      prescription: null,
      id: this.$route.params.id,
    };
  },
  created() {
    this.$axios.$get(`/api/prescriptions/${this.id}`).then((prescription) => {
      this.prescription = prescription || {};
      let frequency = prescription.frequency.split(" ");
      prescription.frequency = frequency[0];
      prescription.frequencyText = frequency[1] + " " + frequency[2];
    });
  },
  methods: {
    updatePrescription(prescription) {
      if (this.prescription.name == prescription.name) {
        prescription.name = null;
      }
      if (this.prescription.frequency == prescription.frequency) {
        prescription.frequency = null;
      }

      prescription.frequency =
        prescription.frequency + " " + prescription.frequencyText;

      this.$axios
        .$put(`/api/prescriptions/${this.id}`, prescription)
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
