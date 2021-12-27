<template>
  <div class="h-100">
    <UserDetails
      :user="professional"
      group="professionals"
      v-if="professional"
    />
    <LoadingPage v-else />
  </div>
</template>

<script>
export default {
  middleware: "professionalSelf",
  data() {
    return {
      professional: null,
    };
  },
  computed: {
    username() {
      return this.$route.params.username;
    },
  },
  created() {
    this.$axios
      .$get(`/api/professionals/${this.username}`)
      .then((professional) => (this.professional = professional || {}));
  },
};
</script>
