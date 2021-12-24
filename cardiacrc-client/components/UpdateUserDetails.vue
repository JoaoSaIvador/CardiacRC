<template>
  <b-container class="secondary-div">
    <b-container class="page-content bg-light">
      <h2 class="main-title text-center">Account Details</h2>
      <form class="needs-validation" :disabled="!isFormValid">
        <div v-if="isCreate" class="main-input">
          <b-form-group
            label="Username:"
            label-for="username"
            :invalid-feedback="invalidUsernameFeedback"
            :state="isUsernameValid"
          >
            <b-input
              id="username"
              v-model.trim="localUser.username"
              placeholder="Enter your username"
              :state="isUsernameValid"
            ></b-input>
          </b-form-group>
        </div>

        <div v-if="!isAdminToPatient" class="main-input">
          <b-form-group
            label="Change Name:"
            label-for="name"
            :invalid-feedback="invalidNameFeedback"
            :state="isNameValid"
          >
            <b-input
              id="name"
              placeholder="Enter your name"
              v-model.trim="localUser.name"
              :state="isNameValid"
            ></b-input>
          </b-form-group>
        </div>

        <div v-if="!isAdminToPatient" class="main-input">
          <b-form-group
            label="Change Email:"
            label-for="email"
            :state="isEmailValid"
          >
            <b-input
              ref="email"
              v-model.trim="localUser.email"
              type="email"
              :state="isEmailValid"
              placeholder="Enter your e-mail"
            ></b-input>
          </b-form-group>
        </div>

        <div v-if="isPatient" class="main-input">
          <b-form-group
            label="Health Number:"
            label-for="healthNumber"
            :invalid-feedback="invalidHealthNumberFeedback"
            :state="isHealthNumberValid"
          >
            <b-input
              id="healthNumber"
              type="number"
              v-model.trim="localUser.healthNumber"
              placeholder="Enter your health number"
              :state="isHealthNumberValid"
            ></b-input>
          </b-form-group>
        </div>

        <div v-if="isProfessional" class="main-input">
          <b-form-group
            label="License Number:"
            label-for="licenseNumber"
            :invalid-feedback="invalidLicenseNumberFeedback"
            :state="isLicenseNumberValid"
          >
            <b-input
              id="licenseNumber"
              type="number"
              v-model.trim="localUser.licenseNumber"
              placeholder="Enter your license number"
              :state="isLicenseNumberValid"
            ></b-input>
          </b-form-group>
        </div>

        <div class="main-input">
          <b-form-group
            label="Change Password:"
            label-for="password"
            :invalid-feedback="invalidPasswordFeedback"
            :state="isPasswordValid"
          >
            <b-input
              id="password"
              type="password"
              v-model.trim="localUser.password"
              placeholder="Enter your password"
              :state="isPasswordValid"
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
            class="main-button"
            variant="outline-dark"
            @click="() => this.$router.back()"
          >
            Back
          </b-button>
          <b-button
            v-if="isCreate"
            class="main-button"
            variant="dark"
            :disabled="!isFormValid"
            @click.prevent="$emit('submit', localUser)"
          >
            Create
          </b-button>
          <b-button
            v-else
            class="main-button"
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
</template>

<script>
export default {
  name: "UpdateUserDetails",
  props: {
    user: Object,
    errorMsg: String,
    to: String,
    mode: String,
  },
  data() {
    return {
      localUser: JSON.parse(JSON.stringify(this.user)),
      showConfirmation: false,
      healthNumberLen: 9,
      licenseNumberLen: 9,
    };
  },
  computed: {
    isAdminToPatient() {
      return (
        this.$auth.user.groups.includes("Administrator") && this.to == "patient"
      );
    },

    isPatient() {
      return (
        this.$auth.user.groups.includes("Patient") ||
        (this.$auth.user.groups.includes("Patient") &&
          nthis.to == "professional")
      );
    },

    isProfessional() {
      return this.to == "professional";
    },

    isCreate() {
      return this.mode == "create";
    },

    invalidUsernameFeedback() {
      if (!this.localUser.username) {
        return null;
      }
      let usernameLen = this.localUser.username.length;
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
      if (!this.localUser.password) {
        return null;
      }
      let passwordLen = this.localUser.password.length;
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
      if (!this.localUser.name) {
        return null;
      }
      let nameLen = this.localUser.name.length;
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
      if (!this.localUser.email) {
        return null;
      }

      if (this.$refs.email) {
        return this.$refs.email.checkValidity();
      }

      return true;
    },

    invalidHealthNumberFeedback() {
      if (!this.localUser.healthNumber) {
        return null;
      }

      if (this.localUser.healthNumber.length > 0) {
        this.healthNumberLen = this.localUser.healthNumber.length;
      }

      if (this.healthNumberLen != 9) {
        return "The health number must have 9 digits.";
      }
      return "";
    },

    isHealthNumberValid() {
      if (this.invalidHealthNumberFeedback === null) {
        return null;
      }
      return this.invalidHealthNumberFeedback === "";
    },

    invalidLicenseNumberFeedback() {
      if (!this.localUser.licenseNumber) {
        return null;
      }

      if (this.localUser.licenseNumber.length > 0) {
        this.licenseNumberLen = this.localUser.licenseNumber.length;
      }

      if (this.licenseNumberLen != 9) {
        return "The license number must have 9 digits.";
      }
      return "";
    },

    isLicenseNumberValid() {
      if (this.invalidLicenseNumberFeedback === null) {
        return null;
      }
      return this.invalidLicenseNumberFeedback === "";
    },

    isFormValid() {
      if (this.isCreate) {
        if (!this.isUsernameValid) {
          return false;
        }
      }

      if (!this.isAdminToPatient) {
        if (!this.isNameValid) {
          return false;
        }

        if (!this.isEmailValid) {
          return false;
        }
      }

      if (this.isPatient) {
        if (!this.isHealthNumberValid) {
          return false;
        }
      }

      if (this.isProfessional) {
        if (!this.isLicenseNumberValid) {
          return false;
        }
      }

      if (this.localUser.password != null && this.localUser.password != "") {
        if (!this.isPasswordValid) {
          return false;
        }
      } else {
        this.localUser.password = null;
      }

      return true;
    },
  },
  methods: {
    confirmPassword(passwordConfirmation) {
      this.localUser.passwordConfirmation = passwordConfirmation;
      this.$emit("submit", this.localUser);
    },
  },
};
</script>
