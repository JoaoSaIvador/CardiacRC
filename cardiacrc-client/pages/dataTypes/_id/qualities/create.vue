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
