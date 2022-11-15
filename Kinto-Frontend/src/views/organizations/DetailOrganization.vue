<template>
  <CRow>
    <CCol>
      <CCard>
        <CCardHeader>
          <CIcon icon="cil-drop" /> Detail Organization
        </CCardHeader>
        <CCardBody>
          <CRow>
            <labe class="form-label text">- Id: {{ organizationId }}</labe>
            <labe class="form-label text">- Name: {{ name }}</labe>
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
              @click="editOrganization(organizationId)"
              :hidden="name == 'KintoVN'"
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
import OrganizationRepository from '@/Repositories/OrganizationRepository'
import * as icon from '@coreui/icons'
export default {
  name: 'Detail Organization',
  data() {
    return {
      organizationId: '',
      name: '',
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
    getDetailOrganization() {
      OrganizationRepository.getOrganizationById(this.id)
        .then((response) => {
          if (response.status === 200) {
            this.isLoading = false
            this.organizationId = response.data.id
            this.name = response.data.name
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
      this.$router.push('/organization/list-organization')
    },
    editOrganization(id) {
      this.$router.push(`/organization/form-organization/${id}`)
    },
  },
  created() {
    this.getDetailOrganization()
  },
  setup() {
    return {
      icon,
    }
  },
}
</script>
