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
  middleware: "professional",
  data() {
    return {
      fields: [
        { key: "id", sortable: true },
        { key: "name", sortable: true },
        { key: "frequency", sortable: true },
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
      this.$axios.$delete(`/api/programs/${id}`).then(() => {
        window.location.reload();
      });
    },
  },
};
</script>
