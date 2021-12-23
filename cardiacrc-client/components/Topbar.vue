<template>
  <b-navbar toggleable="lg" type="light" variant="light" class="customNavbar">
    <b-navbar-brand href="#">Cardiac Rehabilitation Centre</b-navbar-brand>
    <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

    <b-collapse id="nav-collapse" is-nav>
      <b-navbar-nav>
        <b-nav-item to="/administrators/dashboard">Home</b-nav-item>
      </b-navbar-nav>
    </b-collapse>
  </b-navbar>
</template>

<script>
export default {};
</script>

<style scoped>
.customNavbar {
  box-shadow: rgba(0, 0, 0, 0.02) 0px 14px 28px,
    rgba(0, 0, 0, 0.02) 0px 10px 10px;
}
</style>
