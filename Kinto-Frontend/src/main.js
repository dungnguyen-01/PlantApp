import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import formatDate from './services/format_date'
import VueSweetalert2 from 'vue-sweetalert2'
import 'sweetalert2/dist/sweetalert2.min.css'
import 'vue-select/dist/vue-select.css'
import Loading from 'vue-loading-overlay'
import 'vue-loading-overlay/dist/vue-loading.css'

import CoreuiVue from '@coreui/vue'
import CIcon from '@coreui/icons-vue'
import { iconsSet as icons } from '@/assets/icons'
import DocsCallout from '@/components/DocsCallout'
import DocsExample from '@/components/DocsExample'
import vSelect from 'vue-select'
import Dropdown from 'vue-simple-search-dropdown'
import Boxicon from 'boxicons'
import gAuth from 'vue3-google-auth'
import vue3GoogleLogin from 'vue3-google-login'
import axios from 'axios'

const user = JSON.parse(localStorage.getItem('user'))

function isTokenExpired() {
  let token = user.accessToken
  const expiry = JSON.parse(atob(token.split('.')[1])).exp
  return Math.floor(new Date().getTime() / 1000) >= expiry
}
axios.interceptors.response.use(
  (response) => {
    return response
  },
  (error) => {
    if (isTokenExpired() == true || error.response.status == 401) {
      router.push('/pages/login')
      return ''
    }
    return Promise.reject(error)
  },
)

const GAuth = gAuth.createGAuth({
  clientId:
    '1043285007254-1l9vn7oq04sdm8lt4f2ldvioj82n69n3.apps.googleusercontent.com',
})

const app = createApp(App)
app.use(GAuth)
app.use(vue3GoogleLogin, {
  clientId:
    '1043285007254-1l9vn7oq04sdm8lt4f2ldvioj82n69n3.apps.googleusercontent.com',
})

app.use(store)
app.use(router)
app.use(CoreuiVue)
app.use(VueSweetalert2)
app.use(Dropdown)
app.provide('icons', icons)
app.component('CIcon', CIcon)
app.component('DocsCallout', DocsCallout)
app.component('DocsExample', DocsExample)
app.component('v-select', vSelect)
app.component('Boxicon', Boxicon)
app.component('Loading', Loading)
app.component('v-select', vSelect)
app.config.globalProperties.$formatDate = formatDate

app.mount('#app')
