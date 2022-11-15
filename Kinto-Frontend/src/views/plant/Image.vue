<template>
  <CRow>
    <div class="mt-3">
      <h5>3. Image</h5>
      <form>
        <div class="form-group">
          <div class="border p-2 mt-3">
            <div>
              <div class="table-responsive-lg">
                <table class="table table-striped">
                  <thead class="table-dark">
                    <tr>
                      <th>ID.</th>
                      <th>Image</th>
                      <th>Name</th>
                      <th>Date create</th>
                      <th>Action</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr v-for="(item, index) in images" :key="index">
                      <td :refs="'title' + item.id">{{ index + 1 }}</td>
                      <td style="width: 200px">
                        <img
                          :src="item.objectName"
                          style="width: 100%"
                          class="img-fluid border border-dark h-25"
                          @click="showModal(item.objectName)"
                        />
                      </td>
                      <td>{{ item.name }}</td>
                      <td>
                        <p class="mb-0">
                          {{ this.$formatDate.formatDate(item.createAt) }}
                        </p>
                      </td>
                      <td>
                        <div
                          class="btn btn-outline-danger rounded-pill p-0 px-4"
                          title="delete"
                          @click="deleteImage(item.id, index)"
                        >
                          <CIcon :icon="icon.cilTrash" size="m" />
                        </div>
                      </td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </div>
      </form>
      <CModal
        ref="my-modal"
        size="xl"
        :visible="xlDemo"
        @close="
          () => {
            xlDemo = false
          }
        "
      >
        <CModalHeader>
          <CModalTitle>Show image</CModalTitle>
        </CModalHeader>
        <CModalBody>
          <div>
            <img
              :src="linkImg"
              alt="wait"
              class="w-75 rounded mx-auto d-block"
            />
          </div>
        </CModalBody>
      </CModal>
    </div>
  </CRow>
</template>

<script>
import ImageRepository from '../../Repositories/ImageRepository'
import * as icon from '@coreui/icons'
export default {
  name: 'image',
  props: ['images'],
  data() {
    return {
      linkImg: '',
      xlDemo: false,
      organization: this.$store.state.auth.user.user.organization,
    }
  },
  methods: {
    deleteImage(imageId, index) {
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
          ImageRepository.deleteImageById(imageId).then(() => {
            // eslint-disable-next-line vue/no-mutating-props
            this.images.splice(index, 1)
          })
          this.$swal.fire(
            'Deleted!',
            'You have successfully deleted.',
            'success',
          )
        }
      })
    },
    showModal(img) {
      this.linkImg = img
      this.xlDemo = true
    },
  },
  setup() {
    return {
      icon,
    }
  },
}
</script>
