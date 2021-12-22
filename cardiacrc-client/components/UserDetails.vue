<template>
  <b-container class="secondary-div">
    <b-container class="page-content bg-light">
      <h2 class="details-title text-center">Account Details</h2>
      <form>
        <div class="details-input">
          <b-form-group id="name" label="Name:" label-for="name">
            <b-input id="name" :value="user.name" readonly></b-input>
          </b-form-group>
        </div>

        <div class="details-input">
          <b-form-group id="username" label="Username:" label-for="username">
            <b-input id="username" :value="user.username" readonly></b-input>
          </b-form-group>
        </div>

        <div class="details-input">
          <b-form-group id="email" label="Email:" label-for="email">
            <b-input id="email" :value="user.username" readonly></b-input>
          </b-form-group>
        </div>

        <div v-if="group == 'patients'" class="details-input">
          <b-form-group
            id="healthNumber"
            label="Health Number:"
            label-for="healthNumber"
          >
            <b-input
              id="healthNumber"
              :value="user.healthNumber"
              readonly
            ></b-input>
          </b-form-group>
        </div>

        <div v-if="group == 'professionals'" class="details-input">
          <b-form-group
            id="licenseNumber"
            label="License Number:"
            label-for="licenseNumber"
          >
            <b-input
              id="licenseNumber"
              :value="user.licenseNumber"
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
            :to="`/administrators/${user.username}/update`"
          >
            Update
          </b-button>
        </div>
      </form>
    </b-container>
  </b-container>
</template>

<script>
export default {
  name: "UserDetails",
  props: {
    user: Object,
    group: String,
  },
};
</script>
