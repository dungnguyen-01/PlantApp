<template>
  <CRow>
    <CCol>
      <CCard>
        <CCardHeader> <CIcon icon="cil-drop" /> Detail User </CCardHeader>
        <CCardBody>
          <CRow>
            <labe class="form-label text">- Id: {{ userId }}</labe>
            <labe class="form-label text">- Email: {{ email }}</labe>
            <labe class="form-label text">- Full Name: {{ fullname }}</labe>
            <labe class="form-label text">- Role: {{ role }}</labe>
            <labe class="form-label text"
              >- Organization: {{ organization }}</labe
            >
            <labe class="form-label text"
              >- Date create: {{ this.$formatDate.formatDate(createAt) }}</labe
            >
            <labe class="form-label text"
              >- Date update: {{ this.$formatDate.formatDate(updateAt) }}</labe
            >
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
              @click="editUser(userId)"
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
import UserRepository from '@/Repositories/UserRepository'
import * as icon from '@coreui/icons'
export default {
  name: 'DetailUser',
  data() {
    return {
      userId: '',
      email: '',
      fullname: '',
      role: '',
      organization: '',
      createAt: '',
      updateAt: '',
      isLoading: true,
    }
  },
  computed: {
    id() {
      return this.$route.params.id
    },
  },
  methods: {
    getDetailUser() {
      UserRepository.getUserById(this.id)
        .then((response) => {
          if (response.status === 200) {
            this.isLoading = false
            this.userId = response.data.id
            this.email = response.data.email
            this.fullname = response.data.fullname
            this.role = response.data.role.roleName
            this.organization = response.data.organization.name
            this.createAt = response.data.createAt
            this.updateAt = response.data.updateAt
          }
        })
        .catch((error) => {
          this.isLoading = false
          if (error.response.error == 'Access is denied') {
            this.$router.push('/pages/500')
          } else {
            this.$swal.fire(
              'Warning!',
              'The system is experiencing an error, please reload the page',
              'warning',
            )
          }
        })
    },
    backToList() {
      this.$router.push('/User/list-User')
    },
    editUser(id) {
      this.$router.push(`/User/form-User/${id}`)
    },
  },
  created() {
    this.getDetailUser()
  },
  setup() {
    return {
      icon,
    }
  },
}
</script>
