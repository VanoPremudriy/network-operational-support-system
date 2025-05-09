import { RouterConfigurationBuilder } from '@vaadin/hilla-file-router/runtime.js';

import AuthorizationView from 'Frontend/views/AuthorizationView/AuthorizationView';
import Authorization from 'Frontend/components/Authorization/Authorization';
import Registration from 'Frontend/components/Registration/Registration';

import MainView from 'Frontend/views/MainView/MainView';
import MapView from 'Frontend/views/MapView/MapView';
import AuthGuard from 'Frontend/guards/AuthGuard';
import PublicGuard from 'Frontend/guards/PublicGuard';
import ClientView from 'Frontend/views/ClientView/ClientView';

// Объявление путей маршрутов
export enum RoutePath {
  ROOT = '/',  // Пример маршрута для корня
  AUTH = '/auth',
  LOGIN = '/auth/login',
  REGISTER = '/auth/registration',
  MAP = '/map',
  CLIENT = '/client'
}

// Конфигурация маршрутов
export const { router, routes } = new RouterConfigurationBuilder()
  .withReactRoutes([
    {
      path: RoutePath.AUTH,
      element: <PublicGuard />,
      children: [
        {
          element: <AuthorizationView />,
          children: [
            { path: RoutePath.LOGIN, element: <Authorization /> },
            { path: RoutePath.REGISTER, element: <Registration /> },
            { index: true, element: <Authorization /> },
          ],
        },
      ],
    },
    {
      path: RoutePath.ROOT,
      element: <AuthGuard />,
      children: [
        {
          element: <MainView />,
          children: [
            { path: RoutePath.MAP, element: <MapView /> },
            { path: RoutePath.CLIENT, element: <ClientView />},
            { index: true, element: <MapView /> },
          ],
        },
      ],
    },
  ])
  .build();