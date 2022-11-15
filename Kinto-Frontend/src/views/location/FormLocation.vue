<template>
  <CRow>
    <CCol>
      <CCard>
        <form
          @submit.prevent="
            this.$route.params.id == 'null'
              ? createLocation()
              : updateLocation()
          "
        >
          <CCardHeader>
            <CIcon icon="cil-drop" />
            Form Location
            <span class="float-end text-danger">{{ message }}</span>
          </CCardHeader>
          <CCardBody>
            <CRow>
              <div class="row mx-md-2">
                <div class="col-md-6">
                  <label for="id" class="form-label text">Id:</label>
                  <input
                    type="text"
                    class="form-control bg-light"
                    placeholder="id auto increment..."
                    name="id"
                    readonly
                    v-model="id"
                  />
                </div>
                <div class="col-md-6">
                  <label for="code" class="form-label text">Code:</label>
                  <i class="text-danger"> </i>
                  <input
                    type="text"
                    class="form-control"
                    placeholder="enter 3 to 5 characters..."
                    name="code"
                    v-model="code"
                    required
                  />
                </div>
                <div class="col">
                  <label for="address" class="form-label text">Address:</label>
                  <input
                    type="text"
                    class="form-control"
                    placeholder="address..."
                    name="address"
                    v-model="address"
                    required
                  />
                </div>
              </div>
            </CRow>
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
          </CCardFooter>
        </form>
      </CCard>
    </CCol>
  </CRow>
  <Loading v-model:active="isLoading" />
</template>
<script>
import LocationRepository from '@/Repositories/LocationRepository'
import * as icon from '@coreui/icons'
export default {
  name: 'FormLoaction',
  data() {
    return {
      id: '',
      address: '',
      organization: this.$store.state.auth.user.user.organization,
      code: '',
      message: '',
      isLoading: false,
    }
  },
  computed: {
    locationId() {
      return this.$route.params.id
    },
  },
  methods: {
    getDetailLocation() {
      if (this.locationId !== 'null') {
        this.isLoading = true
        LocationRepository.getLocationById(this.locationId)
          .then((response) => {
            if (response.status == 200) {
              this.isLoading = false
              this.id = response.data.id
              this.address = response.data.address
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
      }
    },
    async createLocation() {
      if (
        this.onlyLetters(this.code) == false ||
        this.code.length <= 2 ||
        this.code.length >= 6
      ) {
        this.message =
          'enter 3 to 5 characters and numbers (A-Z, 0-9) without spaces and special characters.'
      } else {
        this.isLoading = true
        await LocationRepository.createLocation({
          address: this.address,
          organization: this.organization,
          code: this.code.toUpperCase(),
        })
          .then((response) => {
            if (response.status == 200) {
              this.isLoading = false
              this.$swal.fire('Success!', 'Create successfully!', 'success')
              this.$router.push('/location/list-location')
            }
          })
          .catch((error) => {
            this.isLoading = false
            this.$swal.fire('Failed!', 'Please check again!', 'error')
            this.message = error.response.data.error
          })
      }
    },
    updateLocation() {
      if (
        this.onlyLetters(this.code) == false ||
        this.code.length <= 2 ||
        this.code.length >= 6
      ) {
        this.message =
          'enter 3 to 5 characters and numbers (A-Z, 0-9) without spaces and special characters.'
      } else {
        this.isLoading = true
        LocationRepository.updateLocation(this.id, {
          address: this.address,
          organization: this.organization,
          code: this.code.toUpperCase(),
        })
          .then((res) => {
            if (res.status == 200) {
              this.isLoading = false
              this.$swal.fire('Success!', 'Update successfully!', 'success')
              this.$router.push('/location/list-location')
            }
          })
          .catch((error) => {
            this.isLoading = false
            this.$swal.fire('Failed!', 'Please check again!', 'error')
            this.message = error.response.data.error
          })
      }
    },
    onlyLetters(str) {
      return /^[A-Z0-9]+$/.test(str)
    },
    resetForm() {
      this.$router.push('/location/form-location/null')
      this.id = ''
      this.address = ''
      this.message = ''
    },
    backToList() {
      this.$router.push('/location/list-location')
    },
  },
  setup() {
    return {
      icon,
    }
  },
  created() {
    this.getDetailLocation()
  },
}
</script>
