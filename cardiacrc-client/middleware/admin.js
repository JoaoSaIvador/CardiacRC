import * as auxiliary from "../utils/auxiliary.js";

export default function ({ redirect, $auth }) {
  return $auth.user.groups.includes("Administrator")
    ? true
    : redirect(auxiliary.returnDashboardPath($auth.user));
}
