<template>
  <div class="col-lg-3 admin-counter">
    <div
      class="fa-2x text-white admin-counter-icon"
      :style="`background-color: ${counter.color}`"
    >
      <fa :icon="['fas', counter.icon]" />
    </div>
    <div class="mx-2">
      <h5 class="mb-3">Total<br />{{ counter.title }}</h5>
      <div
        class="progress-bar"
        :style="`width: 100%; height: 4px; background-color: ${counter.color}`"
      ></div>
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
  margin: 0 0 30px 0;
}

.admin-counter-icon {
  width: 60px;
  height: 60px;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-right: 10px;
  padding: 10px;
  border-radius: 100%;
}

.admin-counter-total {
  font-weight: bold;
  margin-left: 5px;
}
</style>
