<template>
  <div class="h-100">
    <UserTable
      v-if="dataTypes"
      :items="dataTypes"
      :fields="fields"
      sortBy="id"
      group="dataTypes"
      @delete="deleteDataType"
      @export="exportDataTypes"
    />
    <LoadingPage v-else />
  </div>
</template>

<script>
export default {
  middleware: "admin",
  head() {
    return {
      title: "Manage Data Types",
    };
  },
  data() {
    return {
      fields: [
        { key: "id", sortable: true },
        { key: "name", sortable: true },
        { key: "unit", sortable: true },
        { key: "min", sortable: true },
        { key: "max", sortable: true },
        { key: "deleted", sortable: true },
        { key: "actions", sortable: false },
      ],
      dataTypes: null,
    };
  },
  created() {
    this.$axios.$get("/api/dataTypes").then((dataTypes) => {
      this.dataTypes = dataTypes;
    });
  },
  methods: {
    deleteDataType(id) {
      this.$axios
        .$delete(`/api/dataTypes/${id}`)
        .then((response) => {
          this.$toast.success(response).goAway(3000);
          window.location.reload();
        })
        .catch((error) => {
          this.$toast.error(error.response.data).goAway(3000);
        });
    },
    exportDataTypes() {
      this.$axios
        .$get("/api/dataTypes/export", { responseType: "arraybuffer" })
        .then((file) => {
          const url = window.URL.createObjectURL(
            new Blob([file], {
              type: "application/vnd.ms-excel",
            })
          );
          const link = document.createElement("a");
          link.href = url;
          link.setAttribute("download", "dataTypes.xlsx");
          document.body.appendChild(link);
          link.click();
        });
    },
  },
};
</script>
