<template>
  <div>
    <b-container class="page-body">
      <div class="d-md-flex h-md-100">
        <div class="col-md-6 h-md-100">
          <div class="d-md-flex h-100">
            <div>
              <h2>Details</h2>
              <form>
                <div class="patient-info">
                  <label for="name">Name:</label>
                  <div class="input-group">
                    <input
                      type="text"
                      class="form-control"
                      id="name"
                      :value="patient.name"
                      readonly
                    />
                  </div>
                </div>
                <div class="patient-info">
                  <label for="username">Username:</label>
                  <div class="input-group">
                    <input
                      type="text"
                      class="form-control"
                      id="username"
                      :value="patient.username"
                      readonly
                    />
                  </div>
                </div>
                <div class="patient-info">
                  <label for="email">Email:</label>
                  <div class="input-group">
                    <input
                      type="email"
                      class="form-control"
                      id="email"
                      :value="patient.email"
                      readonly
                    />
                  </div>
                </div>
                <div class="patient-info">
                  <label for="healthNumber">Health Number:</label>
                  <div class="input-group">
                    <input
                      type="text"
                      class="form-control"
                      id="healthNumber"
                      :value="patient.healthNumber"
                      readonly
                    />
                  </div>
                </div>
                <div class="patient-info">
                  <label for="associatedProfessional"
                    >Associated Professional:</label
                  >
                  <div class="input-group">
                    <input
                      type="text"
                      class="form-control"
                      id="associatedProfessional"
                      :value="patient.associatedProfessional"
                      readonly
                    />
                  </div>
                </div>
              </form>
              <div>
                <nuxt-link
                  :to="`/patients/${patient.username}/update`"
                  class="btn btn-primary"
                  >Update</nuxt-link
                >
              </div>
            </div>
          </div>
        </div>

        <div class="col-md-6 h-md-100">
          <div class="d-md-flex h-md-100">
            <h2 class="mb-3">Biomedical Data</h2>
          </div>
          <div class="d-md-flex h-md-100">
            <div class="table-wrapper-scroll-y my-custom-scrollbar">
              <b-table
                v-if="patientData.length > 0"
                striped
                over
                outlined
                :items="patientData"
                :fields="patientDataFields"
                class="mt-3"
                style="width: 525px"
              />
              <p v-else>No patient data found.</p>
            </div>
          </div>
          <div>
            <nuxt-link
              :to="`/patients/${patient.username}/createData`"
              class="btn btn-primary"
              >Insert</nuxt-link
            >
          </div>
        </div>
      </div>
      <div class="col-md-12 order-md-1">
        <h2 class="mb-3">Active Prescriptions</h2>
        <b-table
          v-if="activePrescriptions.length > 0"
          striped
          over
          outlined
          :items="activePrescriptions"
          :fields="prescriptionFields"
          class="mt-3"
        >
          <template v-slot:cell(actions)="row">
            <nuxt-link
              class="btn btn-primary btn-xs"
              :to="`/prescriptions/${row.item.id}/details`"
            >
              <fa :icon="['fas', 'clipboard-list']" />
            </nuxt-link>
          </template>
        </b-table>
        <p v-else>No active prescriptions.</p>
      </div>

      <div class="col-md-12 order-md-1">
        <h2 class="mb-3">Inactive Prescriptions</h2>
        <b-table
          v-if="inactivePrescriptions.length > 0"
          striped
          over
          outlined
          :items="inactivePrescriptions"
          :fields="prescriptionFields"
          class="mt-3"
        >
          <template v-slot:cell(actions)="row">
            <nuxt-link
              class="btn btn-primary btn-xs"
              :to="`/prescriptions/${row.item.id}/details`"
            >
              <fa :icon="['fas', 'clipboard-list']" />
            </nuxt-link>
          </template>
        </b-table>
        <p v-else>No inactive prescriptions.</p>
      </div>
      <b-container>
        <nuxt-link to="/patients" class="btn btn-primary">Back</nuxt-link>
      </b-container>
    </b-container>
  </div>
</template>

<script>
export default {
  middleware: "patientSelf",
  data() {
    return {
      patient: {},
      professional: {},
      prescriptionFields: ["id", "name", "duration", "actions"],
      patientDataFields: ["id", "value", "dataType", "date"],
    };
  },
  computed: {
    username() {
      return this.$route.params.username;
    },
    activePrescriptions() {
      return this.patient.activePrescriptionDTOs || [];
    },
    inactivePrescriptions() {
      return this.patient.inactivePrescriptionDTOs || [];
    },
    patientData() {
      return this.patient.patientData || [];
    },
  },
  created() {
    this.$axios
      .$get(`/api/patients/${this.username}`)
      .then((patient) => (this.patient = patient || {}));
  },
  methods: {},
};
</script>

<style scoped>
.page-body {
  padding-bottom: 50px;
}

.patient-info {
  width: 300px;
  margin-bottom: 10px;
}

.my-custom-scrollbar {
  position: relative;
  max-height: 380px;
  overflow: auto;
  margin-bottom: 10px;
}
.table-wrapper-scroll-y {
  display: block;
}
</style>
