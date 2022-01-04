<template>
  <div class="h-100">
    <b-container class="h-100 d-flex align-items-center" v-if="prescription">
      <b-container
        class="
          page-content
          d-flex
          flex-column
          align-items-center
          justify-content-center
          bg-light
        "
      >
        <h2 class="main-title text-center">Description Details</h2>
        <form>
          <div class="main-input">
            <b-form-group label="Name:" label-for="name">
              <b-input id="name" :value="prescription.name" readonly></b-input>
            </b-form-group>
          </div>
          <div class="main-input">
            <b-form-group label="Frequency:" label-for="frequency">
              <b-input
                id="frequency"
                :value="prescription.frequency"
                readonly
              ></b-input>
            </b-form-group>
          </div>
          <div class="main-input">
            <b-form-group label="Description:" label-for="description">
              <textarea
                class="main-input"
                rows="5"
                id="description"
                :value="prescription.description"
                readonly
              ></textarea>
            </b-form-group>
          </div>
          <div class="d-flex flex-row justify-content-center">
            <b-button
              class="main-button"
              variant="outline-dark"
              @click="() => this.$router.back()"
            >
              Back
            </b-button>
          </div>
        </form>
      </b-container>
    </b-container>
    <LoadingPage v-else />
  </div>
</template>

<script>
export default {
  middleware: "patientSelf",
  head() {
    return {
      title: "Prescription Details",
    };
  },
  data() {
    return {
      prescription: null,
    };
  },
  computed: {
    id() {
      return this.$route.params.id;
    },
  },
  created() {
    this.$axios
      .$get(`/api/prescriptions/${this.id}`)
      .then((prescription) => (this.prescription = prescription || {}));
  },
};
</script>
