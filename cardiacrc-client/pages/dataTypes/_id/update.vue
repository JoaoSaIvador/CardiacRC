<template>
  <div class="h-100">
    <SetDataTypeDetails
      v-if="dataType"
      :dataType="dataType"
      @submit="updateDataType"
      group="dataType"
      mode="update"
    />
    <LoadingPage v-else />
  </div>
</template>

<script>
export default {
  middleware: "admin",
  head() {
    return {
      title: "Data Type Details",
    };
  },
  data() {
    return {
      dataType: null,
      id: this.$route.params.id,
    };
  },
  created() {
    this.$axios.$get(`/api/dataTypes/${this.id}`).then((dataType) => {
      this.dataType = dataType || {};
    });
  },
  methods: {
    updateDataType(dataType) {
      if (this.dataType.name == dataType.name) {
        dataType.name = null;
      }
      if (this.dataType.unit == dataType.unit) {
        dataType.unit = null;
      }

      this.$axios
        .$put(`/api/dataTypes/${this.id}`, dataType)
        .then((response) => {
          this.$toast.success(response).goAway(3000);
          this.$router.back();
        })
        .catch((error) => {
          this.$toast.error(error.response.data).goAway(3000);
        });
    },
  },
};
</script>
