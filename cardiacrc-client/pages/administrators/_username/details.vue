<template>
  <div class="h-100">
    <UserDetails
      v-if="administrator"
      :user="administrator"
      group="administrators"
    />
    <LoadingPage v-else />
  </div>
</template>

<script>
export default {
  middleware: "adminSelf",
  data() {
    return {
      username: this.$route.params.username,
      administrator: null,
    };
  },
  created() {
    this.$axios
      .$get(`/api/administrators/${this.username}`)
      .then((administrator) => (this.administrator = administrator || {}));
  },
};
</script>
