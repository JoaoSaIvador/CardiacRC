<template>
  <div>
    <Topbar />
    <b-container>
      <div>
        <h2 class="mb-3 text-center">Account Details</h2>
        <form
          class="needs-validation"
          @submit.prevent="updatePatient"
          :disabled="!isFormValid"
        >
          <div class="col-sm-4 offset-sm-4">
            <b-form-group id="name" label="Name:" label-for="name">
              <b-input id="name" :value="administrator.name" readonly></b-input>
            </b-form-group>
          </div>

          <div class="col-sm-4 offset-sm-4">
            <b-form-group id="username" label="Username:" label-for="username">
              <b-input
                id="username"
                :value="administrator.username"
                readonly
              ></b-input>
            </b-form-group>
          </div>

          <div class="col-sm-4 offset-sm-4">
            <b-form-group id="email" label="Email:" label-for="email">
              <b-input
                id="email"
                :value="administrator.username"
                readonly
              ></b-input>
            </b-form-group>
          </div>

          <div class="col-sm-4 offset-sm-4">
            <nuxt-link
              :to="`/administrators/dashboard`"
              class="btn btn-secondary"
              >Back</nuxt-link
            >
            <nuxt-link
              :to="`/administrators/${administrator.username}/update`"
              class="btn btn-primary"
              >Update</nuxt-link
            >
          </div>
        </form>
      </div>
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
