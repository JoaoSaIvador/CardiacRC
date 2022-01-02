<template>
  <div class="h-100">
    <UserTable
      v-if="qualities"
      :items="qualities"
      :fields="fields"
      sortBy="id"
      group="qualities"
      @delete="deleteQuality"
    />
    <LoadingPage v-else />
  </div>
</template>

<script>
import UserTable from "../../../../components/UserTable.vue";

export default {
  middleware: "admin",
  components: {
    UserTable,
  },
  data() {
    return {
      fields: [
        { key: "id", sortable: true },
        { key: "name", sortable: true },
        { key: "min", sortable: true },
        { key: "max", sortable: true },
        { key: "actions", sortable: false },
      ],
      qualities: null,
      id: this.$route.params.id,
    };
  },
  created() {
    this.$axios.$get(`/api/dataTypes/${this.id}`).then((dataType) => {
      this.qualities = dataType.qualitativeDataTypeDTOs;
    });
  },
  methods: {
    deleteQuality(id) {
      this.$axios
        .$delete(`/api/dataTypes/${this.id}/qualitatives/${id}`)
        .then((response) => {
          this.$toast.success(response).goAway(3000);
          window.location.reload();
        })
        .catch((error) => {
          this.$toast.error(error.response.data).goAway(3000);
        });
    },
  },
};
</script>
