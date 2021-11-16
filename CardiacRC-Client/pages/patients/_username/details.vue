<template>
    <div>
        <Topbar/>
        <b-container class="page-body">
            <div >
                <h2 class="mb-3">Dashboard</h2>
                <form class="needs-validation">
                    <div class="col-sm-5">
                        <label for="name">Name:</label>
                        <div class="input-group">
                            <input type="text" class="form-control" id="name" :value="patient.name" readonly>
                        </div>
                    </div>
                    <div class="col-sm-5">
                        <label for="username">Username:</label>
                        <div class="input-group">
                            <input type="text" class="form-control" id="username" :value="patient.username" readonly>
                        </div>
                    </div>
                    <div class="col-sm-5">
                        <label for="email">Email:</label>
                        <div class="input-group">
                            <input type="email" class="form-control" id="email" :value="patient.email" readonly>
                        </div>
                    </div>
                    <div class="col-sm-5">
                        <label for="healthNumber">Health Number:</label>
                        <div class="input-group">
                            <input type="text" class="form-control" id="healthNumber" :value="patient.healthNumber" readonly>
                        </div>
                    </div>
                    <div class="col-sm-5">
                        <label for="associatedProfessional">Associated Professional:</label>
                        <div class="input-group">
                            <input type="text" class="form-control" id="associatedProfessional" :value="patient.associatedProfessional" readonly>
                        </div>
                    </div>
                </form>

                <b-container>
                  <nuxt-link :to="`/patients/${patient.username}/update`" class="btn btn-primary" >Update</nuxt-link>
                </b-container>
            </div>

            <div class="col-md-12 order-md-1">
                <h2 class="mb-3"> Active Prescriptions</h2>
                <b-table v-if="activePrescriptions.length > 0" striped over outlined :items="activePrescriptions" :fields="prescriptionFields" class="mt-3">

                </b-table>
                <p v-else>No active prescriptions.</p>
            </div>

            <div class="col-md-12 order-md-1">
                <h2 class="mb-3"> Inactive Prescriptions</h2>
                <b-table v-if="inactivePrescriptions.length > 0" striped over outlined :items="inactivePrescriptions" :fields="prescriptionFields" class="mt-3">

                </b-table>
                <p v-else>No active prescriptions.</p>
            </div>

            <b-container>
                <nuxt-link to="/patients" class="btn btn-primary" >Back</nuxt-link>
            </b-container>
        </b-container>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                patient: {},
                professional: {},
                prescriptionFields: ['id', 'name', 'duration', 'actions' ],
            }
        },
        computed: {
            username() {
                return this.$route.params.username
            } ,
            activePrescriptions() {
                return this.patient.activePrescriptionDTOs || []
            },
            inactivePrescriptions() {
                return this.patient.inactivePrescriptionDTOs || []
            }
        },
        created() {
            this.$axios.$get(`/api/patients/${this.username}`)
                .then(patient => this.patient = patient || {})
        },
        methods: {

        }
    }
</script>

<style scoped>
  .page-body {
    padding-bottom: 50px;
  }
</style>
