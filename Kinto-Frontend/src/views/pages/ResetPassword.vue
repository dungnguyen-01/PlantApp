<template>
  <div class="bg-light min-vh-100 d-flex flex-row align-items-center">
    <CContainer>
      <CRow class="justify-content-center">
        <CCol :md="12" :lg="10" :xl="6">
          <CCard class="mx-4 mx-sm-0">
            <CCardBody class="p-4">
              <CForm @submit.prevent="submit">
                <h1 class="mb-5 text-center">Reset Password</h1>
                <p class="text-medium-emphasis">
                  <small class="text-muted"
                    ><i class="text-danger float-end">{{ message }}</i></small
                  >
                </p>
                <div>
                  <CInputGroup class="mb-3">
                    <CInputGroupText> ::: </CInputGroupText>
                    <CFormInput
                      type="text"
                      placeholder="Token..."
                      autocomplete="password"
                      v-model="token"
                      required
                    />
                  </CInputGroup>
                </div>
                <div>
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
                </div>
                <div>
                  <CInputGroup class="mb-3">
                    <CInputGroupText>
                      <CIcon icon="cil-lock-locked" />
                    </CInputGroupText>
                    <CFormInput
                      type="password"
                      placeholder="Password"
                      autocomplete="password"
                      v-model="password"
                      required
                    />
                  </CInputGroup>
                </div>
                <div>
                  <CInputGroup class="mb-3">
                    <CInputGroupText>
                      <CIcon icon="cil-lock-locked" />
                    </CInputGroupText>
                    <CFormInput
                      type="password"
                      placeholder="Confirm Password"
                      autocomplete="new-password"
                      v-model="confirm"
                      required
                    />
                  </CInputGroup>
                </div>
                <a
                  color="link"
                  class="ms-2"
                  @click="loginPage()"
                  style="cursor: pointer"
                >
                  Back to Login
                </a>
                <br />
                <div class="d-grid mt-4">
                  <CButton color="success" type="submit"
                    >Change passwrod</CButton
                  >
                </div>
              </CForm>
            </CCardBody>
          </CCard>
        </CCol>
      </CRow>
    </CContainer>
  </div>
  <Loading v-model:active="isLoading" />
</template>

<script>
import UserRepository from '@/Repositories/UserRepository'
export default {
  name: 'Register',
  data() {
    return {
      email: '',
      password: '',
      confirm: '',
      token: '',
      message: '',
      isLoading: false,
    }
  },
  methods: {
    async submit() {
      this.isLoading = true
      if (this.password != this.confirm) {
        this.message = 'Confirm password & Password need in the same!'
      } else {
        await UserRepository.resetPassword({
          token: this.token,
          email: this.email,
          password: this.password,
          confirm: this.confirm,
        })
          .then((res) => {
            if (res.status === 200) {
              this.isLoading = false
              this.$swal.fire(
                'Change successly!',
                'Now go to login!',
                'success',
              )
              this.message = ''
              this.$router.push('/pages/login')
            }
          })
          .catch((error) => {
            this.isLoading = false
            this.$swal.fire('Change failed!', 'Please check again!', 'error')
            this.message = error.response.this.error
          })
      }
    },
    loginPage() {
      this.$router.push('/pages/login')
    },
  },
}
</script>
