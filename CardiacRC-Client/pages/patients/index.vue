<template>
  <div class="main-div">
    <Topbar />
    <b-container>
      <h1>Patient List:</h1>
      <b-table
        striped
        over
        outlined
        :items="patients"
        :fields="fields"
        class="mt-3"
      >
        <template v-slot:cell(actions)="row">
          <nuxt-link
            class="btn btn-primary btn-xs"
            :to="`/patients/${row.item.username}/update`"
          >
            <fa :icon="['fas', 'pen']" />
          </nuxt-link>
          <button
            class="btn btn-danger btn-xs"
            @click.prevent="deletePatient(row.item.username)"
          >
            <fa :icon="['fas', 'trash']" />
          </button>
        </template>
      </b-table>
    </b-container>
    <b-container>
      <nuxt-link to="/administrators/dashboard" class="btn btn-secondary"
        >Back</nuxt-link
      >
      <nuxt-link to="/patients/create" class="btn btn-primary"
        >Create</nuxt-link
      >
    </b-container>
  </div>
</template>

<script>
export default {
  data() {
    return {
      fields: ["username", "name", "email", "healthNumber", "actions"],
      patients: [],
    };
  },
  created() {
    this.$axios.$get("/api/patients").then((patients) => {
      this.patients = patients;
    });
  },
  methods: {
    deletePatient(username) {
      this.$axios.$delete(`/api/patients/${username}`).then(() => {
        window.location.reload();
      });
    },
  },
};
</script>

<style scoped></style>
