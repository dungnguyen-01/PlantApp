<template>
  <CRow>
    <CCol>
      <CCard>
        <CCardHeader><CIcon icon="cil-drop" /> Plant</CCardHeader>
        <Loading v-model:active="isLoading" />
        <CCardBody>
          <CRow>
            <div>
              <select v-model="selected">
                <option
                  v-for="(option, index) in options"
                  :value="option.value"
                  :key="index"
                >
                  {{ option.text }}
                </option>
              </select>
              <span class="m-2"
                >Selected:
                <b>{{ selected }}</b>
              </span>
              <button
                v-on:click="addPlant()"
                class="btn btn-info text-light float-end mb-2"
                v-show="role.roleName == 'Admin' || role.roleName == 'Editor'"
              >
                Add New Plant
              </button>
            </div>
          </CRow>
          <CRow>
            <div class="p-3 row">
              <div class="col-md-4 mt-2">
                <v-select
                  v-model="plantTypeSelected"
                  :options="optionsPlantType"
                  label="namePlantType"
                  :reduce="(namePlantType) => namePlantType.id"
                />
              </div>
              <div class="col-md-4 mt-2">
                <v-select
                  v-model="userSelected"
                  :options="optionsUser"
                  label="data_item"
                  :reduce="(data_item) => data_item.id"
                />
              </div>
              <div class="col-md-4 mt-2">
                <v-select
                  v-model="locationSelected"
                  :options="optionsLocation"
                  label="address"
                  :reduce="(address) => address.id"
                />
              </div>
            </div>
            <hr />
            <i v-show="plants.lengh == 0">not found!</i>
            <div class="table-responsive-lg">
              <table class="table">
                <thead class="table-dark">
                  <tr>
                    <th>ID.</th>
                    <th>Code</th>
                    <th>Plant Type</th>
                    <th>Person in charge</th>
                    <th>Location</th>
                    <th>Create</th>
                    <th>Update</th>
                    <th></th>
                    <th></th>
                    <th></th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="(plant, index) in plants.content" :key="index">
                    <td>{{ index + 1 }}</td>
                    <td>
                      <div class="cut_text_show">
                        <p>
                          {{ plant.code }}
                        </p>
                      </div>
                    </td>
                    <td style="min-width: 150px">
                      {{ plant.plantType.namePlantType }}
                    </td>
                    <td class="cut_text_show">{{ plant.user.fullname }}</td>
                    <td>
                      <div class="cut_text_show">
                        <p>
                          {{ plant.location.address }}
                        </p>
                      </div>
                    </td>
                    <td>
                      {{ this.$formatDate.formatDate(plant.createAt) }}
                    </td>
                    <td>
                      {{ this.$formatDate.formatDate(plant.updateAt) }}
                    </td>
                    <td>
                      <button
                        class="btn btn-outline-info rounded-pill p-0 px-4"
                        title="detail"
                        v-on:click="detailPlant(plant.id)"
                      >
                        <CIcon :icon="icon.cilCompress" size="m" />
                      </button>
                    </td>
                    <td>
                      <button
                        class="btn btn-outline-warning rounded-pill p-0 px-4"
                        title="edit"
                        v-on:click="updatePlant(plant.id)"
                        v-show="
                          role.roleName == 'Admin' || role.roleName == 'Editor'
                        "
                      >
                        <CIcon :icon="icon.cilPen" size="m" />
                      </button>
                    </td>
                    <td>
                      <button
                        class="btn btn-outline-danger rounded-pill p-0 px-4"
                        title="delete"
                        @click="deletePlant(plant.id)"
                        v-show="
                          role.roleName == 'Admin' || role.roleName == 'Editor'
                        "
                      >
                        <CIcon :icon="icon.cilTrash" size="m" />
                      </button>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
          </CRow>
          <CRow>
            <CPagination
              class="pagination justify-content-center"
              aria-label="Page navigation example"
            >
              <CPaginationItem
                style="cursor: pointer"
                v-show="pageNumber != 1"
                @click="previousPage()"
                >Previous
              </CPaginationItem>
              <CPaginationItem
                style="cursor: pointer"
                v-for="(page, index) in plants.totalPages"
                :key="index"
                @click="setPage(page)"
                :class="page == pageNumber ? 'active' : ''"
              >
                {{ page }}
              </CPaginationItem>
              <CPaginationItem
                style="cursor: pointer"
                v-show="pageNumber != plants.totalPages"
                v-bind:hidden="plants.totalPages == 0"
                @click="nextPage()"
                >Next</CPaginationItem
              >
            </CPagination>
          </CRow>
        </CCardBody>
      </CCard>
    </CCol>
  </CRow>
