import { createRouter, createWebHashHistory } from 'vue-router';

const router = createRouter({
  history: createWebHashHistory(),
  routes: [
    {
      path: '/',
      component: () => import('../components/pages/Index.vue'),
    },
    {
      path: '/memberManagements',
      component: () => import('../components/ui/MemberManagementGrid.vue'),
    },
    {
      path: '/pointViews',
      component: () => import('../components/PointViewView.vue'),
    },
    {
      path: '/fetchSubscriberLists',
      component: () => import('../components/FetchSubscriberListView.vue'),
    },
    {
      path: '/authorStatistics',
      component: () => import('../components/AuthorStatisticsView.vue'),
    },
    {
      path: '/books',
      component: () => import('../components/ui/BookGrid.vue'),
    },
    {
      path: '/manuscripts',
      component: () => import('../components/ui/ManuscriptGrid.vue'),
    },
    {
      path: '/points',
      component: () => import('../components/ui/PointGrid.vue'),
    },
    {
      path: '/subscribeManagements',
      component: () => import('../components/ui/SubscribeManagementGrid.vue'),
    },
    {
      path: '/readingManagements',
      component: () => import('../components/ui/ReadingManagementGrid.vue'),
    },
    {
      path: '/pointRequestManagements',
      component: () => import('../components/ui/PointRequestManagementGrid.vue'),
    },
    {
      path: '/bookPurchaseManagements',
      component: () => import('../components/ui/BookPurchaseManagementGrid.vue'),
    },
    {
      path: '/bookPublications',
      component: () => import('../components/ui/BookPublicationGrid.vue'),
    },
    {
      path: '/platformManagements',
      component: () => import('../components/ui/PlatformManagementGrid.vue'),
    },
    {
      path: '/authorRegistrations',
      component: () => import('../components/ui/AuthorRegistrationGrid.vue'),
    },
  ],
})

export default router;
