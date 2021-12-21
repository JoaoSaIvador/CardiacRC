<template>
  <nuxt-link :to="`${card.path}`" class="admin-card-link p-4">
    <div class="admin-card card bg-dark text-white text-center p-3">
      <h5>{{ card.title }}</h5>
    </div>
  </nuxt-link>
</template>

<script>
export default {
  name: "AdminCard",
  props: {
    card: Object,
  },
};
</script>

<style scoped>
.admin-card {
  height: 150px;
  width: 15rem;
  align-items: center;
  justify-content: center;
  transition: all 0.3s ease-in-out;
  margin: 0px;
}

.admin-card:hover {
  box-shadow: rgba(0, 0, 0, 0.25) 0px 14px 28px,
    rgba(0, 0, 0, 0.22) 0px 10px 10px;
  background-color: #252727 !important;
}

.admin-card-link {
  text-decoration: none;
}
</style>
