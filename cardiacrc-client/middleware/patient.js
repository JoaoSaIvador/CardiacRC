import * as auxiliary from "../utils/auxiliary.js";

export default function ({ redirect, $auth, params }) {
  return $auth.user.groups.includes("Patient") ||
    $auth.user.groups.includes("Professional")
    ? true
    : redirect(auxiliary.returnDashboardPath($auth.user));
}
