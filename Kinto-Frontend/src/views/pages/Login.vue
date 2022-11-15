<template>
  <div class="bg-light min-vh-100 d-flex flex-row align-items-center">
    <CContainer>
      <CRow class="justify-content-center">
        <CCol :md="8">
          <CCardGroup>
            <CCard class="p-1">
              <CCardBody>
                <CForm @submit.prevent="handleLogin">
                  <h1 class="text-center">Login</h1>
                  <p class="text-medium-emphasis">Sign In to your account</p>
                  <i class="text-danger">{{ message }}</i>
                  <CInputGroup class="mb-3">
                    <CInputGroupText>
                      <CIcon icon="cil-user" />
                    </CInputGroupText>
                    <CFormInput
                      placeholder="Email..."
                      autocomplete="email"
                      v-model="email"
                      type="email"
                      required
                    />
                  </CInputGroup>
                  <CInputGroup>
                    <CInputGroupText>
                      <CIcon icon="cil-lock-locked" />
                    </CInputGroupText>
                    <CFormInput
                      type="password"
                      placeholder="Password"
                      autocomplete="current-password"
                      v-model="password"
                      required
                    />
                  </CInputGroup>
                  <a
                    color="link"
                    class="px-0 float-end"
                    @click="forgotPassword()"
                    style="cursor: pointer"
                  >
                    Forgot password?
                  </a>
                  <CRow class="mt-5">
                    <CCol :md="4">
                      <CButton color="primary" class="px-3 mb-2" type="submit">
                        Login
                      </CButton>
                    </CCol>
                    <CCol :md="8" class="text-right">
                      <CButton
                        color="warning"
                        class="px-3"
                        @click="handleLoginWithGoogle()"
                      >
                        Login with <b>Google</b>
                      </CButton>
                    </CCol>
                  </CRow>
                </CForm>
              </CCardBody>
            </CCard>
            <CCard class="text-white bg-primary py-5">
              <CCardBody class="text-center">
                <div>
                  <h2>Welcome!</h2>
                  <CButton
                    color="light"
                    variant="outline"
                    class="mt-3"
                    @click="frmRegister()"
                  >
                    Register Now!
                  </CButton>
                </div>
              </CCardBody>
            </CCard>
          </CCardGroup>
        </CCol>
      </CRow>
    </CContainer>
  </div>
  <CModal ref="my-modal" backdrop="static" :visible="xlDemo">
    <CModalHeader>
      <CModalTitle>Chose organization</CModalTitle>
    </CModalHeader>
    <CModalBody>
      <div class="mb-5">
        <label for="createAt" class="form-label text">Organization:</label>
        <i class="text-danger">{{ messageORG }}</i>
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
      <CButton color="primary" @click="changeORG()">Save changes</CButton>
    </CModalFooter>
  </CModal>
  <Loading v-model:active="isLoading" />
</template>

<script>
import UserRepository from '@/Repositories/UserRepository'
import OrganizationRepository from '@/Repositories/OrganizationRepository'
export default {
  name: 'Login',
  data() {
    return {
      email: '',
      password: '',
      message: '',
      messageORG: '',
      organizationSelected: 'Chose organization...',
      xlDemo: false,
      isLoading: false,
      options: [],
      user: {},
    }
  },

  methods: {
    handleLoginWithGoogle() {
      this.$gAuth.signIn().then((data) => {
        const formData = new FormData()
        formData.append('email', data.getBasicProfile().getEmail())
        formData.append('fullname', data.getBasicProfile().getName())
        formData.append('password', data.getBasicProfile().getId())
        UserRepository.loginWithGoogle(formData).then((resp) => {
          this.isLoading = true
          setTimeout(() => {
            this.isLoading = false
            if (resp.status == 200) {
              this.user = resp.data.user
              if (
                resp.data.user.organization.name == 'KintoVN' &&
                resp.data.user.email !== 'info.kintovn@gmail.com'
              ) {
                console.log(resp.data.user.email)
                this.xlDemo = true
              } else {
                if (resp.data.accessToken) {
                  localStorage.setItem('user', JSON.stringify(resp.data))
                  this.$swal.fire({
                    title: 'Login successfully!',
                    text: `Welcome: ${data.getBasicProfile().getName()}`,
                    icon: 'success',
                    timer: 1500,
                  })
                  setTimeout(() => {
                    this.$router.push('/')
                  }, 1500)
                }
              }
            } else {
              this.$swal.fire('Failed!', 'Login Failed!', 'error')
            }
          }, 2000)
        })
      })
    },
    handleLogin() {
      this.isLoading = true
      UserRepository.loginWithEmail({
        email: this.email,
        password: this.password,
      })
        .then((resp) => {
          setTimeout(() => {
            this.isLoading = false
            if (resp.status == 200) {
              this.user = resp.data.user
              if (
                resp.data.user.organization.name == 'KintoVN' &&
                resp.data.user.email !== 'info.kintovn@gmail.com'
              ) {
                this.xlDemo = true
              } else {
                if (resp.data.accessToken) {
                  localStorage.setItem('user', JSON.stringify(resp.data))
                  this.$swal.fire({
                    title: 'Login successfully!',
                    text: `Welcome: ${resp.data.user.fullname}`,
                    icon: 'success',
                    timer: 1500,
                  })
                  setTimeout(() => {
                    this.$router.push('/')
                  }, 1500)
                }
              }
            }
          }, 1500)
        })
        .catch((error) => {
          setTimeout(() => {
            this.isLoading = false
            this.$swal.fire('Failed!', 'Login Failed!', 'error')
            this.message = error.response.data.error
          }, 2000)
        })
    },
    frmRegister() {
      this.$router.push('/pages/register')
    },
    forgotPassword() {
      this.$router.push('/pages/forgot-password')
    },
    getOrganizationRepository() {
      OrganizationRepository.getAllOrganizations().then((response) => {
        this.options = response.data
      })
    },
    changeORG() {
      this.xlDemo = true
      if (this.organizationSelected == 'Chose organization...') {
        this.messageORG = 'Please select a organization'
      } else {
        this.isLoading = true
        const formData = new FormData()
        formData.append('id', this.user.id)
        formData.append('orgId', this.organizationSelected)
        UserRepository.updateORG(formData)
          .then((response) => {
            this.isLoading = false
            if (response.status === 200) {
              this.xlDemo = false
              this.$swal.fire('Success!', 'Now go to login again!', 'success')
              this.$router.push('/pages/login')
            }
          })
          .catch((error) => {
            this.isLoading = false
            this.$swal.fire('Failed!', 'Please check again!', 'error')
            this.messageORG = error.response.this.error
          })
      }
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
