<template>
  <div class="bg-light min-vh-100 d-flex flex-row align-items-center">
    <CContainer>
      <CRow class="justify-content-center">
        <CCol :md="12" :lg="10" :xl="6">
          <CCard class="mx-4 mx-sm-0">
            <CCardBody class="p-4">
              <CForm @submit.prevent="submit">
                <h1 class="mb-5">Forgot Password</h1>
                <p class="text-medium-emphasis">
                  <small class="text-muted"
                    ><i class="text-danger float-end">{{ message }}</i></small
                  >
                </p>
                <i class="text-danger text"> {{ emailError }} </i>
                <input
                  class="form-control"
                  placeholder="Email"
                  autocomplete="email"
                  v-model="email"
                  required
                  type="email"
                />
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
                  <CButton color="success" type="submit">Send Mail</CButton>
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
      message: '',
      isLoading: false,
    }
  },
  methods: {
    async submit() {
      this.isLoading = true
      await UserRepository.forgotPassword({
        email: this.email,
        password: '',
      })
        .then((res) => {
          if (res.status == 200) {
            this.isLoading = false
            this.$swal.fire(
              'Success',
              'Please check email get token & follow the instructions!',
              'success',
            )
            this.message = ''
          }
        })
        .catch((error) => {
          this.isLoading = false
          this.$swal.fire('Failed!', 'Please check again!', 'error')
          if (error.response.this.error == this.email + ' not found!') {
            this.message = error.response.this.error
          } else {
            this.message = 'The system is crashing, please try again later'
          }
        })
    },
    loginPage() {
      this.$router.push('/pages/login')
    },
  },
}
</script>
