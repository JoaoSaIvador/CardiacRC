export const isPasswordValid = function (password) {
  if (invalidPasswordFeedback(password) === null) {
    return null;
  }
  return invalidPasswordFeedback(password) === "";
};

export const invalidPasswordFeedback = function (password) {
  if (!password) {
    return null;
  }
  let passwordLen = password.length;
  if (passwordLen < 6 || passwordLen > 20) {
    return "The password must be between [6, 20] characters.";
  }
  return "";
};
