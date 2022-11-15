import axios from 'axios'
import authHeader from '../services/auth-header'
const api = process.env.VUE_APP_MODE + '/farm-diary/'
class FarmDiaryRepository {
  getFarmDiaryById(id) {
    return axios.get(api + id, { headers: authHeader() })
  }
  createFarmDiary(farmDiary) {
    return axios.post(api, farmDiary, { headers: authHeader() })
  }
  updateFarmDiary(id, farmDiary) {
    return axios.put(api + id, farmDiary, { headers: authHeader() })
  }
  deleteFarmDiaryById(id) {
    return axios.delete(api + id, { headers: authHeader() })
  }
  deleteFarmDiaryByPlantId(plantId) {
    return axios.delete(api + 'plant/' + plantId, { headers: authHeader() })
  }
}
export default new FarmDiaryRepository()
