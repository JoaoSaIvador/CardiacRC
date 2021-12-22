<template>
  <div class="primary-div">
    <Topbar />
    <b-container class="secondary-div">
      <b-container class="page-content bg-light">
        <h2 class="details-title text-center">Account Details</h2>
        <form>
          <div class="details-input">
            <b-form-group id="name" label="Name:" label-for="name">
              <b-input id="name" :value="administrator.name" readonly></b-input>
            </b-form-group>
          </div>

          <div class="details-input">
            <b-form-group id="username" label="Username:" label-for="username">
              <b-input
                id="username"
                :value="administrator.username"
                readonly
              ></b-input>
            </b-form-group>
          </div>

          <div class="details-input">
            <b-form-group id="email" label="Email:" label-for="email">
              <b-input
                id="email"
                :value="administrator.username"
                readonly
              ></b-input>
            </b-form-group>
          </div>

          <div class="button-group">
            <b-button
              class="details-button"
              variant="outline-dark"
              @click="() => this.$router.back()"
            >
              Back
            </b-button>
            <b-button
              class="details-button"
              variant="dark"
              :to="`/administrators/${administrator.username}/update`"
            >
              Update
            </b-button>
          </div>
        </form>
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
