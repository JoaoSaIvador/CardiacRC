<template>
  <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
      <nuxt-link to="/" class="navbar-brand mb-0 h1"
        >Cardiac Rehabilitation Centre</nuxt-link
      >
      <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav">
          <li class="nav-item">
            <nuxt-link to="/patients" class="nav-link active"
              >Patients</nuxt-link
            >
          </li>
          <li class="nav-item">
            <nuxt-link to="/professionals" class="nav-link active"
              >Professionals</nuxt-link
            >
          </li>
          <li class="nav-item">
            <nuxt-link to="/administrators" class="nav-link active"
              >Administrators</nuxt-link
            >
          </li>
        </ul>
      </div>
    </div>
  </nav>
</template>

<script>
export default {};
</script>

<style>
.navbar {
  margin-bottom: 50px;
}
</style>
