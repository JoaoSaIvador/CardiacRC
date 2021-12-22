<template>
  <section class="vh-100 primary-div">
    <b-container class="secondary-div">
      <b-container class="page-content bg-light text-center">
        <h2 class="login-title">Sign in</h2>
        <b-form @submit.prevent="onSubmit" @reset="onReset">
          <b-form-group class="form-outline">
            <b-input
              name="username"
              id="typeUsername"
              class="form-control form-control-lg"
              placeholder="Your username"
              v-model.trim="username"
              required
            />
            <label
              class="form-label"
              for="typeUsername"
              style="text-align: center"
              >Username</label
            >
          </b-form-group>

          <b-form-group class="form-outline">
            <b-input
              name="password"
              type="password"
              id="typePassword"
              class="form-control form-control-lg"
              placeholder="Your password"
              v-model="password"
              required
            />
            <label class="form-label" for="typePassword">Password</label>
          </b-form-group>

          <button class="btn btn-dark btn-lg btn-block" type="submit">
            Login
          </button>
        </b-form>
      </b-container>
    </b-container>
  </section>
</template>
<script>
export default {
  auth: false,
  data() {
    return {
      username: null,
      password: null,
    };
  },
  methods: {
    onSubmit() {
      let promise = this.$auth.loginWith("local", {
        data: {
          username: this.username,
          password: this.password,
        },
      });
      promise.then(() => {
        this.$toast.success("You are logged in!").goAway(3000);
        // check if the user $auth.user object is set
        console.log(this.$auth.person);
        // TODO redirect based on the user role
        if (this.$auth.user.groups.includes("Administrator")) {
          this.$router.push("/administrators/dashboard");
        } else if (this.$auth.user.groups.includes("Patient")) {
          this.$router.push("/patients/" + this.username + "/details");
        } else if (this.$auth.user.groups.includes("Professional")) {
          this.$router.push("/professionals/" + this.username + "/details");
        }
      });
      promise.catch(() => {
        this.$toast
          .error("Sorry, you cant login. Ensure your credentials are correct")
          .goAway(3000);
      });
    },
    onReset() {
      this.username = null;
      this.password = null;
    },
  },
};
</script>

<style scoped>
@media screen and (max-width: 650px) {
  .page-content {
    background-color: rgb(238, 238, 238) !important;
    box-shadow: none;
  }

  .login-title {
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

  .login-title {
    margin: 0px 0 50px 0;
  }
}

.primary-div {
  height: 100%;
  background-color: rgb(238, 238, 238);
}

.secondary-div {
  height: calc(100% - 56px);
  display: flex;
  align-items: center;
}

.page-content {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}
</style>
