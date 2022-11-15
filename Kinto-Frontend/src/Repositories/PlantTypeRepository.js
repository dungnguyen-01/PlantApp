import axios from 'axios'
const api = process.env.VUE_APP_MODE + '/plant-type/'
import authHeader from '../services/auth-header'
class PlantTypeRepository {
  getAllPlantTypes(plantType) {
    return axios.get(api + 'list' + plantType, { headers: authHeader() })
  }
  getPlantTypes(page) {
    return axios.get(api + page, { headers: authHeader() })
  }
  getPlantTypeById(id) {
    return axios.get(api + id, { headers: authHeader() })
  }
  createPlantType(plantType) {
    return axios.post(api, plantType, { headers: authHeader() })
  }
  updatePlantType(id, plantType) {
    return axios.put(api + id, plantType, { headers: authHeader() })
  }
  deletePlantTypeById(id) {
    return axios.get(api + 'delete/' + id, { headers: authHeader() })
  }
}
export default new PlantTypeRepository()
