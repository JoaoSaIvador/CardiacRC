import * as auxiliary from "../utils/auxiliary.js";

export default function ({ redirect, $auth, params }) {
  return ($auth.user.groups.includes("Patient") &&
    params.username === $auth.user.sub) ||
    $auth.user.groups.includes("Professional")
    ? true
    : redirect(auxiliary.returnDashboardPath($auth.user));
}
