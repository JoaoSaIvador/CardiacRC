<template>
  <SetDataTypeDetails
    v-if="dataType"
    :dataType="dataType"
    @submit="updateDataType"
    group="dataType"
    mode="update"
  />
</template>

<script>
export default {
  middleware: "admin",
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
        .then(() => {
          this.$router.push("/dataTypes");
        })
        .catch((error) => {
          //this.errorMsg = error.response.data;
          //Notification
        });
    },
  },
};
</script>
