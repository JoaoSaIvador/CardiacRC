<template>
  <div class="h-100">
    <b-container class="h-100 d-flex align-items-center" v-if="patients">
      <b-container
        class="page-content-xl d-flex flex-column align-items-center bg-light"
      >
        <h1>Manage Associated Patients</h1>
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
            <b-button variant="outline-dark" @click="() => this.$router.back()">
              Back
            </b-button>
          </div>
          <div v-if="patients.length > 0">
            <b-form-group style="margin: 0">
              <b-input-group size="sm">
                <b-form-input
                  id="filter-input"
                  v-model="filter"
                  type="search"
                  placeholder="Type to Search"
                ></b-form-input>

                <b-input-group-append>
                  <b-button
                    variant="dark"
                    :disabled="!filter"
                    @click="filter = ''"
                    >Clear</b-button
                  >
                </b-input-group-append>
              </b-input-group>
            </b-form-group>
          </div>
          <b-pagination
            v-if="patients.length > 0"
            v-model="currentPage"
            :total-rows="patients.length"
            :per-page="perPage"
            class="m-0"
          />
        </div>
        <b-table
          v-if="patients.length > 0"
          striped
          hover
          bordered
          head-variant="dark"
          :items="patients"
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
                variant="dark"
                class="
                  table-button
                  d-flex
                  align-items-center
                  justify-content-center
                  button-extra
                "
                @click.prevent="disassociatePatient(row.item.username)"
              >
                <fa :icon="['fas', 'minus']" />
                <span class="button-text">&nbsp;Disassociate</span>
              </b-button>
              <b-button
                variant="primary"
                class="
                  table-button
                  d-flex
                  align-items-center
                  justify-content-center
                "
                :to="`patients/${row.item.username}/update`"
              >
                <fa :icon="['fas', 'pen']" />
              </b-button>
              <b-button
                variant="danger"
                class="
                  table-button
                  d-flex
                  align-items-center
                  justify-content-center
                "
                @click.prevent="showConfirmation(row.item.username)"
              >
                <fa :icon="['fas', 'trash']" />
              </b-button>
            </div>
          </template>
        </b-table>
        <p v-else>No associated patients.</p>
        <DeleteConfirmation
          v-show="isShowConfirmation"
          @closeModal="isShowConfirmation = false"
          @confirm="confirm"
        />
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
      fields: [
        { key: "username", sortable: true },
        { key: "name", sortable: true },
        { key: "email", sortable: true },
        { key: "healthNumber", sortable: true },
        { key: "actions", sortable: false },
      ],
      patients: null,
      username: this.$auth.user.sub,
      sortBy: "username",
      currentPage: 1,
      perPage: 10,
      sortDesc: false,
      filter: null,
      isShowConfirmation: false,
      affectedLine: null,
    };
  },
  created() {
    this.$axios
      .$get(`/api/professionals/${this.username}/patients`)
      .then((patients) => {
        this.patients = patients;
      });
  },
  methods: {
    deletePatient(username) {
      this.$axios
        .$delete(`/api/patients/${username}`)
        .then((response) => {
          this.$toast.success(response).goAway(3000);
          window.location.reload();
        })
        .catch((error) => {
          this.$toast.error(error.response.data).goAway(3000);
        });
    },
    showConfirmation(affectedLine) {
      this.isShowConfirmation = true;
      this.affectedLine = affectedLine;
    },
    confirm(confirmation) {
      if (confirmation) {
        deletePatient(this.affectedLine);
      }
    },
    disassociatePatient(patientUsername) {
      this.$axios
        .$patch(`/api/patients/${patientUsername}/removeProfessional`, {
          username: this.$auth.user.sub,
        })
        .then((response) => {
          this.$toast.success(response).goAway(3000);
          window.location.reload();
        })
        .catch((error) => {
          this.$toast.error(error.response.data).goAway(3000);
        });
    },
  },
};
</script>

<style scoped>
@media screen and (max-width: 650px) {
  .button-text {
    display: none;
  }

  .button-extra {
    width: 30px;
  }
}

@media screen and (min-width: 651px) {
  .button-extra {
    width: auto;
  }
}
</style>
