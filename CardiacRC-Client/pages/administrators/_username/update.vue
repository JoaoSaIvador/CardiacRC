<template>
  <div class="main-div">
    <Topbar />
    <b-container class="page-content">
      <h2 class="mb-3 text-center">Account Details</h2>
      <form
        class="needs-validation"
        @submit.prevent="showConfirmation = true"
        :disabled="!isFormValid"
      >
        <div class="col-sm-4 offset-sm-4">
          <b-form-group
            id="name"
            label="Name:"
            label-for="name"
            :invalid-feedback="invalidNameFeedback"
            :state="isNameValid"
          >
            <b-input
              id="name"
              v-model.trim="name"
              placeholder="Enter your name"
              :state="isNameValid"
              trim
            ></b-input>
          </b-form-group>
        </div>

        <div class="col-sm-4 offset-sm-4">
          <b-form-group
            id="username"
            label="Username:"
            label-for="username"
            :invalid-feedback="invalidUsernameFeedback"
            :state="isUsernameValid"
          >
            <b-input
              id="username"
              v-model.trim="username"
              placeholder="Enter your username"
              :state="isUsernameValid"
              trim
            ></b-input>
          </b-form-group>
        </div>

        <div class="col-sm-4 offset-sm-4">
          <b-form-group
            id="email"
            label="Email:"
            label-for="email"
            :state="isEmailValid"
          >
            <b-input
              ref="email"
              v-model.trim="email"
              type="email"
              :state="isEmailValid"
              placeholder="Enter your e-mail"
              trim
            ></b-input>
          </b-form-group>
        </div>

        <div class="col-sm-4 offset-sm-4">
          <b-form-group
            id="password"
            label="Password:"
            label-for="password"
            :invalid-feedback="invalidPasswordFeedback"
            :state="isPasswordValid"
          >
            <b-input
              id="password"
              type="password"
              v-model.trim="password"
              placeholder="Enter your password"
              :state="isPasswordValid"
              trim
            ></b-input>
          </b-form-group>
        </div>

        <div>
          <p v-show="errorMsg" class="text-danger">
            {{ errorMsg }}
          </p>
        </div>

        <div class="col-sm-4 offset-sm-4">
          <nuxt-link
            :to="`/administrators/${username}/details`"
            class="btn btn-secondary"
            >Back</nuxt-link
          >
          <button type="reset" class="btn btn-danger">Reset</button>
          <button
            @click.prevent="showConfirmation = true"
            class="btn btn-primary"
            :disabled="!isFormValid"
          >
            Save
          </button>
        </div>
      </form>
      <PasswordConfirmation
        v-show="showConfirmation"
        @closeModal="showConfirmation = false"
        @confirmPassword="confirmPassword"
      />
    </b-container>
  </div>
</template>

<script>
import PasswordConfirmation from "../../../components/PasswordConfirmation.vue";

export default {
  components: {
    PasswordConfirmation,
  },
  data() {
    return {
      administrator: {},
      password: null,
      name: null,
      email: null,
      errorMsg: false,
      showConfirmation: false,
      passwordConfirmation: null,
    };
  },
  computed: {
    username() {
      return this.$route.params.username;
    },

    invalidUsernameFeedback() {
      if (!this.username) {
        return null;
      }
      let usernameLen = this.username.length;
      if (usernameLen < 3 || usernameLen > 15) {
        return "The username must be between [3, 15] characters.";
      }

      return "";
    },

    isUsernameValid() {
      if (this.invalidUsernameFeedback === null) {
        return null;
      }
      return this.invalidUsernameFeedback === "";
    },

    invalidPasswordFeedback() {
      if (!this.password) {
        return null;
      }
      let passwordLen = this.password.length;
      if (passwordLen < 6 || passwordLen > 20) {
        return "The password must be between [6, 20] characters.";
      }
      return "";
    },

    isPasswordValid() {
      if (this.invalidPasswordFeedback === null) {
        return null;
      }
      return this.invalidPasswordFeedback === "";
    },

    invalidNameFeedback() {
      if (!this.name) {
        return null;
      }
      let nameLen = this.name.length;
      if (nameLen < 3 || nameLen > 25) {
        return "The name must be between [3, 25] characters.";
      }
      return "";
    },

    isNameValid() {
      if (this.invalidNameFeedback === null) {
        return null;
      }
      return this.invalidNameFeedback === "";
    },

    isEmailValid() {
      if (!this.email) {
        return null;
      }
      return this.$refs.email.checkValidity();
    },

    isFormValid() {
      if (!this.isUsernameValid) {
        return false;
      }

      if (!this.isPasswordValid) {
        return false;
      }

      if (!this.isNameValid) {
        return false;
      }

      if (!this.isEmailValid) {
        return false;
      }

      return true;
    },
  },
  created() {
    this.$axios
      .$get(`/api/administrators/${this.username}`)
      .then((administrator) => {
        this.administrator = administrator || {};
        this.name = administrator.name;
        this.email = administrator.email;
      });
  },
  methods: {
    updatePatient() {
      this.$axios
        .$put(`/api/administrators/${this.username}`, {
          username: this.username,
          password: this.password,
          name: this.name,
          email: this.email,
          passwordConfirmation: this.passwordConfirmation,
        })
        .then(() => {
          this.$router.push("/administrators/dashboard");
        })
        .catch((error) => {
          this.errorMsg = error.response.data;
        });
    },
    reset() {
      this.errorMsg = false;
    },
    confirmPassword(passwordConfirmation) {
      this.passwordConfirmation = passwordConfirmation;
      this.updatePatient();
    },
  },
};
</script>

<style scoped>
.main-div {
  height: 100%;
}

.page-content {
  height: 100%;
}
</style>
