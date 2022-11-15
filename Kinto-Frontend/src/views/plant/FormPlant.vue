<template>
  <CRow>
    <CCol>
      <CCard>
        <form
          @submit.prevent="
            this.$route.params.id == 'null' ? createPlant() : updatePlant()
          "
        >
          <CCardHeader>
            <CIcon icon="cil-drop" /> Plant
            <span class="float-end text-danger">{{ message }}</span>
          </CCardHeader>
          <CCardBody>
            <CRow>
              <div class="alert alert-success">FORM PLANT.</div>
            </CRow>
            <hr />
            <CRow>
              <!-- form -->
              <CRow>
                <div class="row">
                  <div class="col-lg-5">
                    <label for="plantId" class="form-label">Id:</label>
                    <input
                      type="text"
                      class="form-control"
                      placeholder="Plant code..."
                      readonly
                      name="plantId"
                      v-model="plantId"
                    />
                  </div>
                  <div class="col-lg-7">
                    <label for="comment" class="form-label text"
                      >Person In Charge:</label
                    >
                    <i class="text-danger">{{ userError }}</i>
                    <v-select
                      v-model="userSelected"
                      :options="optionsUser"
                      label="data_item"
                      :reduce="(data_item) => data_item.id"
                      required
                    />
                  </div>
                </div>
                <div class="row mt-3">
                  <div class="col-lg-5">
                    <label for="id" class="form-label">Plant type: </label>
                    <i class="text-danger">{{ plantTypeError }}</i>
                    <v-select
                      v-model="plantTypeSelected"
                      :options="optionsPlantType"
                      label="namePlantType"
                      :reduce="(namePlantType) => namePlantType.id"
                      required
                    />
                  </div>
                  <div class="col-lg-7">
                    <label for="location" class="form-label text"
                      >Location:</label
                    >
                    <i class="text-danger">{{ locationError }}</i>
                    <v-select
                      v-model="locationSelected"
                      :options="optionsLocation"
                      label="address"
                      :reduce="(address) => address.id"
                      required
                    />
                  </div>
                </div>
                <div class="mt-3 row">
                  <div class="col-lg-6">
                    <label for="cultureMedium" class="form-label text"
                      >Culture Medium:</label
                    >
                    <i class="text-danger">{{ cultureMediumError }}</i>
                    <textarea
                      class="form-control"
                      rows="5"
                      placeholder="Culture Medium..."
                      name="cultureMedium"
                      v-model="cultureMedium"
                      required
                    ></textarea>
                  </div>
                  <div class="col-lg-6">
                    <label for="comment" class="form-label text"
                      >Farm Diary:</label
                    >
                    <textarea
                      class="form-control"
                      rows="5"
                      placeholder="..."
                      name="farmDiary"
                      v-model="farmDiary"
                    ></textarea>
                    <CButton
                      class="btn btn-outline-info rounded-pill ms-2 px-3 m-3"
                      title="create"
                      v-show="farmDiary != ''"
                      @click="addFarmDiary()"
                    >
                      <CIcon :icon="icon.cilPen" size="m" />
                      Add Farm Diary
                    </CButton>
                  </div>
                </div>
              </CRow>
              <!-- Farm Diary -->
              <FarmDiary :farmDiaries="farmDiaries" />
              <!-- image -->
              <Image :images="images" />
              <CRow>
                <div class="mt-3">
                  <h5>Add Image</h5>
                  <form>
                    <div class="form-group">
                      <input
                        type="file"
                        accept="image/*"
                        multiple="multiple"
                        @change="previewMultiImage($event)"
                        class="form-control-file border border-warning"
                        id="my-file"
                        ref="uploadImage"
                      />
                      <div class="border p-2 mt-3">
                        <p>Preview Here:</p>
                        <template v-if="preview_list.length">
                          <div>
                            <table class="table table-striped">
                              <thead>
                                <th>No.</th>
                                <th>Image</th>
                                <th>Description</th>
                              </thead>
                              <tbody>
                                <tr
                                  v-for="(item, index) in preview_list"
                                  :key="index"
                                >
                                  <td>{{ index + 1 }}</td>
                                  <td>
                                    <img
                                      :src="item"
                                      class="img-fluid border border-dark"
                                      style="width: 100px; height: 100px"
                                    />
                                  </td>
                                  <td>
                                    <p class="mb-0">
                                      file name: {{ image_list[index].name }}
                                    </p>
                                  </td>
                                </tr>
                              </tbody>
                            </table>
                          </div>
                        </template>
                      </div>
                    </div>
                  </form>
                </div>
              </CRow>
            </CRow>
            <hr />
          </CCardBody>
          <CCardFooter>
            <div class="text-center my-1">
              <button
                class="btn btn-outline-info rounded-pill px-4"
                title="back to list"
                @click="backToList()"
              >
                <CIcon :icon="icon.cilArrowLeft" size="m" />
                Back
              </button>
              <button
                class="btn btn-outline-primary rounded-pill ms-2 px-4"
                title="create"
                v-show="this.$route.params.id == 'null'"
                type="submit"
              >
                <CIcon :icon="icon.cilPen" size="m" />
                Create
              </button>
              <button
                class="btn btn-outline-success rounded-pill ms-2 px-4"
                title="update"
                v-show="this.$route.params.id != 'null'"
                type="submit"
              >
                <CIcon :icon="icon.cilTrash" size="m" />
                Update
              </button>
              <button
                class="btn btn-outline-warning rounded-pill ms-2 px-4"
                title="reset"
                @click="resetForm()"
              >
                <CIcon :icon="icon.cilReload" size="m" />
                Reset
              </button>
            </div>
            <Loading v-model:active="isLoading" />
          </CCardFooter>
        </form>
      </CCard>
    </CCol>
  </CRow>
