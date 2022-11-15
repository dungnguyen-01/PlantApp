import { h, resolveComponent } from 'vue'
import { createRouter, createWebHistory } from 'vue-router'

import DefaultLayout from '@/layouts/DefaultLayout'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: DefaultLayout,
    redirect: '/plant/list-plant',
    children: [
      // plant
      {
        path: '/plant/list-plant',
        name: 'Plant',
        component: () => import('@/views/plant/Plant.vue'),
      },
      {
        path: '/plant/form-plant/:id',
        name: 'Form Plant',
        component: () => import('@/views/plant/FormPlant.vue'),
      },
      {
        path: '/plant/plant-detail/:id',
        name: 'Detail Plant',
        component: () => import('@/views/plant/PlantDetail.vue'),
      },
      // location
      {
        path: '/location/list-location',
        name: 'Location',
        component: () => import('@/views/location/Location.vue'),
      },
      {
        path: '/location/form-location/:id',
        name: 'Form Location',
        component: () => import('@/views/location/FormLocation.vue'),
      },
      {
        path: '/location/detail-location/:id',
        name: 'Detail Location',
        component: () => import('@/views/location/DetailLocation.vue'),
      },
      // plantType
      {
        path: '/plant_type/list-plant_type',
        name: 'Plant Type',
        component: () => import('@/views/plantType/PlantType.vue'),
      },
      {
        path: '/plant_type/form-plant_type/:id',
        name: 'Form Plant Type',
        component: () => import('@/views/plantType/FormPlantType.vue'),
      },
      {
        path: '/plant_type/detail-plant_type/:id',
        name: 'Detail Plant Type',
        component: () => import('@/views/plantType/DetailPlantType.vue'),
      },
      // organization
      {
        path: '/organization/list-organization',
        name: 'Organization',
        component: () => import('@/views/organizations/Organization.vue'),
      },
      {
        path: '/organization/form-organization/:id',
        name: 'Form Organization',
        component: () => import('@/views/organizations/FormOrganization.vue'),
      },
      {
        path: '/organization/detail-organization/:id',
        name: 'Detail Organization',
        component: () => import('@/views/organizations/DetailOrganization.vue'),
      },
      // user
      {
        path: '/user/list-user',
        name: 'User',
        component: () => import('@/views/users/User.vue'),
      },
      {
        path: '/user/form-user/:id',
        component: () => import('@/views/users/FormUser.vue'),
      },
      {
        path: '/user/detail-user/:id',
        name: 'Detail User',
        component: () => import('@/views/users/UserDetail.vue'),
      },
    ],
  },
  {
    path: '/pages',
    redirect: '/pages/404',
    name: 'Pages',
    component: {
      render() {
        return h(resolveComponent('router-view'))
      },
    },
    children: [
      {
        path: '404',
        name: 'Page404',
        component: () => import('@/views/pages/Page404'),
      },
      {
        path: '/:pathMatch(.*)*',
        name: 'NotFound',
        component: () => import('@/views/pages/Page404'),
      },
      {
        path: '500',
        name: 'Page500',
        component: () => import('@/views/pages/Page500'),
      },
      {
        path: 'login',
        name: 'Login',
        component: () => import('@/views/pages/Login'),
      },
      {
        path: 'forgot-password',
        name: 'ForgotPassword',
        component: () => import('@/views/pages/ForgotPassword'),
      },
      {
        path: 'reset-password',
        name: 'ResetPassword',
        component: () => import('@/views/pages/ResetPassword'),
      },
      {
        path: 'register',
        name: 'Register',
        component: () => import('@/views/pages/Register'),
      },
    ],
  },
]

const router = createRouter({
  //history: createWebHashHistory(),
  history: createWebHistory(),
  routes,
  scrollBehavior() {
    // always scroll to top
    return { top: 0 }
  },
})

router.beforeEach((to, from, next) => {
  const publicPages = [
    '/pages/login',
    '/pages/register',
    '/pages/forgot-password',
    '/pages/reset-password',
  ]
  const authRequired = !publicPages.includes(to.path)
  const loggedIn = localStorage.getItem('user')

  // trying to access a restricted page + not logged in
  // redirect to login page
  if (authRequired && !loggedIn) {
    next('/pages/login')
  } else {
    next()
  }
})

export default router
