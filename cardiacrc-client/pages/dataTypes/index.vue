<template>
  <UserTable
    :items="dataTypes"
    :fields="fields"
    sortBy="id"
    group="dataTypes"
    @delete="deleteDataType"
  />
</template>

<script>
export default {
  middleware: "admin",
  data() {
    return {
      fields: [
        { key: "id", sortable: true },
        { key: "name", sortable: true },
        { key: "unit", sortable: true },
        { key: "min", sortable: true },
        { key: "max", sortable: true },
        { key: "actions", sortable: false },
      ],
      dataTypes: [],
    };
  },
  created() {
    this.$axios.$get("/api/dataTypes").then((dataTypes) => {
      this.dataTypes = dataTypes;
    });
  },
  methods: {
    deleteDataType(id) {
      this.$axios.$delete(`/api/dataTypes/${id}`).then(() => {
        window.location.reload();
      });
    },
  },
};
</script>
