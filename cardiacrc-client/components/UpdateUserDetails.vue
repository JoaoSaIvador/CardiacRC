<template>
  <b-container class="secondary-div">
    <b-container class="page-content bg-light">
      <h2 class="main-title text-center">Account Details</h2>
      <form
        class="needs-validation"
        @submit.prevent="showConfirmation = true"
        :disabled="!isFormValid"
      >
        <div v-if="!isAdminToPatient" class="main-input">
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

        <div v-if="!isAdminToPatient" class="main-input">
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

        <div v-if="isPatient" class="main-input">
          <b-form-group
            id="healthNumber"
            label="Health Number:"
            label-for="healthNumber"
            :invalid-feedback="invalidHealthNumberFeedback"
            :state="isHealthNumberValid"
          >
            <b-input
              id="healthNumber"
              type="number"
              v-model.trim="healthNumber"
              placeholder="Enter your health number"
              :state="isHealthNumberValid"
              trim
            ></b-input>
          </b-form-group>
        </div>

        <div class="main-input">
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
            class="main-button"
            variant="outline-dark"
            @click="() => this.$router.back()"
          >
            Back
          </b-button>
          <b-button
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
    name: String,
    email: String,
    healthNumber: String,
    licenseNumber: String,
    errorMsg: String,
    to: String,
  },
  data() {
    return {
      password: null,
      user: {
        name: this.name,
        email: this.email,
        password: null,
        passwordConfirmation: null,
        healthNumber: this.healthNumber,
        licenseNumber: this.licenseNumber,
      },
      showConfirmation: false,
    };
  },
  computed: {
    isAdminToPatient() {
      return (
        this.$auth.user.groups.includes("Administrator") && this.to == "patient"
      );
    },

    isPatient() {
      return this.$auth.user.groups.includes("Patient");
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

    invalidHealthNumberFeedback() {
      if (!this.healthNumber) {
        return null;
      }

      if (this.healthNumber.length > 0) {
        this.healthNumberLen = this.healthNumber.length;
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

    isFormValid() {
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

      if (this.password != null && this.password != "") {
        if (!this.isPasswordValid) {
          return false;
        }
      } else {
        this.password = null;
      }

      return true;
    },
  },
  methods: {
    confirmPassword(passwordConfirmation) {
      this.user = {
        name: this.name,
        email: this.email,
        password: this.password,
        passwordConfirmation: passwordConfirmation,
        healthNumber: this.healthNumber,
        licenseNumber: this.licenseNumber,
      };
      this.$emit("update", this.user);
    },
  },
};
</script>
