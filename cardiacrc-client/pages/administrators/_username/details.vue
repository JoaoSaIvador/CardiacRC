<template>
  <div class="primary-div">
    <Topbar />
    <UserDetails :user="administrator" group="administrators" />
  </div>
</template>

<script>
import UserDetails from "../../../components/UserDetails.vue";

export default {
  components: {
    UserDetails,
  },
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
