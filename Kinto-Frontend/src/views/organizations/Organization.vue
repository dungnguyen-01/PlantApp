<template>
  <CRow>
    <CCol>
      <CCard>
        <CCardHeader> <CIcon icon="cil-drop" /> List Organization</CCardHeader>
        <CCardBody>
          <CRow>
            <div>
              <select v-model="selectSizePage">
                <option
                  v-for="(option, index) in options"
                  :value="option.value"
                  :key="index"
                >
                  {{ option.text }}
                </option>
              </select>
              <span class="m-2"
                >Selected:
                <b>{{ selectSizePage }}</b>
              </span>
              <CButton
                v-on:click="addOrganization()"
                class="btn btn-info text-light float-end mb-2"
              >
                Add Organization
              </CButton>
            </div>
          </CRow>
          <CRow>
            <div class="table-responsive-lg">
              <table class="table">
                <thead class="table-dark">
                  <tr>
                    <th>No.</th>
                    <th>Name</th>
                    <th>Date create</th>
                    <th>Date update</th>
                    <th></th>
                    <th></th>
                    <th></th>
                  </tr>
                </thead>
                <tbody>
                  <tr
                    v-for="(organization, index) in organizations.content"
                    :key="index"
                  >
                    <td>{{ index + 1 }}</td>
                    <td>
                      <div class="cut_text_show">
                        <p>
                          {{ organization.name }}
                        </p>
                      </div>
                    </td>
                    <td>
                      {{ this.$formatDate.formatDate(organization.createAt) }}
                    </td>
                    <td>
                      {{ this.$formatDate.formatDate(organization.updateAt) }}
                    </td>
                    <td>
                      <button
                        class="btn btn-outline-info rounded-pill p-0 px-4 ml-1"
                        title="detail"
                        v-on:click="detailOrganization(organization.id)"
                      >
                        <CIcon :icon="icon.cilCompress" size="m" />
                      </button>
                    </td>
                    <td>
                      <button
                        class="btn btn-outline-warning rounded-pill p-0 px-4 mx-2"
                        title="edit"
                        v-on:click="updateOrganization(organization.id)"
                        :hidden="organization.name == 'KintoVN'"
                      >
                        <CIcon :icon="icon.cilPen" size="m" />
                      </button>
                    </td>
                    <td>
                      <button
                        class="btn btn-outline-danger rounded-pill p-0 px-4"
                        title="delete"
                        @click="deleteOrganization(organization.id)"
                        :hidden="organization.name == 'KintoVN'"
                      >
                        <CIcon :icon="icon.cilTrash" size="m" />
                      </button>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
          </CRow>
          <CRow>
            <CPagination
              class="pagination justify-content-center"
              aria-label="Page navigation example"
            >
              <CPaginationItem
                style="cursor: pointer"
                v-show="pageNumber != 1"
                @click="previousPage()"
                >Previous
              </CPaginationItem>
              <CPaginationItem
                style="cursor: pointer"
                v-for="(page, index) in organizations.totalPages"
                :key="index"
                @click="setPage(page)"
                :class="page == pageNumber ? 'active' : ''"
              >
                {{ page }}
              </CPaginationItem>
              <CPaginationItem
                style="cursor: pointer"
                v-show="pageNumber != organizations.totalPages"
                v-bind:hidden="organizations.totalPages == 0"
                @click="nextPage()"
                >Next</CPaginationItem
              >
            </CPagination>
          </CRow>
        </CCardBody>
      </CCard>
    </CCol>
  </CRow>
  <Loading v-model:active="isLoading" />
</template>

<script>
import OrganizationRepository from '@/Repositories/OrganizationRepository'
import * as icon from '@coreui/icons'
export default {
  name: 'Organization',
  data() {
    return {
      pageNumber: 1,
      organizations: [],
      responseSuccess: true,
      isLoading: true,
      selectSizePage: 15,
      options: [
        { text: '15', value: '15' },
        { text: '25', value: '25' },
        { text: '50', value: '50' },
        { text: '100', value: '100' },
      ],
    }
  },
  methods: {
    fetchOrganizations() {
      OrganizationRepository.getOrganizations(
        `?size=${this.selectSizePage}&page=${this.pageNumber - 1}`,
      )
        .then((response) => {
          if (response.status === 200) {
            setTimeout(() => {
              this.isLoading = false
            }, 1000)
            this.organizations = response.data
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
    detailOrganization(id) {
      this.$router.push(`/organization/detail-organization/${id}`)
    },
    addOrganization() {
      this.$router.push('/organization/form-organization/null')
    },
    updateOrganization(id) {
      this.$router.push(`/organization/form-organization/${id}`)
    },
    deleteOrganization(id) {
      this.$swal({
        title: 'Are you sure?',
        text: "You won't be able to revert this!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Yes, delete it!',
        cancelButtonText: 'No, cancel!',
        confirmButtonColor: '#55c663',
        reverseButtons: true,
      }).then((result) => {
        if (result.isConfirmed) {
          this.isLoading = true
          OrganizationRepository.deleteOrganizationById(id)
            .then((response) => {
              if (response.status === 200) {
                this.isLoading = false
                this.fetchOrganizations()
                this.$swal.fire(
                  'Deleted!',
                  'You have successfully deleted.',
                  'success',
                )
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
      })
    },
    setPage(page) {
      this.pageNumber = page
    },
    nextPage() {
      this.pageNumber = this.pageNumber + 1
    },
    previousPage() {
      this.pageNumber = this.pageNumber - 1
    },
  },
  created() {
    this.fetchOrganizations()
  },
  setup() {
    return {
      icon,
    }
  },
  watch: {
    selectSizePage(newQuestion, oldQuestion) {
      if (oldQuestion != newQuestion) {
        this.fetchOrganizations()
      }
    },
    pageNumber(newQuestion, oldQuestion) {
      if (oldQuestion != newQuestion) {
        this.fetchOrganizations()
      }
    },
  },
}
</script>
