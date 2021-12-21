<template>
  <div class="main-div">
    <link
      rel="stylesheet"
      href="https://use.fontawesome.com/releases/v5.2.0/css/all.css"
      integrity="sha384-hWVjflwFxL6sNzntih27bfxkr27PmbbK/iSvJ+a4+0owXq79v+lsFkW54bOGbiDQ"
      crossorigin="anonymous"
    />
    <Topbar />
    <b-container class="admin-main-div">
      <div class="row admin-deck card-deck">
        <AdminCard
          v-for="card in cardsFirstLine"
          :key="card.title"
          :card="card"
        />
      </div>
      <div class="row admin-deck card-deck">
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
      fields: ["username", "name", "email", "actions"],
      administrators: [],
      cardsFirstLine: [
        {
          title: "Account Details",
          path: `/administrators/${this.$auth.user.sub}/details`,
        },
        { title: "Manage Administrators", path: "#" },
        { title: "Manage Professionals", path: "#" },
      ],
      cardsSecondLine: [
        { title: "Manage Patients", path: "#" },
        { title: "Manage Quantitative Data Types", path: "#" },
        { title: "Manage Qualitative Data Types", path: "#" },
      ],
    };
  },
  methods: {},
};
</script>

<style scoped>
.admin-deck {
  align-items: center;
  justify-content: center;
  margin-top: 30px;
}
</style>
