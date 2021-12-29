<template>
  <b-container
    v-if="counterData"
    class="
      h-100
      d-flex
      flex-column
      justify-content-center
      align-items-center
      flex-wrap
    "
  >
    <div
      class="
        d-flex
        flex-row
        justify-content-center
        flex-wrap
        align-items-center
        dashboard-counters
        bg-light
      "
    >
      <DashboardCounter
        v-for="(counter, index) in counters"
        :key="counter.title"
        :counter="counter"
        :counterData="counterData[index]"
      />
    </div>
    <div class="d-flex flex-row justify-content-center flex-wrap card-deck">
      <DashboardCard v-for="card in cards1" :key="card.title" :card="card" />
    </div>
    <div class="d-flex flex-row justify-content-center flex-wrap card-deck">
      <DashboardCard v-for="card in cards2" :key="card.title" :card="card" />
    </div>
  </b-container>
</template>

<script>
export default {
  middleware: "professional",
  data() {
    return {
      counterData: null,
      cards1: [
        {
          title: "Account Details",
          path: `/professionals/${this.$auth.user.sub}/details`,
        },
        { title: "Manage Patients", path: `/patients` },
      ],
      cards2: [
        { title: "Manage Prescriptions", path: `/prescriptions` },
        { title: "Manage CRPs", path: `/programs` },
      ],
      counters: [
        {
          title: "Patients",
          color: "#796aee",
          icon: "user",
        },
        {
          title: "Prescriptions",
          color: "#ff7676",
          icon: "file",
        },
        {
          title: "CRPs",
          color: "#54e69d",
          icon: "copy",
        },
      ],
    };
  },
  created() {
    this.$axios
      .$get(`/api/professionals/count`)
      .then((counterData) => (this.counterData = counterData || []));
  },
};
</script>
