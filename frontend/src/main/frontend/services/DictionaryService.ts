import { useCallback } from 'react';
import ClientDictionaryResponse  from 'Frontend/generated/ru/mirea/cnoss/service/dictionary/dto/client/ClientDictionaryResponse';
import {DictionaryEndpoint} from "Frontend/generated/endpoints";
import ClientDictionaryDto from 'Frontend/generated/ru/mirea/cnoss/service/dictionary/dto/client/ClientDictionaryDto';
import NodeDictionaryDto from 'Frontend/generated/ru/mirea/cnoss/service/dictionary/dto/node/NodeDictionaryDto';
import NodeDictionaryResponse from 'Frontend/generated/ru/mirea/cnoss/service/dictionary/dto/node/NodeDictionaryResponse';
import CapacityDictionaryViewResponse from 'Frontend/generated/ru/mirea/cnoss/service/dictionary/dto/capacity/CapacityDictionaryViewResponse';
import CapacityDictionaryViewDto from 'Frontend/generated/ru/mirea/cnoss/service/dictionary/dto/capacity/CapacityDictionaryViewDto';

export const useClientDictionary = () => {

  return useCallback(async (query: string): Promise<{ fullName: string; id: string }[]> => {
    try {
      const res: ClientDictionaryResponse = await DictionaryEndpoint.getClientDictionary(query)

      if (!res.success) {
        throw new Error(res.error?.title || 'Ошибка загрузки словаря');
      }

      return (res.body?.clients || [])
        .filter((c): c is ClientDictionaryDto => !!c && !!c.id && !!c.fullName)
        .map((client) => ({
          id: client.id!,
          fullName: client.fullName!
        }));
    } catch (err) {
      console.error('Ошибка при загрузке клиентов:', err);
      throw err;
    }
  }, []);
};

export const useNodeDictionary = () => {

  return useCallback(async (query: string): Promise<{ name: string; id: string }[]> => {
    try {
      const res: NodeDictionaryResponse = await DictionaryEndpoint.getNodeDictionary(query)

      if (!res.success) {
        throw new Error(res.error?.title || 'Ошибка загрузки словаря');
      }

      return (res.body?.nodes || [])
        .filter((c): c is NodeDictionaryDto => !!c && !!c.id && !!c.name)
        .map((node) => ({
          id: node.id!,
          name: node.name!
        }));
    } catch (err) {
      console.error('Ошибка при загрузке узлов:', err);
      throw err;
    }
  }, []);
};

export const useCapacityDictionary = () => {

  return useCallback(async (query: string): Promise<{ capacity: string }[]> => {
    try {
      const res: CapacityDictionaryViewResponse = await DictionaryEndpoint.getCapacityDictionary(query)

      if (!res.success) {
        throw new Error(res.error?.title || 'Ошибка загрузки словаря');
      }

      return (res.body?.portTypes || [])
        .filter((c): c is CapacityDictionaryViewDto => !!c && !!c.capacity)
        .map((portTypes) => ({
          capacity: portTypes.capacity!,
        }));
    } catch (err) {
      console.error('Ошибка при загрузке узлов:', err);
      throw err;
    }
  }, []);
};