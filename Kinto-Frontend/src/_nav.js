/* eslint-disable */
export default [
  // plant
  {
    component: 'CNavTitle',
    name: 'Menu',
  },
  {
    component: 'CNavItem',
    name: 'Plant',
    to: '/plant/list-plant',
    icon: 'cilPlant',
  },
  // plant type
  {
    component: 'CNavItem',
    name: 'Plant Type',
    to: '/plant_type/list-plant_type',
    icon: 'cilFlower',
  },
  // location
  {
    component: 'CNavItem',
    name: 'Location',
    to: '/location/list-location',
    icon: 'cilLocationPin',
  },
  // plant type
  {
    component: 'CNavTitle',
    name: 'Administration',
  },
  {
    component: 'CNavItem',
    name: 'Organization',
    to: '/organization/list-organization',
    icon: 'cilHouse',
  },
  // user
  {
    component: 'CNavItem',
    name: 'User',
    to: '/user/list-user',
    icon: 'cilPeople',
  },
]
