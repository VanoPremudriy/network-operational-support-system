export interface Task {
  id?: string,
  activeFlag?: boolean,
  createdTime?: string,
  clientName?: string,
  status?: string
  clientId?: string,
  taskType?: string,
  resolvedData?: string,
  capacity?: number,
  startingPoint?: string,
  destinationPoint?: string
  routeBuildingAlgorithm?: string
}

export interface UseTasksProps {
  page: number;
}

export interface TaskGetRequest {
  currentPage: number;
}