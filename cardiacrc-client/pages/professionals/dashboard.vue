<template>
  <b-container class="dashboard">
    <div class="dashboard-counters bg-light">
      <DashboardCounter
        v-for="counter in counters"
        :key="counter.title"
        :counter="counter"
      />
    </div>
    <div class="dashboard-deck card-deck">
      <DashboardCard v-for="card in cards1" :key="card.title" :card="card" />
    </div>
    <div class="dashboard-deck card-deck">
      <DashboardCard v-for="card in cards2" :key="card.title" :card="card" />
    </div>
  </b-container>
</template>

<script>
export default {
  middleware: "professional",
  data() {
    return {
      cards1: [
        {
          title: "Account Details",
          path: `/professionals/${this.$auth.user.sub}/details`,
        },
        { title: "Manage Patients", path: `/patients` },
      ],
      cards2: [
        { title: "Manage Prescriptions", path: `/prescriptions` },
        { title: "Manage PRCs", path: `/professionals/dashboard` },
      ],
      counters: [
        {
          title: "Patients",
          total: "10",
          color: "#796aee",
          icon: "user",
        },
        {
          title: "Prescriptions",
          total: "11",
          color: "#ff7676",
          icon: "file",
        },
        {
          title: "PRCs",
          total: "12",
          color: "#54e69d",
          icon: "copy",
        },
      ],
    };
  },
};
</script>
