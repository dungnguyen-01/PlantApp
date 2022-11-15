<template>
  <!-- Farm Diary -->
  <CRow>
    <div class="mt-3">
      <h5>2. Farm Diaries:</h5>
      <div class="form-group">
        <div class="border p-2 mt-3">
          <div>
            <div class="table-responsive-lg">
              <table class="table table-striped">
                <thead class="table-dark">
                  <tr>
                    <th>ID.</th>
                    <th>Farm Diary</th>
                    <th>Date create</th>
                    <th>Date update</th>
                    <th></th>
                    <th></th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="(item, index) in farmDiaries" :key="index">
                    <td>{{ index + 1 }}</td>
                    <td>
                      {{ item.name }}
                    </td>
                    <td>
                      <p class="mb-0">
                        {{ this.$formatDate.formatDate(item.createAt) }}
                      </p>
                    </td>
                    <td>
                      <p class="mb-0">
                        {{ this.$formatDate.formatDate(item.updateAt) }}
                      </p>
                    </td>
                    <td>
                      <div
                        id="show-btn"
                        class="btn btn-outline-warning rounded-pill p-0 px-4 mx-2"
                        title="edit"
                        v-show="item.id != null"
                        @click="editFarmDiary(item.id)"
                      >
                        <CIcon :icon="icon.cilPen" size="m" />
                      </div>
                    </td>
                    <td>
                      <div
                        class="btn btn-outline-danger rounded-pill p-0 px-4"
                        title="delete"
                        @click="deleteFarmDiary(item.id, index)"
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
    </div>

    <CModal
      :visible="visibleLiveDemo"
      @close="
        () => {
          visibleLiveDemo = false
        }
      "
    >
      <CModalHeader
        dismiss
        @close="
          () => {
            visibleLiveDemo = false
          }
        "
      >
        <CModalTitle>Edit Farm Diary</CModalTitle>
      </CModalHeader>
      <CModalBody>
        <div>
          <label for="id" class="form-label text">Id:</label>
          <input
            type="text"
            class="form-control"
            placeholder="Auto increment code..."
            name="id"
            v-model="farmDiary.id"
            readonly
          />
          <label for="name" class="form-label text">Name:</label>
          <textarea
            type="text"
            class="form-control"
            placeholder="Name Farm Diary..."
            name="name"
            rows="5"
            v-model="farmDiary.name"
          />
        </div>
      </CModalBody>
      <CModalFooter>
        <CButton
          color="secondary"
          @click="
            () => {
              visibleLiveDemo = false
            }
          "
        >
          Close
        </CButton>
        <CButton color="primary" @click="updateFarmDiary()">Save</CButton>
      </CModalFooter>
    </CModal>
  </CRow>
</template>

<script>
import FarmDiaryRepository from '../../Repositories/FarmDiaryRepository'
import * as icon from '@coreui/icons'
import { ref } from 'vue'
export default {
  name: 'FarmDiary',
  props: ['farmDiaries'],
  data() {
    return {
      farmDiary: {},
      organization: this.$store.state.auth.user.user.organization,
    }
  },
  methods: {
    editFarmDiary(id) {
      this.visibleLiveDemo = true
      this.getFarmDiaryById(id)
    },
    getFarmDiaryById(id) {
      FarmDiaryRepository.getFarmDiaryById(id).then((res) => {
        this.farmDiary = res.data
      })
    },
    updateFarmDiary() {
      FarmDiaryRepository.updateFarmDiary(this.farmDiary.id, {
        id: this.farmDiary.id,
        name: this.farmDiary.name,
        organization: this.organization,
      })
        .then(() => {
          this.visibleLiveDemo = false
          window.location.reload()
          this.$forceUpdate()
        })
        .catch((err) => {
          console.log(err)
        })
    },
    // farmDiary
    deleteFarmDiary(farmDiaryId, index) {
      if (farmDiaryId == null) {
        // eslint-disable-next-line vue/no-mutating-props
        this.farmDiaries.splice(this.farmDiaries.indexOf(farmDiaryId), 1)
      } else {
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
            this.$swal.fire(
              'Deleted!',
              'You have successfully deleted.',
              'success',
            )
            FarmDiaryRepository.deleteFarmDiaryById(farmDiaryId).then(() => {
              // eslint-disable-next-line vue/no-mutating-props
              this.farmDiaries.splice(index, 1)
            })
          }
        })
      }
    },
  },
  setup() {
    const visibleLiveDemo = ref(false)
    return {
      icon,
      visibleLiveDemo,
    }
  },
}
</script>
