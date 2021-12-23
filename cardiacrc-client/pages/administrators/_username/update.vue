<template>
  <div class="primary-div">
    <b-container class="secondary-div">
      <b-container class="page-content bg-light">
        <h2 class="details-title text-center">Account Details</h2>
        <form
          class="needs-validation"
          @submit.prevent="showConfirmation = true"
          :disabled="!isFormValid"
        >
          <div class="details-input">
            <b-form-group
              id="name"
              label="Change Name:"
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

          <div class="details-input">
            <b-form-group
              id="username"
              label="Change Username:"
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

          <div class="details-input">
            <b-form-group
              id="email"
              label="Change Email:"
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

          <div class="details-input">
            <b-form-group
              id="password"
              label="Change Password:"
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
              :disabled="!isFormValid"
              @click.prevent="showConfirmation = true"
            >
              Save
            </b-button>
          </div>
        </form>
        <PasswordConfirmation
          v-show="showConfirmation"
          @closeModal="showConfirmation = false"
          @confirmPassword="confirmPassword"
        />
      </b-container>
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

      if (this.password != null && this.password != "") {
        if (!this.isPasswordValid) {
          return false;
        }
      } else {
        this.password = null;
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

<style scoped></style>
