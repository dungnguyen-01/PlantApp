import axios from 'axios'
const api = process.env.VUE_APP_MODE + '/image/'
import authHeader from '../services/auth-header'
class ImageRepository {
  createImage(image) {
    return axios.post(api, image, { headers: authHeader() })
  }
  deleteImageById(id) {
    return axios.delete(api + id, { headers: authHeader() })
  }
  deleteImageByPlantId(plantId) {
    return axios.delete(api + 'plant/' + plantId, { headers: authHeader() })
  }
}
export default new ImageRepository()
