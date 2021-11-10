<template>
  <div class="main-div">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.2.0/css/all.css" integrity="sha384-hWVjflwFxL6sNzntih27bfxkr27PmbbK/iSvJ+a4+0owXq79v+lsFkW54bOGbiDQ" crossorigin="anonymous">
    <Topbar/>
      <b-container>
        <h1>Administrator List:</h1>
        <b-table striped over outlined :items="administrators" :fields="fields" class="mt-3" >
          <template v-slot:cell(actions)="row">
            <nuxt-link class="btn btn-primary btn-xs" :to="`/administrators/${row.item.username}/details`">
                <i class="fas fa-clipboard-list"></i>
            </nuxt-link>
            <nuxt-link class="btn btn-secondary btn-xs" :to="`/administrators/${row.item.username}/update`">
                <i class="fas fa-pen"></i>
            </nuxt-link>
            <button class="btn btn-danger btn-xs"  @click.prevent="deleteAdministrator(row.item.username)">
                <i class="fas fa-trash"></i>
            </button>
          </template>
      </b-table>
      </b-container>
      <b-container>
          <nuxt-link to="/" class="btn btn-primary" >Back</nuxt-link>
          <nuxt-link to="/administrators/create" class="btn btn-secondary">Create a New Administrator</nuxt-link>
      </b-container>
  </div>
</template>

<script>
export default {
  data () {
    return {
      fields: ['username', 'name', 'email', 'actions'],
      administrators: []
    }
  },
  created () {
    this.$axios.$get('/api/administrators').then((administrators) => { this.administrators = administrators })
  } ,
  methods: {
    deleteAdministrator(username) {
        this.$axios.$delete(`/api/administrators/${username}`)
        .then(() => {
          window.location.reload();
        })
    }
  }
}
</script>

<style scoped>

</style>
