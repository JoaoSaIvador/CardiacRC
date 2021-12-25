<template>
  <SetDataTypeDetails
    v-if="dataType && quality"
    :dataType="quality"
    :parentDataType="dataType"
    @submit="updateQuality"
    group="quality"
    mode="update"
  />
</template>

<script>
export default {
  middleware: "admin",
  data() {
    return {
      dataType: null,
      quality: null,
      id: this.$route.params.id,
      qualityId: this.$route.params.qualityId,
    };
  },
  created() {
    this.$axios.$get(`/api/dataTypes/${this.id}`).then((dataType) => {
      this.dataType = dataType || {};
      this.quality = dataType.qualitativeDataTypeDTOs.filter(
        (a) => a.id === this.qualityId
      )[0];
    });
  },
  methods: {
    updateQuality(quality) {
      if (this.quality.name == quality.name) {
        quality.name = null;
      }

      this.$axios
        .$put(
          `/api/dataTypes/${this.id}/qualitatives/${this.qualityId}`,
          quality
        )
        .then(() => {
          this.$router.push(`/dataTypes/${this.id}`);
        })
        .catch((error) => {
          //this.errorMsg = error.response.data;
          //Notification
        });
    },
  },
};
</script>
