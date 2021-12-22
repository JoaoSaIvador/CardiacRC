<template>
  <Topbar />
</template>

<script>
export default {};
</script>

<style>
html,
body,
#__nuxt,
#__layout {
  background-color: rgb(238, 238, 238);
}

@media screen and (min-width: 992px) {
  html,
  body,
  #__nuxt,
  #__layout {
    height: 100%;
    width: 100%;
  }

  .navbar {
    margin-bottom: 0;
  }
}

@media screen and (max-width: 650px) {
  .page-content,
  .page-content-xl {
    background-color: rgb(238, 238, 238) !important;
    box-shadow: none;
  }

  .details-title {
    margin: 30px 0 30px 0;
  }
}

@media screen and (min-width: 651px) {
  .page-content {
    width: 500px;
    height: 600px;
    border-radius: 20px;
    box-shadow: rgba(0, 0, 0, 0.05) 0px 14px 28px,
      rgba(0, 0, 0, 0.05) 0px 10px 10px;
  }

  .page-content-xl {
    width: 1000px;
    height: 700px;
    border-radius: 20px;
    box-shadow: rgba(0, 0, 0, 0.05) 0px 14px 28px,
      rgba(0, 0, 0, 0.05) 0px 10px 10px;
    padding: 0 60px 0 60px;
  }

  .details-title {
    margin: 0px 0 50px 0;
  }
}

.primary-div {
  height: 100%;
}

.secondary-div {
  height: calc(100% - 56px);
  display: flex;
  align-items: center;
}

.page-content,
.page-content-xl {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.page-content {
  justify-content: center;
}

.page-content-xl {
  justify-content: flex-start;
}

.details-input {
  width: 300px;
}

.button-group {
  display: flex;
  flex-direction: row;
  justify-content: center;
}

.details-button {
  margin: 15px 5px 5px 5px;
}

.page-link {
  color: #252727;
}

.page-item.active .page-link {
  color: #ffffff !important;
  background-color: #252727;
  border: none;
}

.table-settings {
  display: flex;
  flex-direction: row;
  width: 100%;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}

.table-button {
  width: 30px;
  height: 30px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0 2px 0 2px;
}

h1 {
  margin: 30px 0 50px 0;
}
</style>
