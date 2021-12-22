<template>
  <nuxt-link :to="`${card.path}`" class="admin-card-link p-4">
    <div class="admin-card card bg-light text-dark text-center p-3">
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
  border: none;
  border-radius: 20px;
  align-items: center;
  justify-content: center;
  transition: all 0.3s ease-in-out;
  margin: 0px;
  box-shadow: rgba(0, 0, 0, 0.05) 0px 14px 28px,
    rgba(0, 0, 0, 0.05) 0px 10px 10px;
}

.admin-card:hover {
  box-shadow: rgba(0, 0, 0, 0.3) 0px 14px 28px, rgba(0, 0, 0, 0.3) 0px 10px 10px;
  background-color: #252727 !important;
  color: white !important;
}

.admin-card-link {
  text-decoration: none;
}
</style>
