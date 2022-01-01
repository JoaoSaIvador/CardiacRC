<template>
  <b-container class="h-100 d-flex align-items-center">
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
      <h2 class="main-title text-center">Observation Details</h2>
      <form class="needs-validation" :disabled="!isFormValid">
        <div class="main-input">
          <b-form-group
            label="Value:"
            label-for="value"
            :invalid-feedback="invalidValueFeedback"
            :state="isValueValid"
          >
            <b-input
              id="value"
              type="number"
              placeholder="Enter a value"
              v-model.trim="localObservation.value"
              :state="isValueValid"
            ></b-input>
          </b-form-group>
        </div>
        <div class="main-input">
          <b-form-group
            label="Data Type:"
            label-for="dataType"
            :invalid-feedback="invalidDataTypeFeedback"
            :state="isDataTypeValid"
          >
            <b-select
              id="dataType"
              v-model="localObservation.dataTypeId"
              :options="dataTypes"
              required
              value-field="id"
              text-field="name"
              :state="isDataTypeValid"
            >
              <template v-slot:first>
                <option :value="null" disabled>-- Select a Data Type --</option>
              </template>
            </b-select>
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
            v-if="isCreate"
            class="main-button"
            variant="dark"
            :disabled="!isFormValid"
            @click.prevent="$emit('submit', localObservation)"
          >
            Create
          </b-button>
          <b-button
            v-else
            class="main-button"
            variant="dark"
            :disabled="!isFormValid"
            @click.prevent="$emit('submit', localObservation)"
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
  name: "SetObservationDetails",
  props: {
    observation: Object,
    dataTypes: Array,
    mode: String,
  },
  data() {
    return {
      errorMsg: false,
      localObservation: JSON.parse(JSON.stringify(this.observation)),
      currentDataType: null,
    };
  },
  computed: {
    isCreate() {
      return this.mode == "create";
    },

    invalidValueFeedback() {
      if (!this.localObservation.value) {
        return null;
      }

      this.currentDataType = this.dataTypes.find((d) => {
        return d.id == this.localObservation.dataTypeId;
      });

      if (this.localObservation.value < this.currentDataType.min) {
        return "Value must be greater than Data Type's minimum value!";
      }

      if (this.localObservation.value > this.currentDataType.max) {
        return "Value must be less than Data Type's maximum value!";
      }

      return "";
    },

    isValueValid() {
      if (this.invalidValueFeedback === null) {
        return null;
      }
      return this.invalidValueFeedback === "";
    },

    invalidDataTypeFeedback() {
      if (!this.localObservation.dataTypeId) {
        return null;
      }

      return "";
    },

    isDataTypeValid() {
      if (this.invalidDataTypeFeedback === null) {
        return null;
      }
      return this.invalidDataTypeFeedback === "";
    },

    isFormValid() {
      if (!this.isValueValid) {
        return false;
      }

      if (!this.localObservation.dataTypeId && !this.isDataTypeValid) {
        return false;
      }

      return true;
    },
  },
};
</script>

<style scoped>
select {
  text-transform: capitalize;
}
</style>
