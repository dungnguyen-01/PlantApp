<template>
  <CRow>
    <CCol>
      <CCard>
        <form
          @submit.prevent="
            this.$route.params.id == 'null' ? createUser() : updateUser()
          "
        >
          <CCardHeader>
            <CIcon icon="cil-drop" />
            Form User
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
                  <label for="fullname" class="form-label text"
                    >Fullname:</label
                  >
                  <i class="text-danger">{{ fullnameError }}</i>
                  <input
                    type="text"
                    class="form-control"
                    placeholder="fullname..."
                    name="fullname"
                    v-model="fullname"
                    required
                  />
                  <label for="email" class="form-label text">Email:</label>
                  <i class="text-danger">{{ emailError }}</i>
                  <input
                    type="email"
                    class="form-control"
                    placeholder="email..."
                    name="email"
                    v-model="email"
                    :readonly="this.$route.params.id != 'null'"
                    required
                  />
                </div>
                <div class="col-md-6">
                  <label for="password" class="form-label text"
                    >Password:</label
                  >
                  <i class="text-danger">{{ passwordError }}</i>
                  <input
                    type="password"
                    class="form-control"
                    placeholder="password..."
                    name="password"
                    v-model="password"
                    :required="this.$route.params.id == 'null'"
                  />
                  <label for="confirmPassword" class="form-label text"
                    >Confirm Password:</label
                  >
                  <i class="text-danger">{{ confirmPasswordError }}</i>
                  <input
                    type="password"
                    class="form-control"
                    placeholder="confirm password..."
                    name="confirmPassword"
                    v-model="confirmPassword"
                    :required="this.$route.params.id == 'null'"
                  />
                  <label for="createAt" class="form-label text"
                    >Organization:</label
                  >
                  <i class="text-danger">{{ organizationError }}</i>
                  <v-select
                    v-model="organizationSelected"
                    :options="options"
                    label="name"
                    :reduce="(name) => name.id"
                  />
                </div>
                <label for="createAt" class="form-label text">Role:</label>
                <div class="form-control d-flex">
                  <div
                    class="form-check form-switch me-3"
                    v-for="(role, index) in roles"
                    :key="index"
                    :hidden="user.email == 'info.kintovn@gmail.com'"
                  >
                    <input
                      name="role"
                      class="form-check-input"
                      type="radio"
                      :value="role.id"
                      :checked="
                        role.id == vCheck ? role.id == vCheck : index == 2
                      "
                      :v-model="vCheck"
                      @change="getValueRole($event)"
                    />
                    <label class="form-check-label" for="{{role.id}}">{{
                      role.roleName
                    }}</label>
                  </div>
                  <i>{{ role.roleName }}</i>
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
import UserRepository from '@/Repositories/UserRepository'
import RoleRepository from '@/Repositories/RoleRepository'
import OrganizationRepository from '@/Repositories/OrganizationRepository'
import * as icon from '@coreui/icons'

