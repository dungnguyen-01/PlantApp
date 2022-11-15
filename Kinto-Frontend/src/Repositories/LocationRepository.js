import axios from 'axios'
const api = process.env.VUE_APP_MODE + '/location/'
import authHeader from '../services/auth-header'
class LocationRepository {
  getAllLocations(location) {
    return axios.get(api + 'list' + location, { headers: authHeader() })
  }
  getLocations(page) {
    return axios.get(api + page, { headers: authHeader() })
  }
  getLocationById(id) {
    return axios.get(api + id, { headers: authHeader() })
  }
  createLocation(location) {
    return axios.post(api, location, { headers: authHeader() })
  }
  updateLocation(id, location) {
    return axios.put(api + id, location, { headers: authHeader() })
  }
  deleteLocationById(id) {
    return axios.get(api + 'delete/' + id, { headers: authHeader() })
  }
}
export default new LocationRepository()
