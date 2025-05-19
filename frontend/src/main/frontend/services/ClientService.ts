import { useState, useEffect } from 'react';
import { ClientEndpoint } from 'Frontend/generated/endpoints';
import ClientViewDto from 'Frontend/generated/ru/mirea/cnoss/service/client/dto/ClientViewDto';
import { Client, ClientGetRequest, UseClientsProps } from 'Frontend/types/Client';
import ClientCreateRequest from 'Frontend/generated/ru/mirea/cnoss/service/client/dto/request/ClientCreateRequest';
import ClientUpdateRequest from 'Frontend/generated/ru/mirea/cnoss/service/client/dto/request/ClientUpdateRequest';

const useClients = ({ page, refreshTrigger  }: UseClientsProps) => {
  const [clients, setClients] = useState<Client[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const [numberOfPages, setNumberOfPages] = useState<number>(0);

  const token = localStorage.getItem("token");

  useEffect(() => {

    console.log(token)

    const fetchClients = async () => {
      if (!token) {
        setError('Нет авторизационного токена или ID пользователя');
        setLoading(false);
        return;
      }

      try {
        const request: ClientGetRequest = {
          token: token,
          currentPage: page,
        };

        const data = await ClientEndpoint.getClients(request);


        if (!data.success) {
          setError(data.error?.title || 'Неизвестная ошибка');
          return;
        }


        setClients((data.clients ?? []).filter((c): c is ClientViewDto => c !== undefined));
        setNumberOfPages(data.numberOfPages ?? 0);
        setLoading(false);
      } catch (err) {
        setError('Ошибка при загрузке данных');
        setLoading(false);
      }
    };

    fetchClients();
  }, [page, token, refreshTrigger]);

  return { clients, loading, error, numberOfPages };
};

export default useClients;

export const deleteClient = async (clientId: string): Promise<{ success: boolean; error?: any }> => {
  const token = localStorage.getItem("token");

  if (!token) {
    return { success: false, error: { title: 'Нет токена' } };
  }

  try {
    const res = await ClientEndpoint.deleteClient({ token, clientId });
    return { success: res.success, error: res.error };
  } catch (error) {
    return { success: false, error: { title: 'Ошибка удаления клиента' } };
  }
};


export async function createClient(
  req: ClientCreateRequest
): Promise<{ success: boolean; errors?: Record<string, string> }> {
  const res = await ClientEndpoint.createClient(req);

  if (res.success) {
    return { success: true }
  } else {
    const infos = res.error?.infos || {};
    const filteredInfos: Record<string, string> = {};

    for (const key in infos) {
      const value = infos[key];
      if (typeof value === 'string') {
        filteredInfos[key] = value;
      }
    }
    return { success: false, errors: filteredInfos };
  }
}

export async function updateClient(
  req: ClientUpdateRequest
): Promise<{ success: boolean; errors?: Record<string, string> }> {
  const res = await ClientEndpoint.updateClient(req);

  if (res.success) {
    return { success: true }
  } else {
    const infos = res.error?.infos || {};
    const filteredInfos: Record<string, string> = {};

    for (const key in infos) {
      const value = infos[key];
      if (typeof value === 'string') {
        filteredInfos[key] = value;
      }
    }
    return { success: false, errors: filteredInfos };
  }
}