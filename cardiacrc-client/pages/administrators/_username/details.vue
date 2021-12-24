<template>
  <UserDetails :user="administrator" group="administrators" />
</template>

<script>
export default {
  middleware: "adminSelf",
  data() {
    return {
      administrator: {},
    };
  },
  computed: {
    username() {
      return this.$route.params.username;
    },
  },
  created() {
    this.$axios
      .$get(`/api/administrators/${this.username}`)
      .then((administrator) => (this.administrator = administrator || {}));
  },
};
</script>
