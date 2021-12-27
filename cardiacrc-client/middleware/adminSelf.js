import * as auxiliary from "../utils/auxiliary.js";

export default function ({ redirect, $auth, params }) {
  return $auth.user.groups.includes("Administrator") &&
    params.username === $auth.user.sub
    ? true
    : redirect(auxiliary.returnDashboardPath($auth.user));
}
