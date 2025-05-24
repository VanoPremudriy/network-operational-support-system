export interface TaskRoutesProps {
  page: number;
  taskId: string;
}

export interface TaskRoutesRequest {
  token: string;
  taskId: string;
  currentPage: number;
}

export interface Route {
  id: string;
  price: string;
  distance: number;
  shifts: number;
  startingPoint: string;
  destinationPoint: string;
  capacity: string;
}

export interface TaskRouteResponse {
  success: boolean;
  routes: Route[];
  error?: {
    title: string;
    message?: string;
  };
}