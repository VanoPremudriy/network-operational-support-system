import styles from 'Frontend/components/Sidebar/Sidebar.module.css';
import React, { useEffect, useState } from 'react';
import SelectButton from 'Frontend/components/Buttons/SelectButton/SelectButton';
import CustomButtonColor from 'Frontend/components/Buttons/CustomButtonColor/CustomButtonColor';
import CustomButtonNonColor from 'Frontend/components/Buttons/CustomButtonNonColor/CustomButtonNonColor';
import { useCapacityDictionary, useClientDictionary, useNodeDictionary } from 'Frontend/services/DictionaryService';
import CreateRouteRequest from 'Frontend/generated/ru/mirea/cnoss/service/route/dto/createRoute/CreateRouteRequest';
import { createRoute } from 'Frontend/services/RouteService';
import { useNotification } from 'Frontend/components/Notifications/NotificationContext';
import { getNodeById } from 'Frontend/services/NodeService';
import DetailedNode from 'Frontend/generated/ru/mirea/cnoss/service/node/dto/DetailedNode';

type SidebarProps = {
  selectedPointId: string | null;
  onClose: () => void;
  isInfo: boolean
};


const Sidebar = ({ selectedPointId, onClose, isInfo }: SidebarProps) => {
  const [isCollapsed, setIsCollapsed] = useState(false);
  const [selectedClient, setSelectedClient] = useState<{ id: string; label: string } | null>(null);
  const [startNode, setStartNode] = useState<{ id: string; label: string } | null>(null);
  const [endNode, setEndNode] = useState<{ id: string; label: string } | null>(null);
  const [capacity, setCapacity] = useState<{ id: string; label: string } | null>(null);
  const [algorithm, setAlgorithm] = useState<{ id: string; label: string } | null>(null);

  const toggleSidebar = () => {
    setIsCollapsed(prev => !prev);
  };

  const fetchClients = useClientDictionary();
  const fetchNodes = useNodeDictionary();
  const fetchCapacity = useCapacityDictionary()

  const { showNotification } = useNotification();


  const handleBuildRoute = async () => {
    if (!selectedClient || !startNode || !endNode || !capacity || !algorithm) {
      showNotification("error", "Ошибка при построении", "Заполните все поля перед построением маршрута");
      return;
    }

    const request: CreateRouteRequest = {
      clientId: selectedClient.id,
      startingPoint: startNode.id,
      destinationPoint: endNode.id,
      capacity: parseFloat(capacity.label),
      routeBuildingAlgorithm: algorithm.id
    };

    try {
      const response = await createRoute(request);

      console.log(response)

      if (response?.success) {
        showNotification(
          "success",
          "Задача на построение создана",
          "Отслеживать статус задачи можно во вкладке “Задачи”"
        );
      } else if (response?.error) {
        // Приоритетное отображение текста из infos.text, если он есть
        const errorTitle = response.error.title || "Ошибка при построении маршрута";

        // Берем первый текст из infos, если infos существует и не пустой
        let errorMessage = "Неизвестная ошибка";

        if (response.error.infos) {
          // Если infos — объект, возьмем первое значение из него
          const infosValues = Object.values(response.error.infos);
          if (infosValues.length > 0 && typeof infosValues[0] === "string") {
            errorMessage = infosValues[0];
          }
        }

        showNotification("error", errorTitle, errorMessage);
      } else {
        // Если response есть, но нет success и error
        showNotification(
          "error",
          "Ошибка при построении маршрута",
          "Неизвестная ошибка"
        );
      }
    } catch (e) {
      showNotification("error", "Ошибка сети", "Не удалось связаться с сервером. Попробуйте позже.");
    }
  };


  const handleSelectClient = (client: { id: string; label: string }) => {
    console.log('Выбран клиент:', client);
    setSelectedClient(client);
  };

  const handleSelectStartNode = (node: {id: string; label: string}) => {
    console.log('Город начала выбрана:', node);
    setStartNode(node);
  }

  const handleSelectEndNode = (node: {id: string; label: string}) => {
    console.log('Город назначения выбрана:', node);
    setEndNode(node);
  }

  const handleSelectCapacity = (capacity: {id: string; label: string}) => {
    console.log('Выбрана скорость:', capacity);
    setCapacity(capacity);
  }

  const handleSelectAlgorithm = (algo: { id: string; label: string }) => {
    console.log('Выбран алгоритм:', algo);
    setAlgorithm(algo);
  };

  const [detailedNode, setDetailedNode] = useState<DetailedNode | null>(null);
  const [expandedBaskets, setExpandedBaskets] = useState<Set<string>>(new Set());
  const [expandedBoards, setExpandedBoards] = useState<Set<string>>(new Set());
  const [expandedPorts, setExpandedPorts] = useState<Set<string>>(new Set());

  useEffect(() => {
    const fetchNode = async () => {
      if (selectedPointId) {
        const res = await getNodeById(selectedPointId);
        setDetailedNode(res.body!);
      }
    };
    fetchNode();
  }, [selectedPointId]);

  const toggleSetItem = (set: Set<string>, id: string): Set<string> => {
    const newSet = new Set(set);
    if (newSet.has(id)) {
      newSet.delete(id);
    } else {
      newSet.add(id);
    }
    return newSet;
  };

  return (
    <div className={`${styles.sidebar} ${isCollapsed ? styles.collapsed : ''}`}>
      <button className={styles.toggleButton} onClick={toggleSidebar}>
        {isCollapsed ? '⮞' : '⮜'}
      </button>

      {!isCollapsed && (
        <>
          {selectedPointId || isInfo ? (
            <>
              {detailedNode && (
                <>
                  {/* Шапка с названием и кнопкой закрытия */}
                  <div className={styles.headerFixed}>
                    <h3 className={styles.nodeName}>Узел: {detailedNode.name}</h3>
                    {!isInfo && (
                      <button className={styles.closeButton} onClick={onClose}>❌</button>
                    )}
                  </div>

                  {/* Координаты — тоже вне скролла */}
                  <div className={styles.coordinatesFixed}>
                    <p>Широта: {detailedNode.latitude?.toString()}</p>
                    <p>Долгота: {detailedNode.longitude?.toString()}</p>
                  </div>

                  {/* Вся остальная детальная информация — в скролле */}
                  <div className={styles.scrollContent}>
                    {detailedNode?.baskets?.map((basket) =>
                      basket ? (
                        <div key={basket.id ?? Math.random().toString()} className={styles.nestedBlock}>
                          <button onClick={() => setExpandedBaskets(prev => toggleSetItem(prev, basket.id!))}>
                            {expandedBaskets.has(basket.id!) ? '−' : '+'}
                          </button>
                          <strong>Корзина: {basket.name}</strong>

                          {expandedBaskets.has(basket.id!) &&
                            basket.boards?.map((board) =>
                              board ? (
                                <div key={board.id ?? Math.random().toString()} className={styles.innerBlock}>
                                  <button onClick={() => setExpandedBoards(prev => toggleSetItem(prev, board.id!))}>
                                    {expandedBoards.has(board.id!) ? '−' : '+'}
                                  </button>
                                  <strong>Плата: {board.name}</strong>

                                  {expandedBoards.has(board.id!) &&
                                    board.ports?.map((port) =>
                                      port ? (
                                        <div key={port.id ?? Math.random().toString()} className={styles.innerPort}>
                                          <button
                                            onClick={() => setExpandedPorts(prev => toggleSetItem(prev, port.id!))}>
                                            {expandedPorts.has(port.id!) ? '−' : '+'}
                                          </button>
                                          Порт: {port.portTypeName}, {port.clientName}, {port.capacity?.toString()}
                                        </div>
                                      ) : null
                                    )}
                                </div>
                              ) : null
                            )}
                        </div>
                      ) : null
                    )}
                  </div>
                </>
              )}
            </>
          ) : (
            <div className={styles.selectBlock}>
              <SelectButton
                label="Город начала"
                fetchOptions={async (query) => {
                  const raw = await fetchNodes(query);
                  return raw.map((c) => ({ id: c.id, label: c.name }));
                }}
                onSelect={handleSelectStartNode}
              />

              <SelectButton
                label="Город назначения"
                fetchOptions={async (query) => {
                  const raw = await fetchNodes(query);
                  return raw.map((c) => ({ id: c.id, label: c.name }));
                }}
                onSelect={handleSelectEndNode}
              />

              <SelectButton
                label="Скорость"
                fetchOptions={async (query) => {
                  const raw = await fetchCapacity(query);
                  return raw.map((c) => ({ id: '', label: c.capacity }));
                }}
                onSelect={handleSelectCapacity}
              />

              <SelectButton
                label="Клиент"
                fetchOptions={async (query) => {
                  const raw = await fetchClients(query);
                  return raw.map((c) => ({ id: c.id, label: c.fullName }));
                }}
                onSelect={handleSelectClient}
              />

              <SelectButton
                label="Алгоритм"
                fetchOptions={async () => [
                  { id: 'routeSearch', label: 'Поиск маршрута' },
                  { id: 'routeSearchMaxflow', label: 'Максимальный поток' }
                ]}
                onSelect={handleSelectAlgorithm}
              />

              <div className={styles.buttons}>
                <CustomButtonColor label={"Построить"} onClick={handleBuildRoute} />
                <CustomButtonNonColor label={"Отменить"} onClick={() => {
                }} />
              </div>
            </div>
          )}
        </>
      )}
    </div>

  );
};


export default Sidebar;