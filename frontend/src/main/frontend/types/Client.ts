export interface Client {
  id?: string;
  login?: string;
  firstName?: string;
  lastName?: string;
  email?: string;
  middleName?: string;
}

export interface Error {
  code: string;
  title: string;
  infos: Record<string, string>;
}

export interface ClientResponse {
  success: boolean;
  error?: Error;
  numberOfPages: number;
  clients: Client[];
}

export interface UseClientsProps {
  page: number;
  refreshTrigger?: number;
}

export interface ClientGetRequest {
  token: string;
  currentPage: number;
}