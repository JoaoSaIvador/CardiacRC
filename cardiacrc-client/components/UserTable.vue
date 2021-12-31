<template>
  <b-container class="h-100 d-flex align-items-center">
    <b-container
      class="page-content-xl d-flex flex-column align-items-center bg-light"
    >
      <h1>Manage {{ group.charAt(0).toUpperCase() + group.slice(1) }}</h1>
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
          <b-button
            v-if="!$auth.user.groups.includes('Patient')"
            variant="dark"
            :to="`${group}/create`"
          >
            Create
          </b-button>
          <b-button
            v-if="
              group == 'patients' && $auth.user.groups.includes('Professional')
            "
            variant="dark"
            :to="`professionals/${this.$auth.user.sub}/associatedPatients`"
          >
            Associated Patients
          </b-button>
        </div>
        <div v-if="items.length > 0">
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
          v-if="items.length > 0"
          v-model="currentPage"
          :total-rows="items.length"
          :per-page="perPage"
          class="m-0"
        />
      </div>
      <b-table
        v-if="items.length > 0"
        striped
        hover
        bordered
        head-variant="dark"
        :items="items"
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
              v-if="group == 'dataTypes'"
              variant="dark"
              class="
                table-button
                d-flex
                align-items-center
                justify-content-center
                button-extra
              "
              :to="`${group}/${row.item.id}/qualities`"
            >
              <fa :icon="['fas', 'clipboard-list']" />
              <span class="button-text">&nbsp;Qualities</span>
            </b-button>
            <b-button
              v-if="
                group == 'patients' &&
                $auth.user.groups.includes('Professional') &&
                !associatedPatients.filter(
                  (p) => p.username == row.item.username
                ).length > 0
              "
              variant="dark"
              class="
                table-button
                d-flex
                align-items-center
                justify-content-center
                button-extra
              "
              @click.prevent="associatePatient(row.item.username)"
            >
              <fa :icon="['fas', 'plus']" />
              <span class="button-text">&nbsp;Associate</span>
            </b-button>
            <b-button
              v-else-if="
                group == 'patients' &&
                $auth.user.groups.includes('Professional')
              "
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
              v-if="group == 'prescriptions' || group == 'programs'"
              variant="success"
              class="
                table-button
                d-flex
                align-items-center
                justify-content-center
              "
              :to="`${group}/${row.item.id}/details`"
            >
              <fa :icon="['fas', 'clipboard-list']" />
            </b-button>
            <b-button
              v-if="group != 'administrators'"
              variant="primary"
              class="
                table-button
                d-flex
                align-items-center
                justify-content-center
              "
              :to="`${group}/${
                row.item.username ? row.item.username : row.item.id
              }/update`"
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
              @click.prevent="
                showConfirmation(
                  row.item.username ? row.item.username : row.item.id
                )
              "
            >
              <fa :icon="['fas', 'trash']" />
            </b-button>
          </div>
        </template>
      </b-table>
      <p v-else>No {{ group }}.</p>
      <DeleteConfirmation
        v-show="isShowConfirmation"
        @closeModal="isShowConfirmation = false"
        @confirm="confirm"
      />
    </b-container>
  </b-container>
</template>

<script>
export default {
  name: "UserTable",
  props: {
    fields: Array,
    items: Array,
    sortBy: String,
    group: String,
    associatedPatients: Array,
  },
  data() {
    return {
      currentPage: 1,
      perPage: 10,
      sortDesc: false,
      filter: null,
      isShowConfirmation: false,
      affectedLine: null,
    };
  },
  methods: {
    showConfirmation(affectedLine) {
      this.isShowConfirmation = true;
      this.affectedLine = affectedLine;
    },
    confirm(confirmation) {
      if (confirmation) {
        this.$emit("delete", this.affectedLine);
      }
    },
    associatePatient(patientUsername) {
      this.$axios
        .$patch(`/api/patients/${patientUsername}/addProfessional`, {
          username: this.$auth.user.sub,
        })
        .then(() => {
          window.location.reload();
        })
        .catch((error) => {
          //this.errorMsg = error.response.data;
          //Notification
        });
    },
    disassociatePatient(patientUsername) {
      this.$axios
        .$patch(`/api/patients/${patientUsername}/removeProfessional`, {
          username: this.$auth.user.sub,
        })
        .then(() => {
          window.location.reload();
        })
        .catch((error) => {
          //this.errorMsg = error.response.data;
          //Notification
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
