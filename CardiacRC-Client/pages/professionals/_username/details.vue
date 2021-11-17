<template>
    <div>
        <Topbar/>
        <b-container class="page-body">
          <div class="d-md-flex h-md-100">
              <div class="col-md-6 h-md-100">
                  <div class="d-md-flex h-100">
                      <div>
                        <h2>Details</h2>
                        <form>
                          <div class="col-sm-5">
                              <label for="name">Name:</label>
                              <div class="input-group">
                                  <input type="text" class="form-control" id="name" :value="professional.name" readonly>
                              </div>
                          </div>
                          <div class="col-sm-5">
                              <label for="username">Username:</label>
                              <div class="input-group">
                                  <input type="text" class="form-control" id="username" :value="professional.username" readonly>
                              </div>
                          </div>
                          <div class="col-sm-5">
                              <label for="email">Email:</label>
                              <div class="input-group">
                                  <input type="email" class="form-control" id="email" :value="professional.email" readonly>
                              </div>
                          </div>
                          <div class="col-sm-5">
                              <label for="licenseNumber">License Number:</label>
                              <div class="input-group">
                                  <input type="text" class="form-control" id="licenseNumber" :value="professional.licenseNumber" readonly>
                              </div>
                          </div>
                        </form>
                        <div>
                          <nuxt-link :to="`/patients/${patient.username}/update`" class="btn btn-primary" >Update</nuxt-link>
                        </div>
                      </div>
                  </div>
              </div>

              <div class="col-md-6 h-md-100">
                <div class="d-md-flex h-md-100">
                  <h2 class="mb-3">Patients</h2>
                </div>
                <div class="d-md-flex h-md-100">
                  <div class="table-wrapper-scroll-y my-custom-scrollbar">
                    <b-table v-if="patients.length > 0" striped over outlined :items="patients" :fields="patientFields" class="mt-3"/>
                    <p v-else>No patients.</p>
                  </div>
                </div>
                <div>
                  <nuxt-link :to="`/patients/${patient.username}/createData`" class="btn btn-primary" >Insert</nuxt-link>
                </div>
              </div>
          </div>

            <div class="col-md-12 order-md-1">
                <h2 class="mb-3">Prescriptions</h2>
                <b-table v-if="prescriptions.length > 0" striped over outlined :items="prescriptions" :fields="prescriptionFields" class="mt-3">

                </b-table>
                <p v-else>No prescriptions.</p>
            </div>

            <b-container>
                <nuxt-link to="/professionals" class="btn btn-primary" >Back</nuxt-link>
            </b-container>
        </b-container>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                professional: {},
                prescriptionFields: ['id', 'name', 'duration', 'patientName', 'actions' ],
                patientFields: ['username', 'name', 'email', 'healthNumber']
            }
        },
        computed: {
            username() {
                return this.$route.params.username
            } ,
            prescriptions() {
                return this.professional.prescriptionDTOs || []
            } ,
            patients() {
                return this.professional.patientDTOs || []
            }
        },
        created() {
            this.$axios.$get(`/api/professionals/${this.username}`)
                .then(professional => this.professional = professional || {})
        },
        methods: {

        }
    }
</script>

<style scoped>

</style>
