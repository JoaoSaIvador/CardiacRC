<template>
  <section class="vh-100 bg-dark">
    <div class="container py-5 h-100">
      <div class="row d-flex justify-content-center align-items-center h-100">
        <div class="col-12 col-md-8 col-lg-6 col-xl-5">
          <div class="card shadow-2-strong" style="border-radius: 1rem">
            <div class="card-body p-5 text-center">
              <h3 class="mb-5">Sign in</h3>

              <b-form @submit.prevent="onSubmit" @reset="onReset">
                <b-form-group class="form-outline mb-4">
                  <b-input
                    name="username"
                    id="typeUsername"
                    class="form-control form-control-lg"
                    placeholder="Your username"
                    v-model.trim="username"
                    required
                  />
                  <label class="form-label" for="typeUsername">Username</label>
                </b-form-group>

                <b-form-group class="form-outline mb-4">
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

                <button class="btn btn-primary btn-lg btn-block" type="submit">
                  Login
                </button>
              </b-form>
            </div>
          </div>
        </div>
      </div>
    </div>
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
