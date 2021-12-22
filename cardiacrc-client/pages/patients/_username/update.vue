<template>
  <div>
    <Topbar />
    <b-container>
      <h2 class="mb-3 text-center">Update Information</h2>
      <form
        class="needs-validation"
        @submit.prevent="updatePatient"
        :disabled="!isFormValid"
      >
        <div v-if="!isAdmin" class="col-sm-4 offset-sm-4">
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

        <div v-if="!isAdmin" class="col-sm-4 offset-sm-4">
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

        <div v-if="!isAdmin" class="col-sm-4 offset-sm-4">
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

        <div v-if="!isAdmin" class="col-sm-4 offset-sm-4">
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
          <p v-show="errorMsg" class="update-errors text-danger">
            {{ errorMsg }}
          </p>
        </div>

        <div class="col-sm-4 offset-sm-4">
          <button type="reset" class="btn btn-danger">Reset</button>
          <button
            @click.prevent="updatePatient"
            class="btn btn-primary"
            :disabled="!isFormValid"
          >
            Update
          </button>
        </div>
      </form>
    </b-container>
  </div>
</template>

<script>
export default {
  data() {
    return {
      patient: {},
      password: null,
      name: null,
      email: null,
      healthNumber: null,
      errorMsg: false,
    };
  },
  computed: {
    username() {
      return this.$route.params.username;
    },

    isAdmin() {
      return this.$auth.user.groups.includes("Administrator");
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

    invalidHealthNumberFeedback() {
      if (!this.healthNumber) {
        return null;
      }
      let healthNumberLen = this.healthNumber.length;
      if (healthNumberLen != 9) {
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
      if (!this.isAdmin) {
        if (!this.isUsernameValid) {
          return false;
        }

        if (!this.isNameValid) {
          return false;
        }

        if (!this.isEmailValid) {
          return false;
        }

        if (!this.isHealthNumberValid) {
          return false;
        }
      }

      if (!this.isPasswordValid) {
        return false;
      }

      return true;
    },
  },
  created() {
    this.$axios.$get(`/api/patients/${this.username}`).then((patient) => {
      this.patient = patient || {};
      this.name = patient.name;
      this.email = patient.email;
      this.healthNumber = patient.healthNumber;
    });
  },
  methods: {
    updatePatient() {
      this.$axios
        .$put(`/api/patients/${this.username}`, {
          username: this.username,
          password: this.password,
          name: this.name,
          email: this.email,
          healthNumber: this.healthNumber,
        })
        .then(() => {
          this.$router.push("/patients");
        })
        .catch((error) => {
          this.errorMsg = error.response.data;
        });
    },
    reset() {
      this.errorMsg = false;
    },
  },
};
</script>

<style scoped>
.update-errors {
  text-align: center;
}
</style>
