<template>
  <b-container class="h-100 d-flex align-items-center">
    <b-container
      class="
        page-content
        d-flex
        flex-column
        align-items-center
        justify-content-center
        bg-light
      "
    >
      <h2 class="main-title text-center">Account Details</h2>
      <form>
        <div class="main-input">
          <b-form-group id="name" label="Name:" label-for="name">
            <b-input id="name" :value="user.name" readonly></b-input>
          </b-form-group>
        </div>

        <div class="main-input">
          <b-form-group id="username" label="Username:" label-for="username">
            <b-input id="username" :value="user.username" readonly></b-input>
          </b-form-group>
        </div>

        <div class="main-input">
          <b-form-group id="email" label="Email:" label-for="email">
            <b-input id="email" :value="user.email" readonly></b-input>
          </b-form-group>
        </div>

        <div v-if="group == 'patients'" class="main-input">
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

        <div v-if="group == 'professionals'" class="main-input">
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

        <div class="d-flex flex-row justify-content-center">
          <b-button
            class="main-button"
            variant="outline-dark"
            @click="() => this.$router.back()"
          >
            Back
          </b-button>
          <b-button
            class="main-button"
            variant="dark"
            :to="`/${group}/${user.username}/update`"
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
