<template>
  <div>
    <Topbar />
    <b-container>
      <h2 class="mb-3 text-center">Create Patient</h2>
      <form
        class="needs-validation"
        @submit.prevent="createPatient"
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
            />
          </b-form-group>
        </div>

        <div class="col-sm-4 offset-sm-4">
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
          <label>Associated Professional:</label>
          <b-select
            v-model="professionalUsername"
            :options="professionals"
            :state="isProfessionalValid"
            required
            value-field="username"
            text-field="name"
          >
            <template v-slot:first>
              <option :value="null" disabled>
                -- Please select a Professional --
              </option>
            </template>
          </b-select>
        </div>

        <div>
          <p v-show="errorMsg" class="text-danger">
            {{ errorMsg }}
          </p>
        </div>

        <div class="col-sm-4 offset-sm-4">
          <nuxt-link to="/patients" class="btn btn-primary">Back</nuxt-link>
          <button type="reset" class="btn btn-danger">Reset</button>
          <button
            @click.prevent="createPatient"
            class="btn btn-secondary"
            :disabled="!isFormValid"
          >
            Create
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
      username: null,
      password: null,
      name: null,
      email: null,
      healthNumber: null,
      professionalUsername: null,
      professionals: [],
      errorMsg: false,
    };
  },

  created() {
    this.$axios.$get("/api/professionals").then((professionals) => {
      this.professionals = professionals;
    });
  },

  computed: {
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
        return "THe health number must have 9 digits.";
      }
      return "";
    },

    isHealthNumberValid() {
      if (this.invalidHealthNumberFeedback === null) {
        return null;
      }
      return this.invalidHealthNumberFeedback === "";
    },

    isProfessionalValid() {
      if (!this.professionalUsername) {
        return null;
      }
      return this.professionals.some(
        (professional) => this.professionalUsername === professional.username
      );
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

      if (!this.isHealthNumberValid) {
        return false;
      }

      if (!this.isProfessionalValid) {
        return false;
      }

      return true;
    },
  },

  methods: {
    createPatient() {
      this.$axios
        .$post("/api/patients", {
          username: this.username,
          password: this.password,
          name: this.name,
          email: this.email,
          healthNumber: this.healthNumber,
          professionalUsername: this.professionalUsername,
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

<style scoped></style>
