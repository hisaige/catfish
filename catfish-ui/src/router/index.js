import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    noCache: true                if set true, the page will no be cached(default is false)
    affix: true                  if set true, the tag will affix in the tags-view
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/redirect',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/redirect/:path(.*)',
        component: () => import('@/views/redirect/index')
      }
    ]
  },
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },
  {
    path: '/auth-redirect',
    component: () => import('@/views/login/auth-redirect'),
    hidden: true
  },
  {
    path: '/404',
    component: () => import('@/views/error-page/404'),
    hidden: true
  },
  {
    path: '/401',
    component: () => import('@/views/error-page/401'),
    hidden: true
  },
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [
      {
        path: 'dashboard',
        component: () => import('@/views/dashboard/index'),
        name: 'Dashboard',
        meta: { title: '仪表盘', icon: 'dashboard', affix: true }
      }
    ]
  }
]

/**
 * asyncRoutes
 * the routes that need to be dynamically loaded based on user roles
 */
export const asyncRoutes = [
  {
    path: '/user',
    component: Layout,
    name: 'luser',
    meta: {
      title: '用户管理',
      icon: 'peoples',
      roles: ['admin'] // you can set roles in root nav
    },
    children: [
      {
        path: 'index',
        component: () => import('@/views/user/index'),
        name: 'index',
        meta: { title: '用户管理', icon: 'list', noCache: true }
      },
      {
        path: 'online',
        component: () => import('@/views/user/online'),
        name: 'online',
        meta: { title: '在线用户', icon: 'list', noCache: true }
      },
      {
        path: 'save',
        name: '新增用户',
        component: () => import('@/views/user/save'),
        meta: { title: '新增用户', icon: 'tree' },
        hidden: true
      }
    ]
  },
  {
    path: '/role',
    component: Layout,
    name: 'role',
    children: [
      {
        path: 'index',
        component: () => import('@/views/role/index'),
        name: '角色管理',
        meta: { title: '角色管理', icon: 'list', noCache: true }
      }
    ]
  },
  {
    path: '/permission',
    component: Layout,
    redirect: '/permission/page',
    alwaysShow: true, // will always show the root menu
    name: 'Permission',
    meta: {
      title: '权限管理',
      icon: 'lock',
      roles: ['admin', 'editor'] // you can set roles in root nav
    },
    children: [
      {
        path: 'list',
        component: () => import('@/views/permission/list'),
        name: 'ListPermission',
        meta: {
          title: '权限管理',
          roles: ['admin'] // or you can only set roles in sub nav
        }
      },
      {
        path: 'page',
        component: () => import('@/views/permission/page'),
        name: 'PagePermission',
        meta: {
          title: 'Page Permission',
          roles: ['admin'] // or you can only set roles in sub nav
        }
      },
      {
        path: 'directive',
        component: () => import('@/views/permission/directive'),
        name: 'DirectivePermission',
        meta: {
          title: 'Directive Permission'
          // if do not set roles, means: this page does not require permission
        }
      },
      {
        path: 'role',
        component: () => import('@/views/permission/role'),
        name: 'RolePermission',
        meta: {
          title: 'Role Permission',
          roles: ['admin']
        }
      }
    ]
  },
  // {
  //   path: '/rogTree',
  //   component: Layout,
  //   name: 'rogTree',
  //   children: [
  //     {
  //       path: 'index',
  //       component: () => import('@/views/org-tree/index'),
  //       name: '角色树',
  //       meta: { title: '组织树管理', icon: 'list', noCache: true }
  //     }
  //   ]
  // },
  {
    path: '/tools',
    component: Layout,
    alwaysShow: true, // will always show the root menu
    name: 'Tools',
    meta: {
      title: '常用工具',
      icon: 'el-icon-s-tools',
      roles: ['admin', 'editor'] // you can set roles in root nav
    },
    children: [
      {
        path: 'json',
        component: () => import('@/views/tools/json'),
        name: 'JSON',
        meta: {
          title: 'JSON格式化',
          roles: ['admin', 'editor'] // or you can only set roles in sub nav
        }
      },
      {
        path: 'cron',
        component: () => import('@/views/tools/cron'),
        name: 'CRON',
        meta: {
          title: 'cron表达式'
          // if do not set roles, means: this page does not require permission
        }
      },
      {
        path: 'codeGenerator',
        component: () => import('@/views/tools/codeGenerator'),
        name: 'CodeGenerator',
        meta: {
          title: '代码生成',
          roles: ['admin']
        }
      },
      {
        path: 'database',
        component: () => import('@/views/tools/database'),
        name: 'database',
        meta: {
          title: '数据库管理',
          roles: ['admin']
        }
      }
    ]
  },
  {
    path: '/cache',
    component: Layout,
    name: 'cache',
    meta: {
      title: '缓存管理',
      icon: 'list',
      roles: ['admin', 'editor'] // you can set roles in root nav
    },
    children: [
      {
        path: 'redis',
        component: () => import('@/views/cache/redis'),
        name: 'redis',
        meta: { title: 'Redis缓存', icon: 'list', noCache: true }
      },
      {
        path: 'locale',
        component: () => import('@/views/cache/locale'),
        name: 'locale',
        meta: { title: '本地缓存', icon: 'list', noCache: true }
      }
    ]
  },
  {
    path: '/logs',
    component: Layout,
    name: 'logs',
    children: [
      {
        path: 'index',
        component: () => import('@/views/logs/index'),
        name: '日志管理',
        meta: { title: '日志管理', icon: 'list', noCache: true }
      }
    ]
  },
  // {
  //   path: '/error',
  //   component: Layout,
  //   redirect: 'noRedirect',
  //   name: 'ErrorPages',
  //   meta: {
  //     title: 'Error Pages',
  //     icon: '404'
  //   },
  //   children: [
  //     {
  //       path: '401',
  //       component: () => import('@/views/error-page/401'),
  //       name: 'Page401',
  //       meta: { title: '401', noCache: true }
  //     },
  //     {
  //       path: '404',
  //       component: () => import('@/views/error-page/404'),
  //       name: 'Page404',
  //       meta: { title: '404', noCache: true }
  //     }
  //   ]
  // },
  // {
  //   path: '/theme',
  //   component: Layout,
  //   children: [
  //     {
  //       path: 'index',
  //       component: () => import('@/views/theme/index'),
  //       name: 'Theme',
  //       meta: { title: 'Theme', icon: 'theme' }
  //     }
  //   ]
  // },
  {
    path: '/swagger',
    component: Layout,
    name: 'swagger',
    meta: {
      title: 'API管理',
      icon: 'list',
      roles: ['admin'] // you can set roles in root nav
    },
    children: [
      {
        path: 'ums',
        component: () => import('@/views/swagger/ums'),
        name: 'ums',
        meta: { title: '用户管理', icon: 'list', noCache: true }
      },
      {
        path: 'file',
        component: () => import('@/views/swagger/file'),
        name: 'file',
        meta: { title: '文件管理', icon: 'list', noCache: true }
      }
    ]
  },
  {
    path: 'external-link',
    component: Layout,
    children: [
      {
        path: 'https://github.com/PanJiaChen/vue-element-admin',
        meta: { title: '外链地址', icon: 'link' }
      }
    ]
  },

  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
