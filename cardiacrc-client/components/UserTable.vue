<template>
  <b-container class="secondary-div">
    <b-container class="page-content-xl bg-light">
      <h1>Manage {{ group.charAt(0).toUpperCase() + group.slice(1) }}</h1>
      <div class="table-settings">
        <div>
          <b-button variant="outline-dark" @click="() => this.$router.back()">
            Back
          </b-button>
          <b-button
            v-if="group != 'patients'"
            variant="dark"
            :to="`${group}/create`"
          >
            Create
          </b-button>
        </div>
        <div>
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
          v-model="currentPage"
          :total-rows="items.length"
          :per-page="perPage"
          class="m-0"
        />
      </div>
      <b-table
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
          <div class="button-group">
            <b-button
              v-if="group == 'dataTypes'"
              variant="dark"
              class="table-button qualities-button"
              :to="`${group}/${row.item.id}/qualities`"
            >
              <fa :icon="['fas', 'clipboard-list']" />
              <span class="button-text">&nbsp;Qualities</span>
            </b-button>
            <b-button
              v-if="group != 'administrators'"
              variant="primary"
              class="table-button"
              :to="`${group}/${
                row.item.username ? row.item.username : row.item.id
              }/update`"
            >
              <fa :icon="['fas', 'pen']" />
            </b-button>
            <b-button
              variant="danger"
              class="table-button"
              @click.prevent="
                $emit(
                  'delete',
                  row.item.username ? row.item.username : row.item.id
                )
              "
            >
              <fa :icon="['fas', 'trash']" />
            </b-button>
          </div>
        </template>
      </b-table>
    </b-container>
  </b-container>
</template>

<script>
export default {
  name: "ManageUserTable",
  props: {
    fields: Array,
    items: Array,
    sortBy: String,
    group: String,
  },
  data() {
    return {
      currentPage: 1,
      perPage: 10,
      sortDesc: false,
      filter: null,
    };
  },
};
</script>

<style scoped>
@media screen and (max-width: 650px) {
  .button-text {
    display: none;
  }

  .qualities-button {
    width: 30px;
  }
}

@media screen and (min-width: 651px) {
  .qualities-button {
    width: auto;
  }
}
</style>
