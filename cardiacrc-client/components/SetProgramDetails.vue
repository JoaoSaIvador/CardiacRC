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
              v-model.trim="localProgram.duration"
              :state="isDurationValid"
            ></b-input>
          </b-form-group>
        </div>
        <div v-if="mode == 'create'" class="main-input">
          <b-form-group
            label="Patient:"
            label-for="patient"
            :invalid-feedback="invalidPatientFeedback"
            :state="isPatientValid"
          >
            <b-select
              id="patient"
              v-model="localProgram.patientUsername"
              :options="professionalPatients"
              required
              value-field="username"
              text-field="name"
              :state="isPatientValid"
            >
              <template v-slot:first>
                <option :value="null" disabled>-- Select a Patient --</option>
              </template>
            </b-select>
          </b-form-group>
        </div>
        <div v-if="mode == 'create'" class="main-input">
          <b-form-group label="Prescriptions:" label-for="prescription">
            <div class="d-flex flex-row">
              <b-select
                id="prescription"
                v-model="prescriptionChoice"
                :options="prescriptions"
                required
                value-field="id"
                text-field="name"
              >
                <template v-slot:first>
                  <option :value="null" disabled>
                    -- Select a Prescription --
                  </option>
                </template>
              </b-select>
              <b-button variant="dark" class="ml-2" @click="addPrescription">
                Add
              </b-button>
            </div>
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
            @click.prevent="$emit('submit', localProgram)"
          >
            Create
          </b-button>
          <b-button
            v-else
            class="main-button"
            variant="dark"
            :disabled="!isFormValid"
            @click.prevent="$emit('submit', localProgram)"
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
  name: "SetProgramDetails",
  props: {
    program: Object,
    mode: String,
    group: String,
    professionalPatients: Array,
    prescriptions: Array,
  },
  data() {
    return {
      errorMsg: false,
      prescriptionChoice: null,
      localProgram: JSON.parse(JSON.stringify(this.program)),
    };
  },
  computed: {
    isCreate() {
      return this.mode == "create";
    },

    invalidDurationFeedback() {
      if (!this.localProgram.duration) {
        return null;
      }

      if (this.localProgram.duration < 0) {
        return "The name must be a positive number.";
      }
      return "";
    },

    isDurationValid() {
      if (this.invalidDurationFeedback === null) {
        return null;
      }
      return this.invalidDurationFeedback === "";
    },

    invalidPatientFeedback() {
      if (!this.localProgram.patientUsername) {
        return null;
      }

      return "";
    },

    isPatientValid() {
      if (this.invalidPatientFeedback === null) {
        return null;
      }
      return this.invalidPatientFeedback === "";
    },

    isFormValid() {
      if (!this.isDurationValid) {
        return false;
      }

      if (!this.isPatientValid) {
        return false;
      }

      return true;
    },
  },
  methods: {
    addPrescription() {
      this.localProgram.prescriptionIds.push(this.prescriptionChoice);
    },
  },
};
</script>
