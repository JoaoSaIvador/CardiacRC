<template>
  <UserDetails :user="professional" group="professionals" />
</template>

<script>
export default {
  middleware: "professionalSelf",
  data() {
    return {
      professional: {},
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
