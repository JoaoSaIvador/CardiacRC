<template>
  <SetDataTypeDetails
    v-if="dataType"
    :dataType="quality"
    :parentDataType="dataType"
    @submit="createQuality"
    group="quality"
    mode="create"
  />
</template>

<script>
export default {
  middleware: "admin",
  data() {
    return {
      dataType: null,
      quality: {
        name: null,
        min: null,
        max: null,
      },
      id: this.$route.params.id,
    };
  },
  created() {
    this.$axios
      .$get(`/api/dataTypes/${this.id}`)
      .then((dataType) => (this.dataType = dataType || {}));
  },
  methods: {
    createQuality(quality) {
      this.$axios
        .$post(`/api/dataTypes/${this.id}/qualitatives`, quality)
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