</template>
<script>
import LocationRepository from '../../Repositories/LocationRepository'
import PlantTypeRepository from '../../Repositories/PlantTypeRepository'
import OrganizationRepository from '../../Repositories/OrganizationRepository'
import PlantRepository from '../../Repositories/PlantRepository'
import FarmDiaryRepository from '../../Repositories/FarmDiaryRepository'
import ImageRepository from '../../Repositories/ImageRepository'
import UserRepository from '../../Repositories/UserRepository'
import * as icon from '@coreui/icons'

import Image from '../plant/Image.vue'
import FarmDiary from '../plant/FarmDiary.vue'

export default {
  name: 'form-plant',
  data() {
    return {
      plantId: '',
      isLoading: false,
      farmDiary: '',
      message: '',
      cultureMedium: '',
      location: {},
      plantType: {},
      organization: {},
      images: [],
      user: {},
      org: this.$store.state.auth.user.user.organization,
      userSelected: 'Chose person in change...',
      locationSelected: 'Chose location...',
      plantTypeSelected: 'Chose plant type...',

      plantTypeError: '',
      locationError: '',
      cultureMediumError: '',
      userError: '',

      preview_list: [],
      image_list: [],
      farmDiaries: [],
      optionsUser: [],
      optionsLocation: [],
      optionsPlantType: [],
    }
  },
  computed: {
    id() {
      return this.$route.params.id
    },
  },
  methods: {
    // plant
    getPlantById() {
      if (this.id !== 'null') {
        this.isLoading = true
        PlantRepository.getPlantById(this.id)
          .then((response) => {
            if (response.status == 200) {
              this.isLoading = false
              this.plantId = response.data.id
              this.userSelected = response.data.user.id
              this.locationSelected = response.data.location.id
              this.plantTypeSelected = response.data.plantType.id
              this.cultureMedium = response.data.cultureMedium
              this.images = response.data.imageDtos
              this.farmDiaries = response.data.farmDiaryDtos
              // set
              this.location = response.data.location
              this.plantType = response.data.plantType
              this.organization = response.data.organization
              this.user = response.data.user
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
      }
    },
    createPlant() {
      if (
        this.userSelected == 'Chose person in change...' ||
        this.locationSelected == 'Chose location...' ||
        this.plantTypeSelected == 'Chose plant type...'
      ) {
        if (this.plantTypeSelected == 'Chose plant type...') {
          this.plantTypeError = ' Please select a plant type'
        } else this.plantTypeError = ''
        if (this.locationSelected == 'Chose location...') {
          this.locationError = ' Please select a location'
        } else this.locationError = ''
        if (this.userSelected == 'Chose person in change...') {
          this.userError = 'Please select person in change'
        } else this.userError = ''
      } else {
        this.isLoading = true
        PlantRepository.createPlant({
          cultureMedium: this.cultureMedium,
          location: this.location,
          plantType: this.plantType,
          orgId: this.org.id,
          user: this.user,
        })
          .then((res) => {
            if (res.status == 200) {
              this.isLoading = false
              this.plantId = res.data.id
              let files = this.$refs.uploadImage.files
              if (files.length > 0) {
                this.createImage(this.plantId)
              }
              this.farmDiaries.forEach((item) => {
                if (item.id == null) {
                  this.createFarmDiary(this.plantId, item.name)
                }
              })
              this.$swal.fire('Success!', 'Create successfully!', 'success')
              this.$router.push('/plant/list-plant')
            }
          })
          .catch((error) => {
            this.isLoading = false
            this.$swal.fire('Failed!', 'Please check again!', 'error')
            this.message = error.response.data.error
          })
      }
    },
    updatePlant() {
      if (
        this.userSelected == 'Chose person in change...' ||
        this.locationSelected == 'Chose location...' ||
        this.plantTypeSelected == 'Chose plant type...'
      ) {
        if (this.plantTypeSelected == 'Chose plant type...') {
          this.plantTypeError = ' Please select a plant type'
        } else this.plantTypeError = ''
        if (this.locationSelected == 'Chose location...') {
          this.locationError = ' Please select a location'
        } else this.locationError = ''
        if (this.userSelected == 'Chose person in change...') {
          this.userError = 'Please select person in change'
        } else this.userError = ''
      } else {
        this.isLoading = true
        console.log(this.org)
        PlantRepository.updatePlant(this.id, {
          cultureMedium: this.cultureMedium,
          location: this.location,
          plantType: this.plantType,
          orgId: this.org.id,
          user: this.user,
        })
          .then((res) => {
            if (res.status == 200) {
              this.isLoading = false
              let files = this.$refs.uploadImage.files
              if (files.length > 0) {
                this.createImage(this.id)
              }
              this.farmDiaries.forEach((item) => {
                if (item.id == null) {
                  this.createFarmDiary(this.plantId, item.name)
                }
              })
              this.$swal.fire('Success!', 'Update successfully!', 'success')
              this.$router.push('/plant/list-plant')
            }
          })
          .catch((error) => {
            this.isLoading = false
            if (error.response.data.error == this.email + ' already exists') {
              this.message = error.response.data.error
            } else {
              this.message = 'The system is crashing, please try again later'
            }
          })
      }
    },
    // loaction
    getLocationRepository() {
      LocationRepository.getAllLocations(`?orgId=${this.org.id}`).then(
        (response) => {
          this.optionsLocation = response.data
        },
      )
    },
    getValueLocation(id) {
      LocationRepository.getLocationById(id).then((response) => {
        this.location = response.data
      })
    },
    //user
    getUserRepository() {
      UserRepository.getAllUsers(`?orgId=${this.org.id}`).then((response) => {
        this.optionsUser = response.data
        this.optionsUser.map(function (x) {
          return (x.data_item =
            x.fullname + ' - ' + ' - ' + '(' + x.role.roleName + ')')
        })
      })
    },
    getValueUser(id) {
      UserRepository.getUserById(id).then((response) => {
        this.user = response.data
      })
    },
    // plantTypes
    getPlantTypeRepository() {
      PlantTypeRepository.getAllPlantTypes(`?orgId=${this.org.id}`).then(
        (response) => {
          this.optionsPlantType = response.data
        },
      )
    },
    getValuePlantType(id) {
      PlantTypeRepository.getPlantTypeById(id).then((response) => {
        this.plantType = response.data
      })
    },
    // organizations
    getOrganizationRepository() {
      OrganizationRepository.getAllOrganizations().then((response) => {
        this.organizations = response.data
      })
    },
    getValueOrganization(event) {
      OrganizationRepository.getOrganizationById(event.target.value).then(
        (response) => {
          this.organization = response.data
        },
      )
    },
    // image
    previewMultiImage(event) {
      var input = event.target
      var count = input.files.length
      var index = 0
      if (input.files) {
        while (count--) {
          var reader = new FileReader()
          reader.onload = (e) => {
            this.preview_list.push(e.target.result)
          }
          this.image_list.push(input.files[index])
          reader.readAsDataURL(input.files[index])
          index++
        }
      }
    },
    createImage(imgId) {
      this.formData = new FormData()
      let files = this.$refs.uploadImage.files
      for (let i = 0; i < files.length; i++) {
        this.formData.append('files', files[i])
      }
      this.formData.append('plantId', imgId)
      this.formData.append('orgId', this.org.id)
      ImageRepository.createImage(this.formData).then(() => {})
    },
    // farmDiary
    createFarmDiary(fdId, nameFarmDiary) {
      FarmDiaryRepository.createFarmDiary({
        name: nameFarmDiary,
        plantId: fdId,
        organization: this.org,
      }).then(() => {})
    },
    addFarmDiary() {
      var dateobj = new Date()
      let farmDiaryObject = {
        id: null,
        name: this.farmDiary,
        plantId: this.id,
        createAt: dateobj.toISOString(),
        updateAt: '',
      }
      this.farmDiaries.push({ ...farmDiaryObject })
      this.farmDiary = ''
    },
    resetForm() {
      this.$router.push('/plant/form-plant/null')
      this.plantId = ''
      this.cultureMedium = ''
      this.userSelected = 'Chose person in change...'
      this.locationSelected = 'Chose location...'
      this.plantTypeSelected = 'Chose plant type...'
      this.preview_list = []
      this.image_list = []
      this.farmDiaries = []
      this.plantTypeError = ''
      this.organizationError = ''
      this.locationError = ''
      this.cultureMediumError = ''
      this.userError = ''
    },
    backToList() {
      this.$router.push('/plant/list-plant')
    },
  },
  created() {
    this.getLocationRepository()
    this.getPlantTypeRepository()
    this.getOrganizationRepository()
    this.getUserRepository()
    this.getPlantById()
  },
  setup() {
    return {
      icon,
    }
  },
  components: {
    Image,
    FarmDiary,
  },
  watch: {
    userSelected(newQuestion, oldQuestion) {
      if (oldQuestion != newQuestion) {
        this.getValueUser(newQuestion)
      }
    },
    locationSelected(newQuestion, oldQuestion) {
      if (oldQuestion != newQuestion) {
        this.getValueLocation(newQuestion)
      }
    },
    plantTypeSelected(newQuestion, oldQuestion) {
      if (oldQuestion != newQuestion) {
        this.getValuePlantType(newQuestion)
      }
    },
  },
}
</script>
