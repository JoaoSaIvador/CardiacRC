<template>
  <div :class="`p-3 admin-counter ${counter.color} text-center`">
    <h1 class="text-white">{{ counter.total }}</h1>
    <h6 class="text-white">{{ counter.title }}</h6>
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
  border-radius: 30px;
  height: 100px;
  width: 13rem;
  margin: 0 10px 30px 10px;
}

h1 {
  margin: 0;
}
</style>
