export const goToDashboard = function () {
  if (this.$auth.user.groups.includes("Administrator")) {
    this.$router.push("/administrators/dashboard");
  } else if (this.$auth.user.groups.includes("Patient")) {
    this.$router.push("/patients/" + this.$auth.user.sub + "/details");
  } else if (this.$auth.user.groups.includes("Professional")) {
    this.$router.push("/professionals/" + this.$auth.user.sub + "/details");
  }
};
