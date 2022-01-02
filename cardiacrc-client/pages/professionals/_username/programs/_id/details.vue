<template>
  <div class="h-100">
    <b-container class="h-100 d-flex align-items-center" v-if="program">
      <b-container
        class="
          page-content-xl
          d-flex
          flex-column
          align-items-center
          justify-content-center
          bg-light
        "
      >
        <h1 class="main-title text-center">Program Details</h1>
        <form class="w-100 px-5">
          <div class="d-flex flex-row justify-content-around">
            <div>
              <div class="main-input">
                <b-form-group label="Duration:" label-for="duration">
                  <b-input
                    id="duration"
                    :value="program.duration"
                    readonly
                  ></b-input>
                </b-form-group>
              </div>
              <div class="main-input">
                <b-form-group label="Start Date:" label-for="startDate">
                  <b-input
                    id="startDate"
                    :value="program.startDate"
                    readonly
                  ></b-input>
                </b-form-group>
              </div>
              <div class="main-input">
                <b-form-group
                  label="Professional Username:"
                  label-for="professionalUsername"
                >
                  <b-input
                    id="professionalUsername"
                    :value="program.professionalUsername"
                    readonly
                  ></b-input>
                </b-form-group>
              </div>
              <div class="main-input">
                <b-form-group
                  label="Patient Username:"
                  label-for="patientUsername"
                >
                  <b-input
                    id="patientUsername"
                    :value="program.patientUsername"
                    readonly
                  ></b-input>
                </b-form-group>
              </div>
            </div>

            <div class="w-50">
              <p>Prescriptions:</p>
              <b-table
                v-if="program.prescriptions.length > 0"
                striped
                hover
                bordered
                head-variant="dark"
                :items="program.prescriptions"
                :fields="fields"
                :current-page="currentPage"
                :per-page="perPage"
                :filter="filter"
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
                      :to="`prescriptions/${row.item.id}/details`"
                    >
                      <fa :icon="['fas', 'clipboard-list']" />
                    </b-button>
                    <b-button
                      variant="primary"
                      class="
                        table-button
                        d-flex
                        align-items-center
                        justify-content-center
                      "
                      :to="`prescriptions/${row.item.id}/update`"
                    >
                      <fa :icon="['fas', 'pen']" />
                    </b-button>
                  </div>
                </template>
              </b-table>
              <b-pagination
                v-if="program.prescriptions.length > 0"
                v-model="currentPage"
                :total-rows="program.prescriptions.length"
                :per-page="perPage"
                class="w-100 d-flex flex-row justify-content-center"
              />
              <p v-else>No prescriptions.</p>
            </div>
          </div>

          <div class="d-flex flex-row justify-content-center">
            <b-button
              class="main-button"
              variant="outline-dark"
              @click="() => this.$router.back()"
            >
              Back
            </b-button>
            <b-button class="main-button" variant="dark" :to="`update`">
              Update
            </b-button>
            <b-button
              class="main-button"
              variant="dark"
              :to="`prescriptions/create`"
            >
              Add Prescription
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
  middleware: "professionalSelf",
  data() {
    return {
      program: null,
      fields: [
        { key: "id", sortable: true },
        { key: "name", sortable: true },
        { key: "frequency", sortable: true },
        { key: "typeName", sortable: true },
        { key: "actions", sortable: false },
      ],
      sortBy: "id",
      currentPage: 1,
      perPage: 5,
      sortDesc: false,
      filter: null,
    };
  },
  computed: {
    id() {
      return this.$route.params.id;
    },
  },
  created() {
    this.$axios
      .$get(`/api/programs/${this.id}`)
      .then((program) => (this.program = program || {}));
  },
};
</script>
