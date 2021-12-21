<template>
  <div class="main-div">
    <Topbar />
    <b-container>
      <h1>Manage Administrators:</h1>
      <b-table
        striped
        over
        outlined
        :items="administrators"
        :fields="fields"
        class="mt-3"
      >
        <template v-slot:cell(actions)="row">
          <button
            class="btn btn-danger btn-xs"
            @click.prevent="deleteAdministrator(row.item.username)"
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
      <nuxt-link to="/administrators/create" class="btn btn-primary"
        >Create</nuxt-link
      >
    </b-container>
  </div>
</template>

<script>
export default {
  data() {
    return {
      fields: ["username", "name", "email", "actions"],
      administrators: [],
    };
  },
  created() {
    this.$axios.$get("/api/administrators").then((administrators) => {
      this.administrators = administrators;
    });
  },
  methods: {
    deleteAdministrator(username) {
      this.$axios.$delete(`/api/administrators/${username}`).then(() => {
        window.location.reload();
      });
    },
  },
};
</script>

<style scoped></style>
