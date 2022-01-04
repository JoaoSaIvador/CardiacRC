<template>
  <SetDataTypeDetails
    :dataType="dataType"
    @submit="createDataType"
    group="dataType"
    mode="create"
  />
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
      dataType: {
        name: null,
        unit: null,
        min: null,
        max: null,
      },
    };
  },
  methods: {
    createDataType(dataType) {
      this.$axios
        .$post("/api/dataTypes", dataType)
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
