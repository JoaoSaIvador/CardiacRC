<template>
  <div class="main-div">
    <Topbar />
    <b-container class="admin-dashboard">
      <div class="row admin-counters bg-light">
        <AdminCounter
          v-for="counter in counters"
          :key="counter.title"
          :counter="counter"
        />
      </div>
      <div class="admin-deck card-deck">
        <AdminCard v-for="card in cards" :key="card.title" :card="card" />
      </div>
    </b-container>
  </div>
</template>

<script>
import AdminCard from "../../components/AdminCard.vue";
import AdminCounter from "../../components/AdminCounter.vue";

export default {
  components: {
    AdminCard,
    AdminCounter,
  },
  data() {
    return {
      cards: [
        {
          title: "Account Details",
          path: `/administrators/${this.$auth.user.sub}/details`,
        },
        { title: "Manage Administrators", path: `/administrators` },
        { title: "Manage Professionals", path: `/professionals` },
        { title: "Manage Patients", path: `/patients` },
        { title: "Manage Quantitative Data Types", path: "#" },
        { title: "Manage Qualitative Data Types", path: "#" },
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

<style scoped>
@media screen and (max-width: 991px) {
  .admin-dashboard {
    margin: 50px auto 50px auto;
  }
}

@media screen and (min-width: 992px) {
  .navbar {
    margin-bottom: 0;
  }
}

.main-div {
  height: 100%;
}

.admin-dashboard {
  height: calc(100% - 56px);
  display: flex;
  flex-direction: column;
  justify-content: center;
  flex-wrap: wrap;
}

.admin-deck,
.admin-counters {
  justify-content: center;
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
}

.admin-counters {
  margin-bottom: 50px;
  padding-top: 30px;
  border-radius: 20px;
  box-shadow: rgba(0, 0, 0, 0.05) 0px 14px 28px,
    rgba(0, 0, 0, 0.05) 0px 10px 10px;
}
</style>
