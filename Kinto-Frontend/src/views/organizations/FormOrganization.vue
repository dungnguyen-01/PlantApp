<template>
  <CRow>
    <CCol>
      <CCard>
        <form
          @submit.prevent="
            this.$route.params.id == 'null'
              ? createOrganization()
              : updateOrganization()
          "
        >
          <CCardHeader>
            <CIcon icon="cil-drop" />
            Form Organization
            <span class="float-end text-danger">{{ message }}</span>
          </CCardHeader>
          <CCardBody>
            <CRow>
              <div class="row mx-md-2">
                <div class="col-md-5">
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
                <div class="col-md-7">
                  <label for="name" class="form-label text">Name:</label>
                  <i class="text-danger">{{ nameError }}</i>
                  <input
                    type="text"
                    class="form-control"
                    placeholder="Name organization..."
                    name="name"
                    v-model="name"
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
import OrganizationRepository from '@/Repositories/OrganizationRepository'
import * as icon from '@coreui/icons'
export default {
  name: 'Form Organization',
  data() {
    return {
      id: '',
      name: '',
      message: '',
      nameError: '',
      isLoading: false,
    }
  },
  computed: {
    organizationId() {
      return this.$route.params.id
    },
  },
  methods: {
    getDetailOrganization() {
      if (this.organizationId !== 'null') {
        this.isLoading = true
        OrganizationRepository.getOrganizationById(this.organizationId)
          .then((response) => {
            if (response.status == 200) {
              this.isLoading = false
              this.id = response.data.id
              this.name = response.data.name
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
    async createOrganization() {
      if (this.name == '') {
        this.nameError = 'Name cannot be null!'
      } else {
        this.isLoading = true
        await OrganizationRepository.createOrganization({
          name: this.name,
        })
          .then((response) => {
            if (response.status == 200) {
              this.isLoading = false
              this.$swal.fire('Success!', 'Create successfully!', 'success')
              this.$router.push('/organization/list-organization')
            }
          })
          .catch((error) => {
            this.isLoading = false
            this.$swal.fire('Failed!', 'Please check again!', 'error')
            if (error.response.data.error == this.name + ' already exists') {
              this.message = error.response.data.error
            } else {
              this.message = 'The system is crashing, please try again later'
            }
          })
      }
    },
    async updateOrganization() {
      if (this.name == '') {
        this.nameError = 'Name cannot be null!'
      } else {
        this.isLoading = true
        await OrganizationRepository.updateOrganization(this.id, {
          name: this.name,
        })
          .then((res) => {
            if (res.status == 200) {
              this.isLoading = false
              this.$swal.fire('Success!', 'Update successfully!', 'success')
              this.$router.push('/organization/list-organization')
            }
          })
          .catch((error) => {
            this.isLoading = false
            this.$swal.fire('Failed!', 'Please check again!', 'error')
            if (error.response.data.error == this.name + ' already exists') {
              this.message = error.response.data.error
            } else {
              this.message = 'The system is crashing, please try again later'
            }
          })
      }
    },
    resetForm() {
      this.$router.push('/organization/form-organization/null')
      this.id = ''
      this.name = ''
      this.message = ''
    },
    backToList() {
      this.$router.push('/organization/list-organization')
    },
  },
  setup() {
    return {
      icon,
    }
  },
  watch: {
    message(newQuestion, oldQuestion) {
      if (oldQuestion != newQuestion) {
        this.createOrganization()
      }
    },
  },
  created() {
    this.getDetailOrganization()
  },
}
</script>
