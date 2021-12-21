<template>
  <nuxt-link
    :to="`${card.path}`"
    class="admin-card-link admin-card card bg-dark text-white text-center p-3"
  >
    <div class="">
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
  max-width: 13rem;
  align-items: center;
  justify-content: center;
  transition: all 0.3s ease-in-out;
}

.admin-card:hover {
  box-shadow: 0 10px 20px 10px rgba(black, 0.2);
}

.admin-card-link {
  padding: 0px;
  text-decoration: none;
}
</style>
