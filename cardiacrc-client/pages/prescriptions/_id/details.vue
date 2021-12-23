<template>
  <div>
    <b-container class="page-body">
      <div class="container">
        <h2 class="mb-3 text-center">Prescription Details</h2>
        <form>
          <div class="col-sm-4 offset-sm-4">
            <label for="name">Name:</label>
            <div class="input-group">
              <input
                type="text"
                class="form-control"
                id="name"
                :value="prescription.name"
                readonly
              />
            </div>
          </div>
          <div class="col-sm-4 offset-sm-4">
            <label for="duration">Duration:</label>
            <div class="input-group">
              <input
                type="text"
                class="form-control"
                id="duration"
                :value="prescription.duration"
                readonly
              />
            </div>
          </div>
          <div class="col-sm-4 offset-sm-4">
            <label for="description">Description:</label>
            <div class="input-group">
              <textarea
                class="form-control"
                rows="5"
                id="description"
              ></textarea>
            </div>
          </div>
        </form>
        <b-container class="col-sm-4 offset-sm-4">
          <nuxt-link
            :to="`/patients/${prescription.patientUsername}/details`"
            class="btn btn-primary"
            >Back</nuxt-link
          >
        </b-container>
      </div>
    </b-container>
  </div>
</template>

<script>
export default {
  data() {
    return {
      patient: {},
      prescription: {},
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
  methods: {},
};
</script>

<style scoped>
.page-body {
  padding-bottom: 50px;
}
</style>
