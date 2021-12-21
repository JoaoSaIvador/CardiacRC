<template>
  <div>
    <Topbar />
    <b-container>
      <div>
        <h2 class="mb-3">Administrator Details</h2>
        <form class="needs-validation">
          <div class="col-sm-5">
            <label for="name">Name:</label>
            <div class="input-group">
              <input
                type="text"
                class="form-control"
                id="name"
                :value="administrator.name"
                readonly
              />
            </div>
          </div>
          <div class="col-sm-5">
            <label for="username">Username:</label>
            <div class="input-group">
              <input
                type="text"
                class="form-control"
                id="username"
                :value="administrator.username"
                readonly
              />
            </div>
          </div>
          <div class="col-sm-5">
            <label for="email">Email:</label>
            <div class="input-group">
              <input
                type="email"
                class="form-control"
                id="email"
                :value="administrator.email"
                readonly
              />
            </div>
          </div>
        </form>
      </div>

      <b-container>
        <nuxt-link to="/administrators" class="btn btn-primary">Back</nuxt-link>
      </b-container>
    </b-container>
  </div>
</template>

<script>
export default {
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
  methods: {},
};
</script>

<style scoped></style>
