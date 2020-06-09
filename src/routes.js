/* eslint-disable react/no-multi-comp */
/* eslint-disable react/display-name */
import React, { lazy } from 'react';
import { Redirect } from 'react-router-dom';

import AuthLayout from './layouts/Auth';
import ErrorLayout from './layouts/Error';
import DashboardLayout from './layouts/Dashboard';

const routes = [
  {
    path: '/',
    exact: true,
    component: () => <Redirect to="/Person" />
  },
  {
    path: '/auth',
    component: AuthLayout,
    routes: [
      {
        path: '/auth/login',
        exact: true,
        component: lazy(() => import('views/Login'))
      },
      {
        component: () => <Redirect to="/errors/error-404" />
      }
    ]
  },
  {
    path: '/errors',
    component: ErrorLayout,
    routes: [
      {
        path: '/errors/error-401',
        exact: true,
        component: lazy(() => import('views/Error401'))
      },
      {
        path: '/errors/error-404',
        exact: true,
        component: lazy(() => import('views/Error404'))
      },
      {
        path: '/errors/error-500',
        exact: true,
        component: lazy(() => import('views/Error500'))
      },
      {
        component: () => <Redirect to="/errors/error-404" />
      }
    ]
  },
  {
    route: '*',
    component: DashboardLayout,
    routes: [
      
      {
        path: '/person',
        exact: true,
        component: lazy(() => import('views/PersonManagentList'))
      },
      {
        path: '/person/new',
        exact: true,
        component: lazy(() => import('views/PersonManagentDetails'))
      },
      {
        path: '/person/:id',
        exact: true,
        component: lazy(() => import('views/PersonManagentDetails'))
      },
      {
        path: '/document',
        exact: true,
        component: lazy(() => import('views/DocumentManagentList'))
      },
      {
        path: '/document/new',
        exact: true,
        component: lazy(() => import('views/DocumentManagentDetails'))
      },
      {
        path: '/document/:id',
        exact: true,
        component: lazy(() => import('views/DocumentManagentDetails'))
      },

      {
        path: '/server/new',
        exact: true,
        component: lazy(() => import('views/ServerManagentDetails'))
      },
      {
        path: '/server/:id',
        exact: true,
        component: lazy(() => import('views/ServerManagentDetails'))
      },
      {
        component: () => <Redirect to="/errors/error-404" />
      }
      path: '/file',
        exact: true,
        component: lazy(() => import('views/FileManagentList'))
      },
      {
        path: '/upload',
        exact: true,
        component: lazy(() => import('views/FileManagentDetails'))
      },
      {
        path: '/download',
        exact: true,
        component: lazy(() => import('views/FileManagentDetails'))
      },

    ]
  }
];

export default routes;
