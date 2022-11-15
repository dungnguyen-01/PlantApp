import axios from 'axios'
const api = process.env.VUE_APP_MODE + '/plant/'
import authHeader from '../services/auth-header'
class PlantRepository {
  getPlants(page) {
    return axios.get(api + page, { headers: authHeader() })
  }
  getPlantById(id) {
    return axios.get(api + id, { headers: authHeader() })
  }
  createPlant(plant) {
    return axios.post(api, plant, { headers: authHeader() })
  }
  updatePlant(id, plant) {
    return axios.put(api + id, plant, { headers: authHeader() })
  }
  deletePlantById(id) {
    return axios.delete(api + id, { headers: authHeader() })
  }
}
export default new PlantRepository()
