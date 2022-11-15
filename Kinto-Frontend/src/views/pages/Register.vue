<template>
  <div class="bg-light min-vh-100 d-flex flex-row align-items-center">
    <CContainer>
      <CRow class="justify-content-center">
        <CCol :md="12" :lg="10" :xl="6">
          <CCard class="mx-4 mx-sm-0">
            <CCardBody class="p-4">
              <CForm @submit.prevent="submit">
                <h1 class="mb-5">Register</h1>
                <p class="text-medium-emphasis">
                  <i class="text-danger float-end">{{ message }}</i>
                </p>
                <CInputGroup class="mb-3">
                  <CInputGroupText>
                    <CIcon icon="cil-user" />
                  </CInputGroupText>
                  <CFormInput
                    placeholder="Fullname"
                    autocomplete="fullname"
                    v-model="fullname"
                    required
                  />
                </CInputGroup>
                <CInputGroup class="mb-3">
                  <CInputGroupText>@</CInputGroupText>
                  <CFormInput
                    placeholder="Email"
                    autocomplete="email"
                    v-model="email"
                    required
                    type="email"
                  />
                </CInputGroup>
                <CInputGroup class="mb-3">
                  <CInputGroupText>
                    <CIcon icon="cil-lock-locked" />
                  </CInputGroupText>
                  <CFormInput
                    type="password"
                    placeholder="Password"
                    autocomplete="new-password"
                    v-model="password"
                    required
                  />
                </CInputGroup>
                <CInputGroup class="mb-3">
                  <CInputGroupText>
                    <CIcon icon="cil-lock-locked" />
                  </CInputGroupText>
                  <CFormInput
                    type="password"
                    placeholder="Repeat password"
                    autocomplete="new-password"
                    v-model="repeatPassword"
                    required
                  />
                </CInputGroup>
                <div class="d-grid">
                  <CButton color="success" type="submit"
                    >Create Account</CButton
                  >
                </div>
              </CForm>
            </CCardBody>
          </CCard>
        </CCol>
      </CRow>
    </CContainer>
  </div>
  <CModal
    ref="my-modal"
    backdrop="static"
    :visible="xlDemo"
    @close="
      () => {
        xlDemo = false
      }
    "
  >
    <CModalHeader>
      <CModalTitle>Chose organization</CModalTitle>
    </CModalHeader>
    <CModalBody>
      <div class="mb-5">
        <label for="createAt" class="form-label text">Organization:</label>
        <i class="text-danger float-end">{{ message }}</i>
        <v-select
          v-model="organizationSelected"
          :options="options"
          label="name"
          :reduce="(name) => name.id"
        />
      </div>
    </CModalBody>
    <CModalFooter>
      <CButton
        color="secondary"
        @click="
          () => {
            xlDemo = false
          }
        "
      >
        Close
      </CButton>
      <CButton color="primary" @click="handleRegister()">Register</CButton>
    </CModalFooter>
  </CModal>
  <Loading v-model:active="isLoading" />
</template>

<script>
import UserRepository from '@/Repositories/UserRepository'
import OrganizationRepository from '@/Repositories/OrganizationRepository'
export default {
  name: 'Register',
  data() {
    return {
      fullname: '',
      email: '',
      password: '',
      repeatPassword: '',
      message: '',
      organizationSelected: 'Chose organization...',
      xlDemo: false,
      isLoading: false,
      options: [],
    }
  },
  methods: {
    async submit() {
      if (this.password != this.repeatPassword) {
        this.message = 'Repeat password & Password need in the same!'
      } else {
        this.xlDemo = true
      }
    },
    async handleRegister() {
      if (this.organizationSelected == 'Chose organization...') {
        this.message = 'Please select a organization'
      } else {
        this.isLoading = true
        await UserRepository.register({
          fullname: this.fullname,
          password: this.password,
          email: this.email,
          orgId: this.organizationSelected,
        })
          .then((res) => {
            setTimeout(() => {
              if (res.status == 200) {
                this.isLoading = false
                this.$swal.fire(
                  'Register Success!',
                  'Now go to login!',
                  'success',
                )
                this.$router.push('/pages/login')
              }
            }, 2000)
          })
          .catch((error) => {
            setTimeout(() => {
              this.isLoading = false
              this.xlDemo = false
            }, 1500)
            this.$swal.fire('Register failed!', 'Please check again!', 'error')
            if (error.response.this.error == this.email + ' already exits!') {
              this.message = error.response.this.error
            } else {
              this.message = 'The system is crashing, please try again later'
            }
          })
      }
    },
    getOrganizationRepository() {
      OrganizationRepository.getAllOrganizations().then((response) => {
        this.options = response.data
      })
    },
  },
  created() {
    this.getOrganizationRepository()
  },
  watch: {
    organizationSelected(newQuestion, oldQuestion) {
      if (newQuestion != oldQuestion) {
        if (newQuestion !== 'Chose organization...') {
          this.organizationSelected = newQuestion
        }
      }
    },
  },
}
</script>
