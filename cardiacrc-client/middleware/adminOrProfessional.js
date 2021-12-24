import * as auxiliary from "../utils/auxiliary.js";

export default function ({ redirect, $auth }) {
  return $auth.user.groups.includes("Administrator") ||
    $auth.user.groups.includes("Professional")
    ? true
    : redirect(auxiliary.returnDashboardPath($auth.user));
}
