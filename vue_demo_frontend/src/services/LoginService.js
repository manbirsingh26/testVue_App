import http from "../http-common.js";

class LoginService {
  login(credentials) {
    return http.post(`/login`, credentials);
  }

  register(studentData) {
    return http.post(`/register`, studentData);
  }
}

export default new LoginService();