<template>
  <CRow>
    <CCol>
      <CCard>
        <CCardHeader> <CIcon icon="cil-drop" /> Plant </CCardHeader>
        <CCardBody>
          <CRow>
            <div class="alert alert-primary text-uppercase text-center">
              <h3>plant information</h3>
            </div>
          </CRow>
          <!-- information -->
          <CRow>
            <h5>1. Information</h5>
            <div class="row">
              <div class="col-md-9">
                <div class="form-control">
                  <ul style="line-height: 50px">
                    <li>Id: {{ plantId }}</li>
                    <li>Code: {{ code }}</li>
                    <li>Culture Medium: {{ cultureMedium }}</li>
                    <li>Plant Type: {{ plantType }}</li>
                    <li>Location: {{ location }}</li>
                    <li>
                      Person In Chage: {{ user.fullname }} |
                      {{ role.roleName }}
                    </li>
                    <li>Organization: {{ organization }}</li>
                    <li>
                      Date create: {{ this.$formatDate.formatDate(createAt) }}
                    </li>
                    <li>
                      Date update: {{ this.$formatDate.formatDate(updateAt) }}
                    </li>
                  </ul>
                </div>
              </div>
              <div class="col-md-3">
                <div class="form-control">
                  <qrcode-vue :value="plantAddress" :size="size" level="H" />
                </div>
              </div>
            </div>
          </CRow>
          <!-- Farm Diary -->
          <h5 class="mt-3">2. Farm Diaries:</h5>
          <form>
            <div class="form-group">
              <div class="border p-2 mt-3">
                <div>
                  <table class="table table-striped">
                    <thead class="table-dark">
                      <tr>
                        <th>No.</th>
                        <th>Farm Diary</th>
                        <th>Date create</th>
                        <th>Date update</th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr v-for="(item, index) in farmDiaries" :key="index">
                        <td>{{ index + 1 }}</td>
                        <td>
                          {{ item.name }}
                        </td>
                        <td>
                          <p class="mb-0">
                            {{ this.$formatDate.formatDate(item.createAt) }}
                          </p>
                        </td>
                        <td>
                          <p class="mb-0">
                            {{ this.$formatDate.formatDate(item.updateAt) }}
                          </p>
                        </td>
                      </tr>
                    </tbody>
                  </table>
                </div>
              </div>
            </div>
          </form>
          <!-- Image -->
          <h5 class="mt-3">3. Image:</h5>
          <div class="row">
            <div
              v-for="(item, index) in imgs"
              :key="index"
              class="col-lg-3 col-md-4 mb-3"
            >
              <div class="card">
                <img
                  :src="item.objectName"
                  style="height: 250px"
                  class="card-img-top img-fluid border border-dark"
                  :alt="item.id"
                  @click="showModal(item.objectName)"
                />
                <div class="card-body">
                  <h5 class="card-title">Info</h5>
                  <div class="card-text">
                    <i>{{ item.name }}</i>
                    <br />
                    Date create:{{ this.$formatDate.formatDate(item.createAt) }}
                  </div>
                  <a
                    href="#"
                    class="btn btn-ghost-info"
                    @click="showModal(item.objectName)"
                    >Zoom</a
                  >
                </div>
              </div>
            </div>
          </div>
        </CCardBody>
        <CCardFooter>
          <div class="text-center">
            <button
              type="button"
              class="btn btn-info rounded-pill mx-2 px-4"
              title="detail"
              @click="backToList()"
            >
              <CIcon :icon="icon.cilMenu" size="m" />
              Back to list
            </button>
            <button
              type="button"
              class="btn btn-warning rounded-pill mx-2 px-4"
              title="edit"
              @click="editPlant(plantId)"
              v-show="
                roleShow.roleName == 'Admin' || roleShow.roleName == 'Editor'
              "
            >
              <CIcon :icon="icon.cilPen" size="m" />
              Edit
            </button>
          </div>
        </CCardFooter>
        <CModal
          ref="my-modal"
          size="xl"
          :visible="xlDemo"
          @close="
            () => {
              xlDemo = false
            }
          "
        >
          <CModalHeader>
            <CModalTitle>Show image</CModalTitle>
          </CModalHeader>
          <CModalBody>
            <div>
              <img
                :src="linkImg"
                alt="wait"
                class="w-75 rounded mx-auto d-block"
              />
            </div>
          </CModalBody>
        </CModal>
      </CCard>
    </CCol>
  </CRow>
  <Loading v-model:active="isLoading" />
</template>
<script>
import PlantRepository from '@/Repositories/PlantRepository'
import QrcodeVue from 'qrcode.vue'
import * as icon from '@coreui/icons'
export default {
  name: 'PlantDetail',
  data() {
    return {
      plantAddress: null,
      linkImg: '',
      size: 200,
      roleShow: this.$store.state.auth.user.user.role,
      xlDemo: false,
      plantId: '',
      cultureMedium: '',
      code: '',
      user: {},
      role: {},
      location: '',
      plantType: '',
      organization: '',
      createAt: '',
      updateAt: '',
      imgs: [],
      farmDiaries: [],
      plant: {},
      isLoading: true,
    }
  },
  computed: {
    id() {
      return this.$route.params.id
    },
  },
  methods: {
    getPlantDetail() {
      this.plantAddress = window.location.href
      PlantRepository.getPlantById(this.id)
        .then((response) => {
          if (response.status === 200) {
            this.isLoading = false
            this.plantId = response.data.id
            this.cultureMedium = response.data.cultureMedium
            this.location = response.data.location.address
            this.plantType = response.data.plantType.namePlantType
            this.organization = response.data.user.organization.name
            this.createAt = response.data.createAt
            this.updateAt = response.data.updateAt
            this.imgs = response.data.imageDtos
            this.farmDiaries = response.data.farmDiaryDtos
            this.plant = response.data
            this.user = response.data.user
            this.role = response.data.user.role
            this.code = response.data.code
          }
        })
        .catch(() => {
          this.isLoading = false
          this.$swal.fire(
            'Warning!',
            'The system is experiencing an error, please reload the page',
            'warning',
          )
        })
    },
    showModal(img) {
      this.linkImg = img
      this.xlDemo = true
    },
    backToList() {
      this.$router.push('/plant/list-plant')
    },
    editPlant(id) {
      this.$router.push(`/plant/form-plant/${id}`)
    },
  },
  created() {
    this.getPlantDetail()
  },
  components: {
    QrcodeVue,
  },
  setup() {
    return {
      icon,
    }
  },
}
</script>
