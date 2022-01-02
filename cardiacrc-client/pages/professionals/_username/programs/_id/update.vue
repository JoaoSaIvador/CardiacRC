<template>
  <div class="h-100">
    <b-container class="h-100 d-flex align-items-center" v-if="program">
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
        <h2 class="main-title text-center">Program Details</h2>
        <form class="needs-validation" :disabled="!isFormValid">
          <div class="main-input">
            <b-form-group
              label="Duration:"
              label-for="duration"
              :invalid-feedback="invalidDurationFeedback"
              :state="isDurationValid"
            >
              <b-input
                id="duration"
                placeholder="Enter a duration"
                type="number"
                v-model.trim="program.duration"
                :state="isDurationValid"
              ></b-input>
            </b-form-group>
          </div>
          <div>
            <p v-show="errorMsg" class="text-danger">
              {{ errorMsg }}
            </p>
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
              :disabled="!isFormValid"
              @click.prevent="updateProgram(program)"
            >
              Save
            </b-button>
            <b-button
              class="main-button"
              variant="dark"
              :to="`prescriptions/create`"
            >
              Add Prescription
            </b-button>
          </div>
        </form>
      </b-container>
    </b-container>
    <LoadingPage v-else />
  </div>
</template>

<script>
export default {
  middleware: "professional",
  data() {
    return {
      program: null,
      errorMsg: false,
      id: this.$route.params.id,
    };
  },
  created() {
    this.$axios.$get(`/api/programs/${this.id}`).then((program) => {
      this.program = program || {};
    });
  },
  computed: {
    invalidDurationFeedback() {
      if (!this.program.duration) {
        return null;
      }

      return "";
    },

    isDurationValid() {
      if (this.invalidDurationFeedback === null) {
        return null;
      }
      return this.invalidDurationFeedback === "";
    },

    isFormValid() {
      if (!this.isDurationValid) {
        return false;
      }

      return true;
    },
  },
  methods: {
    updateProgram(program) {
      this.$axios
        .$patch(`/api/programs/${this.id}`, program)
        .then(() => {
          this.$router.back();
        })
        .catch((error) => {
          //this.errorMsg = error.response.data;
          //Notification
        });
    },
  },
};
</script>
