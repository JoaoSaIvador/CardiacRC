import * as auxiliary from "../utils/auxiliary.js";

export default function ({ redirect, $auth, params }) {
  return $auth.user.groups.includes("Professional") ||
    ($auth.user.groups.includes("Patient") &&
      params.username === $auth.user.sub)
    ? true
    : redirect(auxiliary.returnDashboardPath($auth.user));
}