export default {
  name: 'FormUser',
  data() {
    return {
      id: '',
      fullname: '',
      email: '',
      password: '',
      confirmPassword: '',
      createAt: '',
      updateAt: '',
      message: '',
      vCheck: '',
      organizationSelected: 'Chose organization...',
      isLoading: false,

      emailError: '',
      fullnameError: '',
      passwordError: '',
      confirmPasswordError: '',
      organizationError: '',
      organization: {},
      user: {},
      role: {},
      roles: [],
      options: [],
    }
  },
  computed: {
    userId() {
      return this.$route.params.id
    },
  },
  methods: {
    getUserById() {
      if (this.userId !== 'null') {
        this.isLoading = true
        UserRepository.getUserById(this.userId)
          .then((response) => {
            if (response.status == 200) {
              this.isLoading = false
              this.fullname = response.data.fullname
              this.email = response.data.email
              this.vCheck = response.data.role.id
              this.organizationSelected = response.data.organization.id
              this.user = response.data

              this.organization = response.data.organization
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
    getRoles() {
      RoleRepository.getRoles().then((res) => {
        this.roles = res.data
        this.roles.forEach((role) => (this.vCheck = role.id))
      })
    },
    getOrganizationRepository() {
      OrganizationRepository.getAllSelectByUserOrganizations().then(
        (response) => {
          this.options = response.data
        },
      )
    },
    createUser() {
      if (this.organizationSelected == 'Chose organization...') {
        this.message = 'Please fix the errors below.'
        this.organizationError = ' Please select a organization'
      } else if (this.password != this.confirmPassword) {
        this.confirmPasswordError = ' password & confirm password in the same!'
        this.passwordError = ' password & confirm password in the same!'
        this.emailError = ''
        this.fullnameError = ''
        this.organizationError = ''
      } else {
        this.isLoading = true
        UserRepository.createUser({
          fullname: this.fullname,
          password: this.password,
          email: this.email,
          organization: this.organization,
          role: this.role,
        })
          .then((response) => {
            if (response.status == 200) {
              this.isLoading = false
              this.$swal.fire('Success!', 'Create successfully!', 'success')
              this.$router.push('/user/list-user')
            }
          })
          .catch((error) => {
            this.isLoading = false
            this.$swal.fire('Failed!', 'Please check again!', 'error')
            if (error.response.data.error == this.email + ' already exists') {
              this.message = error.response.data.error
            } else {
              this.message = 'The system is crashing, please try again later'
            }
          })
      }
    },
    updateUser() {
      if (
        this.fullname == '' ||
        this.organizationSelected == 'Chose organization...' ||
        this.email == ''
      ) {
        if (this.organizationSelected == 'Chose organization...') {
          this.organizationError = ' Please select a organization'
        } else this.organizationError = ''
        if (this.fullname == '') {
          this.fullnameError = 'Fullname cannot be null!'
        } else this.fullnameError = ''
        if (this.email == '') {
          this.emailError = 'Email cannot be null!'
        } else this.emailError = ''
      } else {
        this.isLoading = true
        console.log(this.organization)
        UserRepository.updateUser(this.userId, {
          fullname: this.fullname,
          email: this.email,
          organization: this.organization,
          role: this.role,
        })
          .then((res) => {
            if (res.status == 200) {
              this.isLoading = false
              this.$swal.fire('Success!', 'Update successfully!', 'success')
              this.$router.push('/user/list-user')
            }
          })
          .catch((error) => {
            this.isLoading = false
            this.$swal.fire('Failed!', 'Please check again!', 'error')
            if (error.response.data.error == this.email + ' already exists') {
              this.message = error.response.data.error
            } else {
              this.message = 'The system is crashing, please try again later'
            }
          })
      }
    },
    getValueRole(event) {
      this.vCheck = event.target.value
      RoleRepository.getRoleById(this.vCheck).then((resp) => {
        this.role = resp.data
      })
    },
    getValueRoleById() {
      RoleRepository.getRoleById(this.vCheck).then((resp) => {
        this.role = resp.data
      })
    },
    getValueOrganization(id) {
      OrganizationRepository.getOrganizationById(id).then((response) => {
        this.organization = response.data
      })
    },
    resetForm() {
      this.$router.push('/user/form-user/null')
      this.id = ''
      this.fullname = ''
      this.email = ''
      this.password = ''
      this.confirmPassword = ''
      this.createAt = ''
      this.updateAt = ''
      this.message = ''
      this.organizationSelected = 'Chose organization...'
      this.emailError = ''
      this.fullnameError = ''
      this.passwordError = ''
      this.confirmPasswordError = ''
      this.organizationError = ''
      if (this.$route.params.id == 'null') window.location.reload()
    },
    backToList() {
      this.$router.push('/user/list-User')
    },
  },
  setup() {
    return {
      icon,
    }
  },
  watch: {
    vCheck(newQuestion, oldQuestion) {
      if (oldQuestion != newQuestion) {
        this.getValueRoleById()
      }
    },
    organizationSelected(newQuestion, oldQuestion) {
      if (oldQuestion != newQuestion) {
        this.getValueOrganization(newQuestion)
      }
    },
  },
  created() {
    //this.getDetailUser()
    this.getRoles()
    this.getOrganizationRepository()
    this.getUserById()
  },
  mounted() {
    this.vCheck
  },
}
</script>
