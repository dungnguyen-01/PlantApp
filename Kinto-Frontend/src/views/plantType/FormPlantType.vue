<template>
  <CRow>
    <CCol>
      <CCard>
        <form
          @submit.prevent="
            this.$route.params.id == 'null'
              ? createPlantType()
              : updatePlantType()
          "
        >
          <CCardHeader>
            <CIcon icon="cil-drop" />
            Form Plant Type
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
                  <i class="text-danger">*Enter numbers and alphabets only.</i>
                </div>
                <div>
                  <label for="namePlantType" class="form-label text"
                    >Name Plant Type:</label
                  >
                  <i class="text-danger">{{ namePlantTypeError }}</i>
                  <input
                    type="text"
                    class="form-control"
                    placeholder="Name Plant Type..."
                    name="namePlantType"
                    v-model="namePlantType"
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
import PlantTypeRepository from '@/Repositories/PlantTypeRepository'
import * as icon from '@coreui/icons'
export default {
  name: 'FormLoaction',
  data() {
    return {
      id: '',
      namePlantType: '',
      namePlantTypeError: '',
      code: '',
      message: '',
      isLoading: false,
      organization: this.$store.state.auth.user.user.organization,
    }
  },
  computed: {
    plantTypeId() {
      return this.$route.params.id
    },
  },
  methods: {
    getDetailPlantType() {
      if (this.plantTypeId !== 'null') {
        this.isLoading = true
        PlantTypeRepository.getPlantTypeById(this.plantTypeId)
          .then((response) => {
            if (response.status == 200) {
              this.isLoading = false
              this.id = response.data.id
              this.namePlantType = response.data.namePlantType
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
    createPlantType() {
      if (
        this.onlyLetters(this.code) == false ||
        this.code.length <= 2 ||
        this.code.length >= 6
      ) {
        this.message =
          'enter 3 to 5 characters and numbers (A-Z, 0-9) without spaces and special characters.'
      } else {
        this.isLoading = true
        PlantTypeRepository.createPlantType({
          namePlantType: this.namePlantType,
          organization: this.organization,
          code: this.code.toUpperCase(),
        })
          .then((res) => {
            if (res.status == 200) {
              this.isLoading = false
              this.$swal.fire('Success!', 'Create successfully!', 'success')
              this.$router.push('/plant_type/list-plant_type')
            }
          })
          .catch((error) => {
            this.isLoading = false
            this.$swal.fire('Failed!', 'Please check again!', 'error')
            this.message = error.response.data.error
          })
      }
    },
    updatePlantType() {
      if (
        this.onlyLetters(this.code) == false ||
        this.code.length <= 2 ||
        this.code.length >= 6
      ) {
        this.message =
          'Enter 3 to 5 characters and numbers (A-Z, 0-9) without spaces and special characters.'
      } else {
        this.isLoading = true
        PlantTypeRepository.updatePlantType(this.id, {
          id: this.id,
          namePlantType: this.namePlantType,
          organization: this.organization,
          code: this.code.toUpperCase(),
        })
          .then((res) => {
            if (res.status == 200) {
              this.isLoading = false
              this.$swal.fire('Success!', 'Update successfully!', 'success')
              this.$router.push('/plant_type/list-plant_type')
            }
          })
          .catch((error) => {
            this.isLoading = false
            this.$swal.fire('Failed!', 'Please check again!', 'error')
            this.message = error.response.data.error
          })
      }
    },
    backToList() {
      this.$router.push('/plant_type/list-plant_type')
    },
    resetForm() {
      this.$router.push('/plant_type/form-plant_type/null')
      this.id = ''
      this.namePlantType = ''
      this.message = ''
    },
    onlyLetters(str) {
      return /^[A-Z0-9]+$/.test(str)
    },
  },
  setup() {
    return {
      icon,
    }
  },
  created() {
    this.getDetailPlantType()
  },
}
</script>
