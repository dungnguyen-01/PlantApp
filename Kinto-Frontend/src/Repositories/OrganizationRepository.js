import axios from 'axios'
const api = process.env.VUE_APP_MODE + '/organization/'
import authHeader from '../services/auth-header'
class OrganizationRepository {
  getAllOrganizations() {
    return axios.get(api + 'list', { headers: authHeader() })
  }
  getAllSelectByUserOrganizations() {
    return axios.get(api + 'list-user', { headers: authHeader() })
  }
  getOrganizations(page) {
    return axios.get(api + page, { headers: authHeader() })
  }
  getOrganizationById(id) {
    return axios.get(api + id, { headers: authHeader() })
  }
  createOrganization(organization) {
    return axios.post(api, organization, { headers: authHeader() })
  }
  updateOrganization(id, organization) {
    return axios.put(api + id, organization, { headers: authHeader() })
  }
  deleteOrganizationById(id) {
    return axios.get(api + 'delete/' + id, { headers: authHeader() })
  }
}
export default new OrganizationRepository()
