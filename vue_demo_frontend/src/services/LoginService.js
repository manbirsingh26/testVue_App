import http from "../http-common.js";

class LoginService {
  login(credentials) {
    return http.post(`/login`, credentials);
  }

  remove(credentials) {
    return http.post(`/remove`, credentials);
  }

  register(studentData) {
    return http.post(`/register`, studentData);
  }
}

export default new LoginService();