import axios from 'axios'
const api = process.env.VUE_APP_MODE + '/role/'
import authHeader from '../services/auth-header'
class RoleRepository {
  getRoles() {
    return axios.get(api + 'list', { headers: authHeader() })
  }
  getRoleById(id) {
    return axios.get(api + id, { headers: authHeader() })
  }
}
export default new RoleRepository()
