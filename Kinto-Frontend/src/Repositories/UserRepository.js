import axios from 'axios'
const api = process.env.VUE_APP_MODE + '/user/'
import authHeader from '../services/auth-header'
class UserRepository {
  getAllUsers(user) {
    return axios.get(api + 'list' + user, { headers: authHeader() })
  }
  getUsers(page) {
    return axios.get(api + page, { headers: authHeader() })
  }
  getUserById(id) {
    return axios.get(api + id, { headers: authHeader() })
  }
  getUserByIdAll(id) {
    return axios.get(api + 'all/' + id, { headers: authHeader() })
  }
  createUser(user) {
    return axios.post(api, user, { headers: authHeader() })
  }
  updateUser(id, user) {
    return axios.put(api + id, user, { headers: authHeader() })
  }
  deleteUserById(id) {
    return axios.get(api + 'delete/' + id, { headers: authHeader() })
  }
  loginWithEmail(user) {
    return axios.post(api + 'login', user, { headers: authHeader() })
  }
  register(user) {
    return axios.post(api + 'register', user, { headers: authHeader() })
  }

  forgotPassword(user) {
    return axios.post(api + 'forgot-password', user, { headers: authHeader() })
  }

  resetPassword(user) {
    return axios.post(api + 'reset-password', user, { headers: authHeader() })
  }

  loginWithGoogle(user) {
    return axios.post(api + 'google-login', user, { headers: authHeader() })
  }

  changePassword(user) {
    return axios.post(api + 'change-password', user, { headers: authHeader() })
  }

  updateORG(user) {
    return axios.put(api + 'update-org', user, { headers: authHeader() })
  }
}
export default new UserRepository()
