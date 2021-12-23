<template>
  <div class="primary-div">
    <Topbar />
    <b-container class="secondary-div">
      <b-container class="page-content bg-light">
        <h2 class="details-title text-center">Account Details</h2>
        <form
          class="needs-validation"
          @submit.prevent="updatePatient"
          :disabled="!isFormValid"
        >
          <div v-if="!isAdmin" class="details-input">
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

          <div v-if="!isAdmin" class="details-input">
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

          <div v-if="!isAdmin" class="details-input">
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

          <div v-if="!isAdmin" class="details-input">
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
              @click.prevent="updatePatient"
            >
              Save
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
      patient: {},
      password: null,
      name: null,
      email: null,
      healthNumber: null,
      healthNumberLen: 9,
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

@media screen and (min-width: 651px) {
  .page-content {
    width: 500px;
    height: 700px;
    border-radius: 20px;
    box-shadow: rgba(0, 0, 0, 0.05) 0px 14px 28px,
      rgba(0, 0, 0, 0.05) 0px 10px 10px;
  }

  .details-title {
    margin: 50px 0 50px 0;
  }
}

.page-content {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start;
}
</style>
