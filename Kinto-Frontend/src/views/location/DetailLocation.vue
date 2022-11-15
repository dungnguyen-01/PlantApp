<template>
  <CRow>
    <CCol>
      <CCard>
        <CCardHeader> <CIcon icon="cil-drop" /> Detail Location </CCardHeader>
        <CCardBody>
          <CRow>
            <labe class="form-label text">- Id: {{ locationId }}</labe>
            <labe class="form-label text">- Code: {{ code }}</labe>
            <labe class="form-label text">- Address: {{ address }}</labe>
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
              @click="editLocation(locationId)"
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
import LocationRepository from '@/Repositories/LocationRepository'
import * as icon from '@coreui/icons'
export default {
  name: 'DetailLocation',
  data() {
    return {
      locationId: '',
      role: this.$store.state.auth.user.user.role,
      address: '',
      code: '',
      createAt: '',
      updateAt: '',
      org: '',
      isLoading: true,
    }
  },
  computed: {
    id() {
      return this.$route.params.id
    },
  },
  methods: {
    getDetailLocation() {
      LocationRepository.getLocationById(this.id)
        .then((response) => {
          if (response.status === 200) {
            this.isLoading = false
            this.locationId = response.data.id
            this.address = response.data.address
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
      this.$router.push('/location/list-location')
    },
    editLocation(id) {
      this.$router.push(`/location/form-location/${id}`)
    },
  },
  created() {
    this.getDetailLocation()
  },
  setup() {
    return {
      icon,
    }
  },
}
</script>
