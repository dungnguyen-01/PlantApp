<template>
  <CRow>
    <CCol>
      <CCard>
        <CCardHeader> <CIcon icon="cil-drop" /> List Plant Type</CCardHeader>
        <CCardBody>
          <CRow>
            <div>
              <select v-model="selectSizePage">
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
                <b>{{ selectSizePage }}</b>
              </span>
              <CButton
                v-on:click="addPlantType()"
                class="btn btn-info text-light float-end mb-2"
                v-show="role.roleName == 'Admin' || role.roleName == 'Editor'"
              >
                Add Plant Type
              </CButton>
            </div>
          </CRow>
          <CRow>
            <div class="table-responsive-lg">
              <table class="table">
                <thead class="table-dark">
                  <tr>
                    <th>ID.</th>
                    <th>Code</th>
                    <th>Name</th>
                    <th>Date create</th>
                    <th>Date Update</th>
                    <th>{{ totalPages }}</th>
                    <th></th>
                    <th></th>
                  </tr>
                </thead>
                <tbody>
                  <tr
                    v-for="(plantType, index) in plantTypes.content"
                    :key="index"
                  >
                    <td>{{ index + 1 }}</td>
                    <td>{{ plantType.code }}</td>
                    <td>
                      <div class="cut_text_show">
                        <p>
                          {{ plantType.namePlantType }}
                        </p>
                      </div>
                    </td>
                    <td>
                      {{ this.$formatDate.formatDate(plantType.createAt) }}
                    </td>
                    <td>
                      {{ this.$formatDate.formatDate(plantType.updateAt) }}
                    </td>
                    <td>
                      <button
                        class="btn btn-outline-info rounded-pill p-0 px-4 ml-1"
                        title="detail"
                        v-on:click="detailPlantType(plantType.id)"
                      >
                        <CIcon :icon="icon.cilCompress" size="m" />
                      </button>
                    </td>
                    <td>
                      <button
                        class="btn btn-outline-warning rounded-pill p-0 px-4 mx-2"
                        title="edit"
                        v-on:click="updatePlantType(plantType.id)"
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
                        @click="deletePlantType(plantType.id)"
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
                v-for="(page, index) in plantTypes.totalPages"
                :key="index"
                @click="setPage(page)"
                :class="page == pageNumber ? 'active' : ''"
              >
                {{ page }}
              </CPaginationItem>
              <CPaginationItem
                style="cursor: pointer"
                v-show="pageNumber != plantTypes.totalPages"
                v-bind:hidden="plantTypes.totalPages == 0"
                @click="nextPage()"
                >Next</CPaginationItem
              >
            </CPagination>
          </CRow>
        </CCardBody>
      </CCard>
    </CCol>
  </CRow>
  <Loading v-model:active="isLoading" />
</template>

<script>
import PlantTypeRepository from '@/Repositories/PlantTypeRepository'
import * as icon from '@coreui/icons'
export default {
  name: 'PlantType',
  data() {
    return {
      plantTypes: [],
      pageNumber: 1,
      role: this.$store.state.auth.user.user.role,
      orgId: this.$store.state.auth.user.user.organization.id,
      responseSuccess: true,
      isLoading: true,
      selectSizePage: 15,
      options: [
        { text: '15', value: '15' },
        { text: '25', value: '25' },
        { text: '50', value: '50' },
        { text: '100', value: '100' },
      ],
    }
  },
  methods: {
    fetchPlantTypes() {
      PlantTypeRepository.getPlantTypes(
        `?size=${this.selectSizePage}&page=${this.pageNumber - 1}&orgId=${
          this.orgId
        }`,
      )
        .then((response) => {
          setTimeout(() => {
            this.isLoading = false
          }, 1000)
          if (response.status === 200) {
            this.plantTypes = response.data
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
    detailPlantType(id) {
      this.$router.push(`/plant_type/detail-plant_type/${id}`)
    },
    addPlantType() {
      this.$router.push('/plant_type/form-plant_type/null')
    },
    updatePlantType(id) {
      this.$router.push(`/plant_type/form-plant_type/${id}`)
    },
    deletePlantType(id) {
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
          PlantTypeRepository.deletePlantTypeById(id)
            .then((response) => {
              if (response.status === 200) {
                this.isLoading = false
                this.fetchPlantTypes()
                this.$swal.fire(
                  'Deleted!',
                  'You have successfully deleted.',
                  'success',
                )
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
    this.fetchPlantTypes()
  },
  setup() {
    return {
      icon,
    }
  },
  watch: {
    selectSizePage(newQuestion, oldQuestion) {
      if (oldQuestion != newQuestion) {
        this.fetchPlantTypes()
      }
    },
    pageNumber(newQuestion, oldQuestion) {
      if (oldQuestion != newQuestion) {
        this.fetchPlantTypes()
      }
    },
  },
}
</script>
