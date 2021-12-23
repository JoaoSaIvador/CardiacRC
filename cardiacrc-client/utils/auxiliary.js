export const goToDashboard = function (user, router) {
  if (user.groups.includes("Administrator")) {
    router.push("/administrators/dashboard");
  } else if (user.groups.includes("Patient")) {
    router.push("/patients/" + user.sub + "/details");
  } else if (user.groups.includes("Professional")) {
    router.push("/professionals/" + user.sub + "/details");
  }
};
