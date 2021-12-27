<template>
  <b-container
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
        v-for="counter in counters"
        :key="counter.title"
        :counter="counter"
      />
    </div>
    <div class="d-flex flex-row justify-content-center flex-wrap card-deck">
      <DashboardCard v-for="card in cards" :key="card.title" :card="card" />
    </div>
  </b-container>
</template>

<script>
export default {
  middleware: "admin",
  data() {
    return {
      cards: [
        {
          title: "Account Details",
          path: `/administrators/${this.$auth.user.sub}/details`,
        },
        {
          title: "Update Account Details",
          path: `/administrators/${this.$auth.user.sub}/update`,
        },
        { title: "Manage Administrators", path: `/administrators` },
        { title: "Manage Professionals", path: `/professionals` },
        { title: "Manage Patients", path: `/patients` },
        { title: "Manage Data Types", path: `/dataTypes` },
      ],
      counters: [
        {
          title: "Administrators",
          total: "10",
          color: "#796aee",
          icon: "shield-alt",
        },
        {
          title: "Professionals",
          total: "11",
          color: "#ff7676",
          icon: "user-md",
        },
        {
          title: "Patients",
          total: "12",
          color: "#54e69d",
          icon: "user",
        },
        {
          title: "Data Types",
          total: "13",
          color: "#ffc36d",
          icon: "database",
        },
      ],
    };
  },
};
</script>
