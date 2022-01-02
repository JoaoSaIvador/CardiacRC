<template>
  <div
    class="
      h-auto
      d-flex
      flex-row
      align-items-start
      justify-content-center
      flex-wrap
    "
  >
    <div class="mt-5 mr-5" v-if="patient && observations && programs">
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
        <h2 class="main-title text-center">Account Details</h2>
        <form>
          <div class="main-input">
            <b-form-group id="name" label="Name:" label-for="name">
              <b-input id="name" :value="patient.name" readonly></b-input>
            </b-form-group>
          </div>

          <div class="main-input">
            <b-form-group id="username" label="Username:" label-for="username">
              <b-input
                id="username"
                :value="patient.username"
                readonly
              ></b-input>
            </b-form-group>
          </div>

          <div class="main-input">
            <b-form-group id="email" label="Email:" label-for="email">
              <b-input id="email" :value="patient.email" readonly></b-input>
            </b-form-group>
          </div>

          <div class="main-input">
            <b-form-group
              id="healthNumber"
              label="Health Number:"
              label-for="healthNumber"
            >
              <b-input
                id="healthNumber"
                :value="patient.healthNumber"
                readonly
              ></b-input>
            </b-form-group>
          </div>

          <div class="d-flex flex-row justify-content-center">
            <b-button
              class="main-button"
              variant="dark"
              :to="`/patients/${patient.username}/update`"
            >
              Update
            </b-button>
          </div>
        </form>
      </b-container>
    </div>

    <div class="d-flex flex-column justify-content-around my-5">
      <b-container
        class="d-flex align-items-center"
        v-if="patient && observations && programs"
      >
        <b-container
          class="page-content-xl d-flex flex-column align-items-center bg-light"
        >
          <h1>Manage Observations</h1>
          <div
            class="
              w-100
              d-flex
              flex-row
              justify-content-between
              align-items-center
              mb-3
            "
          >
            <div>
              <b-button variant="dark" :to="`observations/create`">
                Create
              </b-button>
            </div>
            <div v-if="observations.length > 0">
              <b-form-group style="margin: 0">
                <b-input-group size="sm">
                  <b-form-input
                    id="filter-input"
                    v-model="filterObservations"
                    type="search"
                    placeholder="Type to Search"
                  ></b-form-input>

                  <b-input-group-append>
                    <b-button
                      variant="dark"
                      :disabled="!filterObservations"
                      @click="filterObservations = ''"
                      >Clear</b-button
                    >
                  </b-input-group-append>
                </b-input-group>
              </b-form-group>
            </div>
            <b-pagination
              v-if="observations.length > 0"
              v-model="currentPageObservations"
              :total-rows="observations.length"
              :per-page="perPage"
              class="m-0"
            />
          </div>
          <b-table
            v-if="observations.length > 0"
            striped
            hover
            bordered
            head-variant="dark"
            :items="observations"
            :fields="observationFields"
            :current-page="currentPageObservations"
            :per-page="perPage"
            :filter="filterObservations"
            :sort-by.sync="sortBy"
            :sort-desc.sync="sortDesc"
            small
          >
            <template v-slot:cell(actions)="row">
              <div class="d-flex flex-row justify-content-center">
                <b-button
                  variant="danger"
                  class="
                    table-button
                    d-flex
                    align-items-center
                    justify-content-center
                  "
                  @click.prevent="showConfirmation(row.item.id)"
                >
                  <fa :icon="['fas', 'trash']" />
                </b-button>
              </div>
            </template>
          </b-table>
          <p v-else>No observations.</p>
          <DeleteConfirmation
            v-show="isShowConfirmation"
            @closeModal="isShowConfirmation = false"
            @confirm="confirm"
          />
        </b-container>
      </b-container>

      <b-container
        class="d-flex align-items-center mt-5"
        v-if="patient && observations && programs"
      >
        <b-container
          class="page-content-xl d-flex flex-column align-items-center bg-light"
        >
          <h1>Manage Programs</h1>
          <div
            class="
              w-100
              d-flex
              flex-row
              justify-content-between
              align-items-center
              mb-3
            "
          >
            <div v-if="programs.length > 0">
              <b-form-group style="margin: 0">
                <b-input-group size="sm">
                  <b-form-input
                    id="filter-input"
                    v-model="filterPrograms"
                    type="search"
                    placeholder="Type to Search"
                  ></b-form-input>

                  <b-input-group-append>
                    <b-button
                      variant="dark"
                      :disabled="!filterPrograms"
                      @click="filterPrograms = ''"
                      >Clear</b-button
                    >
                  </b-input-group-append>
                </b-input-group>
              </b-form-group>
            </div>
            <b-pagination
              v-if="programs.length > 0"
              v-model="currentPagePrograms"
              :total-rows="programs.length"
              :per-page="perPage"
              class="m-0"
            />
          </div>
          <b-table
            v-if="programs.length > 0"
            striped
            hover
            bordered
            head-variant="dark"
            :items="programs"
            :fields="programFields"
            :current-page="currentPagePrograms"
            :per-page="perPage"
            :filter="filterPrograms"
            :sort-by.sync="sortBy"
            :sort-desc.sync="sortDesc"
            small
          >
            <template v-slot:cell(actions)="row">
              <div class="d-flex flex-row justify-content-center">
                <b-button
                  variant="success"
                  class="
                    table-button
                    d-flex
                    align-items-center
                    justify-content-center
                  "
                  :to="`programs/${row.item.id}/details`"
                >
                  <fa :icon="['fas', 'clipboard-list']" />
                </b-button>
              </div>
            </template>
          </b-table>
          <p v-else>No programs.</p>
        </b-container>
      </b-container>
    </div>
  </div>
</template>

<script>
export default {
  middleware: "patientSelfOrProfessional",
  data() {
    return {
      patient: null,
      observations: null,
      programs: null,
      observationFields: [
        { key: "id", sortable: true },
        { key: "value", sortable: true },
        { key: "dataTypeName", sortable: true },
        { key: "qualitativeName", sortable: true },
        { key: "date", sortable: true },
        { key: "actions", sortable: false },
      ],
      programFields: [
        { key: "id", sortable: true },
        { key: "duration", sortable: true },
        { key: "startDate", sortable: true },
        { key: "professionalUsername", sortable: true },
        { key: "patientUsername", sortable: true },
        { key: "actions", sortable: false },
      ],
      sortBy: "id",
      currentPageObservations: 1,
      currentPagePrograms: 1,
      perPage: 10,
      sortDesc: false,
      filterObservations: null,
      filterPrograms: null,
      isShowConfirmation: false,
      affectedLine: null,
    };
  },
  computed: {
    username() {
      return this.$route.params.username;
    },
  },
  created() {
    this.$axios.$get(`/api/patients/${this.username}`).then((patient) => {
      this.patient = patient || {};
      this.observations = patient.observations || {};
      this.programs = patient.programs || {};
    });
  },
  methods: {
    showConfirmation(affectedLine) {
      this.isShowConfirmation = true;
      this.affectedLine = affectedLine;
    },
    confirm(confirmation) {
      if (confirmation) {
        this.$axios
          .$delete(`/api/observations/${this.affectedLine}`)
          .then(() => {
            window.location.reload();
          });
      }
    },
  },
};
</script>
