<template>
  <CDropdown variant="nav-item">
    <CDropdownToggle placement="bottom-end" class="py-0" :caret="false">
      <CAvatar :src="avatar" size="md" />
    </CDropdownToggle>
    <CDropdownMenu class="pt-0">
      <CDropdownHeader component="h6" class="bg-light fw-semibold py-2">
        Account
      </CDropdownHeader>
      <CDropdownItem>
        <CIcon icon="cil-user" /> Fullname:
        <b>{{ user.fullname }}</b>
      </CDropdownItem>
      <CDropdownItem>
        <CIcon icon="cil-envelope-open" /> Email:
        <b>{{ user.email }}</b>
      </CDropdownItem>
      <CDropdownItem>
        <CIcon icon="cil-comment-square" /> Date create:
        <b>{{ this.$formatDate.formatDate(user.createAt) }}</b>
      </CDropdownItem>
      <CDropdownItem>
        <CIcon icon="cil-comment-square" /> Date update:
        <b>{{ this.$formatDate.formatDate(user.updateAt) }}</b>
      </CDropdownItem>
      <CDropdownHeader component="h6" class="bg-light fw-semibold py-2">
        Role
      </CDropdownHeader>
      <CDropdownItem>
        <CIcon icon="cil-shield-alt" /> Role: <b>{{ role.roleName }}</b>
      </CDropdownItem>
      <CDropdownHeader component="h6" class="bg-light fw-semibold py-2">
        Organization
      </CDropdownHeader>
      <CDropdownItem>
        <CIcon icon="cil-task" /> Name: <b>{{ organization.name }}</b>
      </CDropdownItem>
      <CDropdownDivider />
      <div class="d-flex">
        <CDropdownItem style="cursor: pointer">
          <button
            style="cursor: pointer"
            class="btn btn-outline-info"
            @click="logout"
          >
            <CIcon icon="cil-lock-locked" /> Logout
          </button>
        </CDropdownItem>
        <CDropdownItem style="cursor: pointer">
          <button
            style="cursor: pointer"
            class="btn btn-outline-warning"
            @click="showModal"
          >
            Change password
          </button>
        </CDropdownItem>
      </div>
    </CDropdownMenu>
  </CDropdown>
  <CModal
    ref="my-modal"
    size="md"
    :visible="xlDemo"
    @close="
      () => {
        xlDemo = false
      }
    "
  >
    <CModalHeader>
      <CModalTitle>Change password</CModalTitle>
    </CModalHeader>
    <CModalBody>
      <CForm @submit.prevent="handleChange">
        <p class="text-medium-emphasis">
          <small class="text-muted"
            ><i class="text-danger float-end">{{ message }}</i></small
          >
        </p>
        <div class="mb-3 mt-3">
          <label for="oldPass" class="form-label">Old password:</label>
          <input
            type="password"
            class="form-control"
            id="oldPass"
            placeholder="Enter old password..."
            v-model="oldPass"
            required
          />
        </div>
        <div class="mb-3 mt-3">
          <label for="newPass" class="form-label">New password:</label>
          <input
            type="password"
            class="form-control"
            id="newPass"
            placeholder="Enter new password..."
            v-model="newPass"
            required
          />
        </div>
        <div class="mb-3 mt-3">
          <label for="confirmPass" class="form-label">Confirm password:</label>
          <input
            type="password"
            class="form-control"
            id="confirmPass"
            placeholder="Enter confirm password..."
            v-model="confirmPass"
            required
          />
        </div>
        <br />
        <div class="d-grid">
          <CButton color="success" type="submit">Save</CButton>
        </div>
      </CForm>
    </CModalBody>
  </CModal>
</template>

<script>
import avatar from '@/assets/images/avatars/8.jpg'
import UserRepository from '@/Repositories/UserRepository'
export default {
  name: 'AppHeaderDropdownAccnt',
  setup() {
    return {
      avatar: avatar,
    }
  },
  data() {
    return {
      organization: this.$store.state.auth.user.user.organization,
      user: this.$store.state.auth.user.user,
      role: this.$store.state.auth.user.user.role,
      xlDemo: false,
      newPass: '',
      oldPass: '',
      confirmPass: '',
      message: '',
    }
  },
  methods: {
    logout() {
      this.$swal({
        title: 'Are you sure?',
        text: 'Are you sure you want to sign out?',
        icon: 'question',
        showCancelButton: true,
        confirmButtonText: 'Logout!',
        cancelButtonText: 'No, cancel!',
        confirmButtonColor: '#55c663',
        reverseButtons: true,
      }).then((result) => {
        if (result.isConfirmed) {
          localStorage.removeItem('user')
          this.$router.push('/pages/login')
          this.$swal.fire(
            'Logout!',
            'You have successfully logged out',
            'success',
          )
        }
      })
    },
    showModal() {
      this.xlDemo = true
    },
    handleChange() {
      if (this.newPass != this.confirmPass) {
        this.message = 'New password and confirm password need in the same!'
      } else if (this.oldPass == this.newPass) {
        this.message =
          'Please enter a new password that is different from the old password!'
      } else {
        var formData = new FormData()
        formData.append('new-pass', this.newPass)
        formData.append('confirm-pass', this.confirmPass)
        formData.append('old-pass', this.oldPass)
        formData.append('id', this.user.id)
        UserRepository.changePassword(formData)
          .then(() => {
            this.$swal.fire('Success!', 'Change successfully!', 'success')
            this.xlDemo = false
          })
          .catch((error) => {
            this.message = error.response.data.error
            this.xlDemo = true
          })
      }
    },
  },
}
</script>
