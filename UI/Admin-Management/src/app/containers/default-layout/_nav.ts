import { INavData } from '@coreui/angular';
import { Permissions } from 'src/app/service/user/permission.service'

export const navItems: INavData[] = [
  {
    name: 'Dashboard',
    url: '/dashboard',
    iconComponent: { name: 'cil-speedometer' }
  },
  {
    title: true,
    name: 'USER MANAGEMENT',
    attributes: {
      "Permission" : Permissions.MANAGE_USERS
    }
  },
  {
    name: 'Employee',
    url: '/employee',
    iconComponent: { name: 'cilWc' },
    attributes: {
      "Permission" : Permissions.MANAGE_USERS
    },
    children: [
      {
        name: 'Onboard',
        url: '/employee/onboard',
        iconComponent: { name: 'cilPlus' },
        attributes: {
          "Permission" : Permissions.EDIT_USERS
        }
      },
      {
        name: 'Edit',
        url: '/employee/edit',
        iconComponent: { name: 'cilPen' },
        attributes: {
          "Permission" : Permissions.EDIT_USERS
        }
      },
      {
        name: 'View',
        url: '/employee/view',
        iconComponent: { name: 'cilList' },
      },
      {
        name: 'Permissions',
        url: '/employee/permissions',
        iconComponent: { name: 'cilLockUnlocked' },
        attributes: {
          "Permission" : Permissions.EDIT_USERS
        }
      }
    ]
  },
  {
    name: 'Customer',
    url: '/customer',
    iconComponent: { name: 'cilPeople' },
    attributes: {
      "Permission" : Permissions.MANAGE_USERS
    },
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
    name: 'INVENTORY MANAGEMENT',
    attributes: {
      "Permission" : Permissions.MANAGE_PRODUCTS
    }
  },
  {
    name: 'Supplier',
    url: '/supplier',
    iconComponent: { name: 'cilFactory' },
    attributes: {
      "Permission" : Permissions.MANAGE_SUPPLIER
    },
    children: [
      {
        name: 'Onboard',
        url: '/supplier/onboard',
        iconComponent: { name: 'cilPlus' },
      },
      {
        name: 'View',
        url: '/supplier/view',
        iconComponent: { name: 'cilList' },
      }
    ]
  },
  {
    name: 'Product',
    url: '/product',
    iconComponent: { name: 'cilCart' },
    attributes: {
      "Permission" : Permissions.MANAGE_PRODUCTS
    },
    children: [
      {
        name: 'Add New',
        url: '/product/add',
        iconComponent: { name: 'cilPlus' },
        attributes: {
          "Permission" : Permissions.EDIT_PRODUCTS
        }
      },
      {
        name: 'Edit',
        url: '/product/edit',
        iconComponent: { name: 'cilPen' },
        attributes: {
          "Permission" : Permissions.EDIT_PRODUCTS
        }
      },
      {
        name: 'View',
        url: '/product/view',
        iconComponent: { name: 'cilList' },
      },
      {
        name: 'Category',
        url: '/product/category',
        iconComponent: { name: 'cilObjectGroup' },
        attributes: {
          "Permission" : Permissions.EDIT_PRODUCTS
        }
      }
    ]
  },
  {
    name: 'Shipping',
    url: '/shipping',
    iconComponent: { name: 'cilFlightTakeoff' },
    attributes: {
      "Permission" : Permissions.MANAGE_ORDERS
    },
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
    name: 'ORDER MANAGEMENT',
    attributes: {
      "Permission" : Permissions.MANAGE_ORDERS
    },
  },
  {
    name: 'Orders',
    url: '/order',
    iconComponent: { name: 'cilListRich' },
    attributes: {
      "Permission" : Permissions.MANAGE_ORDERS
    },
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
        attributes: {
          "Permission" : Permissions.EDIT_ORDERS
        }
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
    },
    attributes: {
      "Permission" : Permissions.MANAGE_REPORTING
    }
  },
  {
    title: true,
    name: 'SALES',
    attributes: {
      "Permission" : Permissions.MANAGE_ORDERS
    },
  },
  {
    name: 'POS',
    url: '/pos',
    iconComponent: { name: 'cilCash' },
    badge: {
      color: 'info',
      text: 'V1'
    },
    attributes: {
      "Permission" : Permissions.POINT_OF_SALE
    }
  },
  {
    name: 'Online',
    url: '/online',
    iconComponent: { name: 'cilCreditCard' },
    attributes: {
      "Permission" : Permissions.EDIT_ORDERS
    },
  },
  {
    title: true,
    name: 'PROMOTIONAL MARKETING',
    attributes: {
      "Permission" : Permissions.MANAGE_PROMOTIONS
    },
  },
  {
    name: 'Coupons',
    url: '/coupon',
    iconComponent: { name: 'cilGift' },
    attributes: {
      "Permission" : Permissions.MANAGE_COUPONS
    },
  },
  {
    name: 'Offers',
    url: '/offer',
    iconComponent: { name: 'cilDollar' },
    attributes: {
      "Permission" : Permissions.EDIT_PROMOTIONS
    },
  },
  {
    name: 'Templates',
    url: '/templates',
    iconComponent: { name: 'cilNoteAdd' },
    attributes: {
      "Permission" : Permissions.MANAGE_PROMOTIONS
    },
    children: [
      {
        name: 'Email',
        url: '/templates/emailtemplate',
        iconComponent: { name: 'cilEnvelopeClosed' },
        attributes: {
          "Permission" : Permissions.EDIT_PROMOTIONS
        },
      },
      {
        name: 'Invoice',
        url: '/templates/invoicetemplate',
        iconComponent: { name: 'cilPrint' },
      }
    ]
  },
  {
    title: true,
    name: 'SUPPORT'
  },
  {
    name: 'Site Settings',
    url: '/sitesettings',
    iconComponent: { name: 'cilSettings'},
    attributes: {
      "Permission" : Permissions.ADMIN
    }
  },
  {
    name: 'Audit Logs',
    url: '/auditlogs',
    iconComponent: { name: 'cilNotes'},
    attributes: {
      "Permission" : Permissions.SUPER_USER
    }
  },
  {
    name: 'Scheduled Tasks',
    url: '/scheduledtasks',
    iconComponent: { name: 'cilAvTimer'},
    attributes: {
      "Permission" : Permissions.SUPER_USER
    },
    badge: {
      color: 'info',
      text: 'V1'
    },
  },
  {
    name: 'Help',
    url: '/help',
    iconComponent: { name: 'cilHandshake'},
  }
];
