<template>
  <CRow>
    <CCol>
      <CCard>
        <CCardHeader> <CIcon icon="cil-drop" /> List Location </CCardHeader>
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
                v-on:click="addUser()"
                class="btn btn-info text-light float-end mb-2"
              >
                Add User
              </CButton>
            </div>
          </CRow>
          <CRow>
            <div class="table-responsive-lg">
              <table class="table">
                <thead class="table-dark">
                  <tr>
                    <th>No.</th>
                    <th>Email</th>
                    <th>Fullname</th>
                    <th>Role</th>
                    <th>Organization</th>
                    <th>Create</th>
                    <th>Update</th>
                    <th></th>
                    <th></th>
                    <th></th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="(user, index) in users.content" :key="index">
                    <td>{{ index + 1 }}</td>
                    <td>{{ user.email }}</td>
                    <td style="min-width: 150px">{{ user.fullname }}</td>
                    <td>{{ user.role.roleName }}</td>
                    <td style="min-width: 150px">
                      {{ user.organization.name }}
                    </td>
                    <td>
                      {{ this.$formatDate.formatDate(user.createAt) }}
                    </td>
                    <td>
                      {{ this.$formatDate.formatDate(user.updateAt) }}
                    </td>
                    <td>
                      <button
                        class="btn btn-outline-info rounded-pill p-0 px-4 ml-1"
                        title="detail"
                        v-on:click="detailUser(user.id)"
                      >
                        <CIcon :icon="icon.cilCompress" size="m" />
                      </button>
                    </td>
                    <td>
                      <button
                        class="btn btn-outline-warning rounded-pill p-0 px-4 mx-2"
                        title="edit"
                        v-on:click="updateUser(user.id)"
                      >
                        <CIcon :icon="icon.cilPen" size="m" />
                      </button>
                    </td>
                    <td>
                      <button
                        class="btn btn-outline-danger rounded-pill p-0 px-4"
                        title="delete"
                        :hidden="user.email == 'info.kintovn@gmail.com'"
                        @click="deleteUser(user.id)"
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
                v-for="(page, index) in users.totalPages"
                :key="index"
                @click="setPage(page)"
                :class="page == pageNumber ? 'active' : ''"
              >
                {{ page }}
              </CPaginationItem>
              <CPaginationItem
                style="cursor: pointer"
                v-show="pageNumber != users.totalPages"
                v-bind:hidden="users.totalPages == 0"
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
import UserRepository from '@/Repositories/UserRepository'
import * as icon from '@coreui/icons'
export default {
  name: 'User',
  data() {
    return {
      users: [],
      user: '',
      isLoading: true,
      selectSizePage: 15,
      pageNumber: 1,
      options: [
        { text: '15', value: '15' },
        { text: '25', value: '25' },
        { text: '50', value: '50' },
        { text: '100', value: '100' },
      ],
    }
  },
  methods: {
    fetchUsers() {
      console.log(this.pageNumber)
      UserRepository.getUsers(
        `?size=${this.selectSizePage}&page=${this.pageNumber - 1}`,
      )
        .then((response) => {
          if (response.status === 200) {
            setTimeout(() => {
              this.isLoading = false
            }, 1000)
            this.users = response.data
          }
        })
        .catch((error) => {
          this.isLoading = false
          if (error.response.error == 'Access is denied') {
            this.$router.push('/pages/400')
          } else {
            this.$swal.fire(
              'Warning!',
              'The system is experiencing an error, please reload the page',
              'warning',
            )
          }
        })
    },
    detailUser(id) {
      this.$router.push(`/user/detail-user/${id}`)
    },
    addUser() {
      this.$router.push('/user/form-user/null')
    },
    updateUser(id) {
      this.$router.push(`/user/form-user/${id}`)
    },
    deleteUser(id) {
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
          UserRepository.deleteUserById(id)
            .then((response) => {
              if (response.status === 200) {
                this.isLoading = false
                this.fetchUsers()
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
    this.fetchUsers()
  },
  setup() {
    return {
      icon,
    }
  },
  watch: {
    pageNumber(newQuestion, oldQuestion) {
      if (oldQuestion != newQuestion) {
        this.fetchUsers()
      }
    },
  },
}
</script>
