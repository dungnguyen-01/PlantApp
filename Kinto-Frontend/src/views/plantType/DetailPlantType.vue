<template>
  <CRow>
    <CCol>
      <CCard>
        <CCardHeader> <CIcon icon="cil-drop" /> Detail Plant Type </CCardHeader>
        <CCardBody>
          <CRow>
            <labe class="form-label text">- Id: {{ plantTypeId }}</labe>
            <labe class="form-label text">- Code: {{ code }}</labe>
            <labe class="form-label text"
              >- Name Plant Type: {{ namePlantType }}</labe
            >
            <labe class="form-label text"
              >- Date create: {{ this.$formatDate.formatDate(createAt) }}</labe
            >
            <labe class="form-label text"
              >- Date update: {{ this.$formatDate.formatDate(updateAt) }}</labe
            >
            <hr />
            <label>- Organization: {{ org }}</label>
          </CRow>
        </CCardBody>
        <CCardFooter>
          <div>
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
              @click="editPlantType(plantTypeId)"
              v-show="role.roleName == 'Admin' || role.roleName == 'Editor'"
            >
              <CIcon :icon="icon.cilPen" size="m" />
              Edit
            </button>
          </div>
        </CCardFooter>
      </CCard>
    </CCol>
  </CRow>
  <Loading v-model:active="isLoading" />
</template>
<script>
import PlantTypeRepository from '@/Repositories/PlantTypeRepository'
import * as icon from '@coreui/icons'
export default {
  name: 'DetailPlantType',
  data() {
    return {
      plantTypeId: '',
      role: this.$store.state.auth.user.user.role,
      namePlantType: '',
      code: '',
      createAt: '',
      updateAt: '',
      isLoading: true,
      org: '',
    }
  },
  computed: {
    id() {
      return this.$route.params.id
    },
  },
  methods: {
    getDetailPlantType() {
      PlantTypeRepository.getPlantTypeById(this.id)
        .then((response) => {
          if (response.status === 200) {
            this.isLoading = false
            this.plantTypeId = response.data.id
            this.namePlantType = response.data.namePlantType
            this.code = response.data.code
            this.org = response.data.organization.name
            this.createAt = response.data.createAt
            this.updateAt = response.data.updateAt
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
    backToList() {
      this.$router.push('/plant_type/list-plant_type')
    },
    editPlantType(id) {
      this.$router.push(`/plant_type/form-plant_type/${id}`)
    },
  },
  created() {
    this.getDetailPlantType()
  },
  setup() {
    return {
      icon,
    }
  },
}
</script>
