<template>
    <div>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.2.0/css/all.css" integrity="sha384-hWVjflwFxL6sNzntih27bfxkr27PmbbK/iSvJ+a4+0owXq79v+lsFkW54bOGbiDQ" crossorigin="anonymous">
        <Topbar/>
        <b-container class="page-body">
          <div class="d-md-flex h-md-100">
              <div class="col-md-6 h-md-100">
                  <div class="d-md-flex h-100">
                      <div>
                        <h2>Details</h2>
                        <form>
                          <div class="professional-info">
                              <label for="name">Name:</label>
                              <div class="input-group">
                                  <input type="text" class="form-control" id="name" :value="professional.name" readonly>
                              </div>
                          </div>
                          <div class="professional-info">
                              <label for="username">Username:</label>
                              <div class="input-group">
                                  <input type="text" class="form-control" id="username" :value="professional.username" readonly>
                              </div>
                          </div>
                          <div class="professional-info">
                              <label for="email">Email:</label>
                              <div class="input-group">
                                  <input type="email" class="form-control" id="email" :value="professional.email" readonly>
                              </div>
                          </div>
                          <div class="professional-info">
                              <label for="licenseNumber">License Number:</label>
                              <div class="input-group">
                                  <input type="text" class="form-control" id="licenseNumber" :value="professional.licenseNumber" readonly>
                              </div>
                          </div>
                        </form>
                        <div>
                          <nuxt-link :to="`/professionals/${professional.username}/update`" class="btn btn-primary" >Update</nuxt-link>
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
                    <b-table v-if="patients.length > 0" striped over outlined :items="patients" :fields="patientFields" class="mt-3" style="width: 525px;"/>
                    <p v-else>No patients.</p>
                  </div>
                </div>
                <div>
                  <nuxt-link :to="`/professionals/${professional.username}/details`" class="btn btn-primary" >Add</nuxt-link>
                </div>
              </div>
          </div>

          <div class="col-md-12 order-md-1">
              <h2 class="mb-3"> Active Prescriptions</h2>
              <b-table v-if="activePrescriptions.length > 0" striped over outlined :items="activePrescriptions" :fields="prescriptionFields" class="mt-3">
                  <template v-slot:cell(actions)="row">
                    <nuxt-link class="btn btn-primary btn-xs" :to="`/prescriptions/${row.item.id}/details`">
                        <i class="fas fa-clipboard-list"></i>
                    </nuxt-link>
                  </template>
              </b-table>
              <p v-else>No active prescriptions.</p>
          </div>

          <div class="col-md-12 order-md-1">
              <h2 class="mb-3"> Inactive Prescriptions</h2>
              <b-table v-if="inactivePrescriptions.length > 0" striped over outlined :items="inactivePrescriptions" :fields="prescriptionFields" class="mt-3">
                <template v-slot:cell(actions)="row">
                    <nuxt-link class="btn btn-primary btn-xs" :to="`/prescriptions/${row.item.id}/details`">
                        <i class="fas fa-clipboard-list"></i>
                    </nuxt-link>
                  </template>
              </b-table>
              <p v-else>No inactive prescriptions.</p>
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
            activePrescriptions() {
                return this.professional.activePrescriptionDTOs || []
            },
            inactivePrescriptions() {
                return this.professional.inactivePrescriptionDTOs || []
            },
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
  .page-body {
    padding-bottom: 50px;
  }

  .professional-info {
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
