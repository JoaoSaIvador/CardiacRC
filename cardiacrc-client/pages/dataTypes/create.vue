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
        .then(() => {
          this.$router.back();
        })
        .catch((error) => {
          //this.errorMsg = error.response.data;
          //Notification
        });
    },
  },
};
</script>
