<template>
  <UserTable
    :items="qualities"
    :fields="fields"
    sortBy="id"
    group="qualities"
    @delete="deleteQuality"
  />
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
      qualities: [],
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
        .then(() => {
          window.location.reload();
        });
    },
  },
};
</script>
