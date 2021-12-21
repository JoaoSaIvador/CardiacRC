<template>
  <div class="main-div">
    <Topbar />
    <b-container class="admin-dashboard">
      <div class="admin-deck card-deck">
        <AdminCard
          v-for="card in cardsFirstLine"
          :key="card.title"
          :card="card"
        />
      </div>
      <div class="admin-deck card-deck">
        <AdminCard
          v-for="card in cardsSecondLine"
          :key="card.title"
          :card="card"
        />
      </div>
    </b-container>
  </div>
</template>

<script>
import AdminCard from "../../components/AdminCard.vue";

export default {
  components: {
    AdminCard,
  },
  data() {
    return {
      cardsFirstLine: [
        {
          title: "Account Details",
          path: `/administrators/${this.$auth.user.sub}/details`,
        },
        { title: "Manage Administrators", path: `/administrators` },
        { title: "Manage Professionals", path: "#" },
      ],
      cardsSecondLine: [
        { title: "Manage Patients", path: "#" },
        { title: "Manage Quantitative Data Types", path: "#" },
        { title: "Manage Qualitative Data Types", path: "#" },
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

.admin-deck {
  justify-content: center;
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
}
</style>
