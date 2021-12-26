<template>
  <b-container class="secondary-div">
    <b-container class="page-content bg-light">
      <h2 class="main-title text-center">
        {{ group.charAt(0).toUpperCase() + group.slice(1) }} Details
      </h2>
      <form class="needs-validation" :disabled="!isFormValid">
        <div class="main-input">
          <b-form-group
            label="Name:"
            label-for="name"
            :invalid-feedback="invalidNameFeedback"
            :state="isNameValid"
          >
            <b-input
              id="name"
              placeholder="Enter a name"
              v-model.trim="localPrescription.name"
              :state="isNameValid"
            ></b-input>
          </b-form-group>
        </div>
        <div class="main-input">
          <b-form-group
            label="Frequency:"
            label-for="frequency"
            :invalid-feedback="invalidFrequencyFeedback"
            :state="isFrequencyValid"
          >
            <b-input
              id="unit"
              placeholder="Enter a frequency"
              v-model.trim="localPrescription.frequency"
              :state="isFrequencyValid"
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
            @click.prevent="$emit('submit', localPrescription)"
          >
            Create
          </b-button>
          <b-button
            v-else
            class="main-button"
            variant="dark"
            :disabled="!isFormValid"
            @click.prevent="$emit('submit', localPrescription)"
          >
            Save
          </b-button>
        </div>
      </form>
    </b-container>
  </b-container>
</template>

<script>
export default {
  name: "SetPrescriptionDetails",
  props: {
    prescription: Object,
    mode: String,
    group: String,
  },
  data() {
    return {
      errorMsg: false,
      localPrescription: JSON.parse(JSON.stringify(this.prescription)),
    };
  },
  computed: {
    isCreate() {
      return this.mode == "create";
    },

    invalidNameFeedback() {
      if (!this.localPrescription.name) {
        return null;
      }
      let nameLen = this.localPrescription.name.length;
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

    invalidFrequencyFeedback() {
      if (!this.localPrescription.frequency) {
        return null;
      }

      return "";
    },

    isFrequencyValid() {
      if (this.invalidFrequencyFeedback === null) {
        return null;
      }
      return this.invalidFrequencyFeedback === "";
    },

    isFormValid() {
      if (!this.isNameValid) {
        return false;
      }

      if (!this.isFrequencyValid) {
        return false;
      }

      return true;
    },
  },
};
</script>
