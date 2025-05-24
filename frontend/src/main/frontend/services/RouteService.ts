import { useEffect, useState } from "react";
import { RouteEndpoint } from "Frontend/generated/endpoints";
import CreateRouteRequest from 'Frontend/generated/ru/mirea/cnoss/service/route/dto/createRoute/CreateRouteRequest';
import CreateRouteResponse from 'Frontend/generated/ru/mirea/cnoss/service/route/dto/createRoute/CreateRouteResponse';
import { TaskRoutesProps } from 'Frontend/types/Route';
import { Task } from 'Frontend/types/Task';
import TaskRoute from 'Frontend/generated/ru/mirea/cnoss/service/route/dto/taskroute/TaskRoute';
import TaskRouteResponse from 'Frontend/generated/ru/mirea/cnoss/service/route/dto/taskroute/TaskRouteResponse';
import { useNotification } from 'Frontend/components/Notifications/NotificationContext';
import BaseResponse from 'Frontend/generated/ru/mirea/cnoss/service/BaseResponse';


interface Point {
  id: string;
  name: string;
  coordinates: [number, number];
  equipmentAmount: number;
}

interface Edge {
  source: string;
  target: string;
}

const token = localStorage.getItem("token");

export const useRouteData = () => {
  const [data, setData] = useState<{ points: Point[]; edges: Edge[] } | null>(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const route = await RouteEndpoint.getRoute(token || '');

        const points = Array.from(route?.nodes ?? [])
          .filter((node): node is Point => node !== undefined)
          .map((node) => ({
            id: node.id,
            name: node.name,
            coordinates: [
              Number(node.coordinates[0]),
              Number(node.coordinates[1]),
            ] as [number, number],
            equipmentAmount: node.equipmentAmount
          }));

        const edges = Array.from(route?.edges ?? [])
          .filter((edge): edge is Edge => edge !== undefined)
          .map((edge) => ({
            source: edge.source,
            target: edge.target,
          }));

        setData({ points, edges });
      } catch (error) {
        console.error("Ошибка при загрузке маршрута", error);
      }
    };

    fetchData();
  }, []);

  return data;
};


export const createRoute = async (request: CreateRouteRequest): Promise<CreateRouteResponse | null> => {
  const token = localStorage.getItem('token');
  if (!token) {
    console.error("Токен не найден в localStorage");
    return null;
  }

  try {
    const response = await RouteEndpoint.createRoute(token, request);
    return response;
  } catch (error) {
    console.error("Ошибка при создании маршрута:", error);
    return null;
  }
};

export const getTaskRoutes = ({page, taskId}: TaskRoutesProps) => {
  const [routes, setRoutes] = useState<TaskRoute[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const [numberOfPages, setNumberOfPages] = useState<number>(0);


  const token = localStorage.getItem("token");

  useEffect(() => {
    const fetchRoutes = async () => {
      if (!token) {
        setError("Нет авторизационного токена");
        setLoading(false);
        return;
      }

      try {
        const request = {
          token,
          taskId,
          pageNumber: page,
        };

        const response: TaskRouteResponse = await RouteEndpoint.getTaskRoutes(request);

        if (!response.success) {
          setError(response.error?.title || "Неизвестная ошибка");
          setLoading(false);
          return;
        }

        setRoutes((response.routes ?? []).filter((r): r is TaskRoute => r !== undefined));
        setLoading(false);
        setNumberOfPages(response.numberOfPages ?? 0);
      } catch (err) {
        setError("Ошибка при получении маршрутов");
        setLoading(false);
      }
    };

    fetchRoutes();
  }, [taskId, page, token]);

  return { routes, loading, error, numberOfPages};
}


export const useRouteDataByRouteId = (id: string) => {
  const [data, setData] = useState<{ points: Point[]; edges: Edge[] } | null>(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const route = await RouteEndpoint.getRouteById(token || '', id);

        const points = Array.from(route?.nodes ?? [])
          .filter((node): node is Point => node !== undefined)
          .map((node) => ({
            id: node.id,
            name: node.name,
            coordinates: [
              Number(node.coordinates[0]),
              Number(node.coordinates[1]),
            ] as [number, number],
            equipmentAmount: node.equipmentAmount
          }));

        const edges = Array.from(route?.edges ?? [])
          .filter((edge): edge is Edge => edge !== undefined)
          .map((edge) => ({
            source: edge.source,
            target: edge.target,
          }));

        setData({ points, edges });
      } catch (error) {
        console.error("Ошибка при загрузке маршрута", error);
      }
    };

    fetchData();
  }, []);

  return data;
};

export const useApplyRoute = () => {
  return async (routeId: string): Promise<BaseResponse> => {
    if (!token) {
      throw new Error('Пользователь не авторизован');
    }
    return await RouteEndpoint.applyRoute(token, routeId);
  };
};
