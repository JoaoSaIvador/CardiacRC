<template>
  <div class="admin-counter">
    <div
      class="fa-2x text-white admin-counter-icon"
      :style="`background-color: ${counter.color}`"
    >
      <fa :icon="['fas', counter.icon]" />
    </div>
    <div class="admin-counter-title" :style="`border-color: ${counter.color}`">
      <h5>Total<br />{{ counter.title }}</h5>
    </div>
    <div class="admin-counter-total">
      <h2>{{ counter.total }}</h2>
    </div>
  </div>
</template>

<script>
export default {
  name: "AdminCounter",
  props: {
    counter: Object,
  },
};
</script>

<style scoped>
.admin-counter {
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  margin: 30px 20px 30px 20px;
}

.admin-counter-icon {
  min-width: 60px;
  min-height: 60px;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-right: 10px;
  padding: 10px;
  border-radius: 100%;
}

.admin-counter-total {
  font-weight: bold;
  margin: 0 0 -10px 5px;
}

.admin-counter-title {
  border-bottom: 4px solid;
  margin: 0 5px 0 0;
}
</style>