</template>
<script>
import { CIcon } from '@coreui/icons-vue'
import * as icon from '@coreui/icons'
import PlantRepository from '@/Repositories/PlantRepository'
import PlantTypeRepository from '@/Repositories/PlantTypeRepository'
import ImageRepository from '@/Repositories/ImageRepository'
import FarmDiaryRepository from '@/Repositories/FarmDiaryRepository'
import UserRepository from '@/Repositories/UserRepository'
import LocationRepository from '@/Repositories/LocationRepository'
export default {
  name: 'Plant',
  components: {
    CIcon,
  },
  data() {
    return {
      status: false,
      isLoading: true,
      pageNumber: 1,
      role: this.$store.state.auth.user.user.role,
      orgId: this.$store.state.auth.user.user.organization.id,
      plantTypeSelected: 'Filter by plant type...',
      userSelected: 'Filter by user...',
      locationSelected: 'Filter by location...',
      plants: [],
      optionsUser: [],
      optionsLocation: [],
      optionsPlantType: [],
      selected: 15,
      options: [
        { text: '15', value: '15' },
        { text: '25', value: '25' },
        { text: '50', value: '50' },
        { text: '100', value: '100' },
      ],
    }
  },
  methods: {
    async fetchPlants() {
      PlantRepository.getPlants(
        `?plant_type=${this.plantTypeSelected}&location=${
          this.locationSelected
        }&pic=${this.userSelected}&size=${this.selected}&page=${
          this.pageNumber - 1
        }&orgId=${this.orgId}`,
      )
        .then((response) => {
          if (response.status === 200) {
            setTimeout(() => {
              this.isLoading = false
              this.plants = response.data
            }, 1000)
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
    updatePlant(id) {
      this.$router.push(`/plant/form-plant/${id}`)
    },
    deletePlant(id) {
      this.$swal({
        title: 'Are you sure?',
        text: "You won't be able to revert this!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Yes, delete it!',
        cancelButtonText: 'No, cancel!',
        confirmButtonColor: '#55c663',
        reverseButtons: true,
      }).then((result) => {
        if (result.isConfirmed) {
          this.isLoading = true
          PlantRepository.deletePlantById(id)
            .then((response) => {
              if (response.status === 200) {
                this.isLoading = false
                ImageRepository.deleteImageByPlantId(id).then(() => {})
                FarmDiaryRepository.deleteFarmDiaryByPlantId(id).then(() => {})
                this.$swal.fire(
                  'Deleted!',
                  'You have successfully deleted.',
                  'success',
                )
                this.fetchPlants()
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
      })
    },
    getAllPlantType() {
      PlantTypeRepository.getAllPlantTypes(`?orgId=${this.orgId}`).then(
        (res) => {
          this.optionsPlantType = res.data
        },
      )
    },
    getValuePlantType(id) {
      if (id != null) {
        PlantTypeRepository.getPlantTypeById(id).then(() => {
          this.fetchPlants()
        })
      } else window.location.reload()
    },
    changeStatus() {
      this.status = true
    },
    detailPlant(id) {
      this.$router.push(`/plant/plant-detail/${id}`)
    },
    addPlant() {
      this.$router.push('/plant/form-plant/null')
    },
    // user
    getUserRepository() {
      UserRepository.getAllUsers(`?orgId=${this.orgId}`).then((response) => {
        this.optionsUser = response.data
        this.optionsUser.map(function (x) {
          return (x.data_item =
            x.fullname + ' - ' + ' - ' + '(' + x.role.roleName + ')')
        })
      })
    },
    getValueUser(id) {
      if (id != null) {
        UserRepository.getUserByIdAll(id).then((response) => {
          this.user = response.data
          this.fetchPlants()
        })
      } else window.location.reload()
    },
    // loaction
    getLocationRepository() {
      LocationRepository.getAllLocations(`?orgId=${this.orgId}`).then(
        (response) => {
          this.optionsLocation = response.data
        },
      )
    },
    getValueLocation(id) {
      if (id != null) {
        LocationRepository.getLocationById(id).then((response) => {
          this.location = response.data
          this.fetchPlants()
        })
      } else window.location.reload()
    },
    // pagination
    setPage(page) {
      this.pageNumber = page
    },
    nextPage() {
      this.pageNumber = this.pageNumber + 1
    },
    previousPage() {
      this.pageNumber = this.pageNumber - 1
    },
  },
  created() {
    this.fetchPlants()
    this.getAllPlantType()
    this.getUserRepository()
    this.getLocationRepository()
  },
  setup() {
    return {
      icon,
    }
  },
  computed() {
    this.fetchPlants()
  },
  watch: {
    selected(newQuestion, oldQuestion) {
      if (newQuestion != oldQuestion) {
        this.fetchPlants()
      }
    },
    plantTypeSelected(newQuestion, oldQuestion) {
      if (oldQuestion != newQuestion) {
        this.getValuePlantType(newQuestion)
      }
    },
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
    pageNumber(newQuestion, oldQuestion) {
      if (oldQuestion != newQuestion) {
        this.fetchPlants()
      }
    },
  },
}
</script>
