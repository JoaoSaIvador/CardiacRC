<template>
  <div class="h-100">
    <UserTable
      v-if="programs"
      :items="programs"
      :fields="fields"
      sortBy="id"
      group="programs"
      @delete="deleteProgram"
    />
    <LoadingPage v-else />
  </div>
</template>

<script>
export default {
  middleware: "professionalSelf",
  head() {
    return {
      title: "Manage Programs",
    };
  },
  data() {
    return {
      fields: [
        { key: "id", sortable: true },
        { key: "duration", sortable: true },
        { key: "startDate", sortable: true },
        { key: "professionalUsername", sortable: true },
        { key: "patientUsername", sortable: true },
        { key: "actions", sortable: false },
      ],
      programs: null,
      username: this.$auth.user.sub,
    };
  },
  created() {
    this.$axios
      .$get(`/api/professionals/${this.username}`)
      .then((professionals) => {
        this.programs = professionals.programsDTOs;
      });
  },
  methods: {
    deleteProgram(id) {
      this.$axios
        .$delete(`/api/programs/${id}`)
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
