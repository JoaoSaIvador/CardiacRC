export const goToDashboard = function (user, router) {
  if (user.groups.includes("Administrator")) {
    router.push("/administrators/dashboard");
  } else if (user.groups.includes("Patient")) {
    router.push("/patients/" + user.sub + "/details");
  } else if (user.groups.includes("Professional")) {
    router.push("/professionals/" + user.sub + "/details");
  } else {
    router.push("/auth/login");
  }
};

export const returnDashboardPath = function (user) {
  if (user.groups.includes("Administrator")) {
    return "/administrators/dashboard";
  } else if (user.groups.includes("Patient")) {
    return "/patients/" + user.sub + "/details";
  } else if (user.groups.includes("Professional")) {
    return "/professionals/" + user.sub + "/details";
  } else {
    return "/auth/login";
  }
};
