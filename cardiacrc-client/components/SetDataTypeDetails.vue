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
              v-model.trim="localDataType.name"
              :state="isNameValid"
            ></b-input>
          </b-form-group>
        </div>
        <div class="main-input" v-if="group == 'dataType'">
          <b-form-group
            label="Unit:"
            label-for="unit"
            :invalid-feedback="invalidUnitFeedback"
            :state="isUnitValid"
          >
            <b-input
              id="unit"
              placeholder="Enter a unit"
              v-model.trim="localDataType.unit"
              :state="isUnitValid"
            ></b-input>
          </b-form-group>
        </div>
        <div class="main-input">
          <b-form-group
            label="Minimum Value:"
            label-for="min"
            :invalid-feedback="invalidMinFeedback"
            :state="isMinValid"
          >
            <b-input
              id="min"
              placeholder="Enter a minimum value"
              type="number"
              v-model.trim="localDataType.min"
              :state="isMinValid"
            ></b-input>
          </b-form-group>
        </div>
        <div class="main-input">
          <b-form-group
            label="Maximum Value:"
            label-for="max"
            :invalid-feedback="invalidMaxFeedback"
            :state="isMaxValid"
          >
            <b-input
              id="max"
              placeholder="Enter a maximum value"
              type="number"
              v-model.trim="localDataType.max"
              :state="isMaxValid"
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
            @click.prevent="$emit('submit', localDataType)"
          >
            Create
          </b-button>
          <b-button
            v-else
            class="main-button"
            variant="dark"
            :disabled="!isFormValid"
            @click.prevent="$emit('submit', localDataType)"
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
  name: "UpdateUserDetails",
  props: {
    dataType: Object,
    parentDataType: Object,
    mode: String,
    group: String,
  },
  data() {
    return {
      errorMsg: false,
      localDataType: JSON.parse(JSON.stringify(this.dataType)),
    };
  },
  computed: {
    isCreate() {
      return this.mode == "create";
    },

    invalidNameFeedback() {
      if (!this.localDataType.name) {
        return null;
      }
      let nameLen = this.localDataType.name.length;
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

    invalidUnitFeedback() {
      if (!this.localDataType.unit) {
        return null;
      }

      return "";
    },

    isUnitValid() {
      if (this.invalidUnitFeedback === null) {
        return null;
      }
      return this.invalidUnitFeedback === "";
    },

    invalidMinFeedback() {
      if (!this.localDataType.min) {
        return null;
      }

      if (
        parseFloat(this.localDataType.min) > parseFloat(this.localDataType.max)
      ) {
        return "Minimum value must be less than Maximum value.";
      }

      if (
        this.group == "quality" &&
        parseFloat(this.parentDataType.min) > parseFloat(this.localDataType.min)
      ) {
        return "Minimum value must be the same or greater than Data Type's Minimum value.";
      }

      return "";
    },

    isMinValid() {
      if (this.invalidMinFeedback === null) {
        return null;
      }
      return this.invalidMinFeedback === "";
    },

    invalidMaxFeedback() {
      if (!this.localDataType.max) {
        return null;
      }

      if (
        parseFloat(this.localDataType.max) < parseFloat(this.localDataType.min)
      ) {
        return "Maximum value must be greater than Minimum value.";
      }

      if (
        this.group == "quality" &&
        parseFloat(this.parentDataType.max) < parseFloat(this.localDataType.max)
      ) {
        return "Minimum value must be the same or less than Data Type's Maximum value.";
      }

      return "";
    },

    isMaxValid() {
      if (this.invalidMaxFeedback === null) {
        return null;
      }
      return this.invalidMaxFeedback === "";
    },

    isFormValid() {
      if (!this.isNameValid) {
        return false;
      }

      if (this.group == "dataType" && !this.isUnitValid) {
        return false;
      }

      if (!this.isMinValid) {
        return false;
      }

      if (!this.isMaxValid) {
        return false;
      }

      return true;
    },
  },
};
</script>
