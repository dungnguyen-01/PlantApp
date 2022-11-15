<template>
  <CSidebar
    position="fixed"
    :unfoldable="sidebarUnfoldable"
    :visible="sidebarVisible"
    @visible-change="
      (event) =>
        $store.commit({
          type: 'updateSidebarVisible',
          value: event,
        })
    "
  >
    <CSidebarBrand>
      <i class="text-center"
        ><h6 class="mt-3 text-info">Welcome!</h6>
        <p class="text-warning">
          {{ this.$store.state.auth.user.user.fullname }}
        </p>
      </i>
    </CSidebarBrand>
    <AppSidebarNav />
    <CSidebarToggler
      class="d-none d-lg-flex"
      @click="$store.commit('toggleUnfoldable')"
    />
  </CSidebar>
</template>

<script>
import { computed } from 'vue'
import { useStore } from 'vuex'
import { AppSidebarNav } from './AppSidebarNav'
import { logoNegative } from '@/assets/brand/logo-negative'
import { sygnet } from '@/assets/brand/sygnet'
export default {
  name: 'AppSidebar',
  components: {
    AppSidebarNav,
  },
  setup() {
    const store = useStore()
    return {
      logoNegative,
      sygnet,
      sidebarUnfoldable: computed(() => store.state.sidebarUnfoldable),
      sidebarVisible: computed(() => store.state.sidebarVisible),
    }
  },
}
</script>
