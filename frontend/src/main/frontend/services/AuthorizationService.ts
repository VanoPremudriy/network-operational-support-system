import {AuthEndpoint} from "Frontend/generated/endpoints"
import { useEffect, useState } from 'react';

export const getAuthToken = async (formData: {
  login: string;
  password: string;
}): Promise<{ success: boolean; errors?: Record<string, string> }> => {
  const response = await AuthEndpoint.auth(formData);

  if (response.success) {
    return { success: true };
  } else {
    const infos = response.error?.infos || {};
    const filteredInfos: Record<string, string> = {};

    for (const key in infos) {
      const value = infos[key];
      if (typeof value === 'string') {
        filteredInfos[key] = value;
      }
    }

    return { success: false, errors: filteredInfos };
  }
};


export const registration = async (formData: {
  login: string,
  password: string,
  firstName: string,
  lastName: string
}):Promise<{ success: boolean; errors?: Record<string, string> }> => {
  const response = await AuthEndpoint.register(formData);

  if (response.success) {
    return { success: true };
  } else {
    const infos = response.error?.infos || {};
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

export const useAuth = () => {
  const [isAuthenticated, setIsAuthenticated] = useState<boolean | null>(null); // null — значит "ещё не знаем"

  useEffect(() => {
    AuthEndpoint.isAuthenticated()
      .then(setIsAuthenticated)
      .catch(() => setIsAuthenticated(false));
  }, []);

  return isAuthenticated;
};