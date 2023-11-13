import { INavData } from '@coreui/angular';

export const navItems: INavData[] = [
  {
    name: 'Dashboard',
    url: '/dashboard',
    iconComponent: { name: 'cil-speedometer' },
  },
  {
    title: true,
    name: 'USER MANAGEMENT'
  },
  {
    name: 'Employee',
    url: '/employee',
    iconComponent: { name: 'cilWc' },
    children: [
      {
        name: 'Onboard',
        url: '/employee/onboard',
        iconComponent: { name: 'cilPlus' },
      },
      {
        name: 'Edit',
        url: '/employee/edit',
        iconComponent: { name: 'cilPen' },
      },
      {
        name: 'View',
        url: '/employee/view',
        iconComponent: { name: 'cilList' },
      }
    ]
  },
  {
    name: 'Customer',
    url: '/customer',
    iconComponent: { name: 'cilPeople' },
    children: [
      {
        name: 'View',
        url: '/employee/view',
        iconComponent: { name: 'cilList' },
      }
    ]
  },
  {
    title: true,
    name: 'INVENTORY MANAGEMENT'
  },
  {
    name: 'Product',
    url: '/product',
    iconComponent: { name: 'cilFactory' },
    children: [
      {
        name: 'Add New',
        url: '/product/add',
        iconComponent: { name: 'cilPlus' },
      },
      {
        name: 'Edit',
        url: '/product/edit',
        iconComponent: { name: 'cilPen' },
      },
      {
        name: 'View',
        url: '/product/view',
        iconComponent: { name: 'cilList' },
      }
    ]
  },
  {
    name: 'Shipping',
    url: '/shipping',
    iconComponent: { name: 'cilFlightTakeoff' },
    children: [
      {
        name: 'Tracker',
        url: '/shipping/track',
        iconComponent: { name: 'cilTruck' },
      },
      {
        name: 'Delivery Locations',
        url: '/shipping/delivery',
        iconComponent: { name: 'cilLocationPin' },
      }
    ]
  },
  {
    title: true,
    name: 'ORDER MANAGEMENT'
  },
  {
    name: 'Orders',
    url: '/order',
    iconComponent: { name: 'cilListRich' },
    children: [
      {
        name: 'View',
        url: '/order/view',
        iconComponent: { name: 'cilList' },
      },
      {
        name: 'Edit',
        url: '/order/edit',
        iconComponent: { name: 'cilPen' },
      }
    ]
  },
  {
    name: 'Analytics',
    url: '/analysis',
    iconComponent: { name: 'cilChartPie' },
    badge: {
      color: 'danger',
      text: 'NA'
    }
  },
  {
    title: true,
    name: 'SALES'
  },
  {
    name: 'POS',
    url: '/pos',
    iconComponent: { name: 'cilCash' },
    badge: {
      color: 'info',
      text: 'NEW'
    }
  },
  {
    name: 'Online',
    url: '/online',
    iconComponent: { name: 'cilCreditCard' },
  },
  {
    title: true,
    name: 'PROMOTIONAL MARKETING'
  },
  {
    name: 'Coupons',
    url: '/coupon',
    iconComponent: { name: 'cilGift' },
  },
  {
    name: 'Offers',
    url: '/offer',
    iconComponent: { name: 'cilDollar' },
  },
  {
    title: true,
    name: 'SUPPORT'
  },
  
];
