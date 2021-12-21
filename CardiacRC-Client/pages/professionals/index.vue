<template>
  <div class="main-div">
    <Topbar />
    <b-container>
      <h1>Professional List:</h1>
      <b-table
        striped
        over
        outlined
        :items="professionals"
        :fields="fields"
        class="mt-3"
      >
        <template v-slot:cell(actions)="row">
          <nuxt-link
            class="btn btn-primary btn-xs"
            :to="`/professionals/${row.item.username}/details`"
          >
            <fa :icon="['fas', 'clipboard-list']" />
          </nuxt-link>
          <nuxt-link
            class="btn btn-secondary btn-xs"
            :to="`/professionals/${row.item.username}/update`"
          >
            <fa :icon="['fas', 'pen']" />
          </nuxt-link>
          <button
            class="btn btn-danger btn-xs"
            @click.prevent="deleteProfessional(row.item.username)"
          >
            <fa :icon="['fas', 'trash']" />
          </button>
        </template>
      </b-table>
    </b-container>
    <b-container>
      <nuxt-link to="/" class="btn btn-primary">Back</nuxt-link>
      <nuxt-link to="/professionals/create" class="btn btn-secondary"
        >Create a New Professional</nuxt-link
      >
    </b-container>
  </div>
</template>

<script>
export default {
  data() {
    return {
      fields: ["username", "name", "email", "licenseNumber", "actions"],
      professionals: [],
    };
  },
  created() {
    this.$axios.$get("/api/professionals").then((professionals) => {
      this.professionals = professionals;
    });
  },
  methods: {
    deleteProfessional(username) {
      this.$axios.$delete(`/api/professionals/${username}`).then(() => {
        window.location.reload();
      });
    },
  },
};
</script>

<style scoped></style>
