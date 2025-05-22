import styles from 'Frontend/components/Sidebar/Sidebar.module.css';
import React, { useState } from 'react';
import SelectButton from 'Frontend/components/Buttons/SelectButton/SelectButton';
import CustomButtonColor from 'Frontend/components/Buttons/CustomButtonColor/CustomButtonColor';
import CustomButtonNonColor from 'Frontend/components/Buttons/CustomButtonNonColor/CustomButtonNonColor';
import { useCapacityDictionary, useClientDictionary, useNodeDictionary } from 'Frontend/services/DictionaryService';
import CreateRouteRequest from 'Frontend/generated/ru/mirea/cnoss/service/route/dto/createRoute/CreateRouteRequest';
import { createRoute } from 'Frontend/services/RouteService';
import { useNotification } from 'Frontend/components/Notifications/NotificationContext';

type SidebarProps = {
  selectedPointId: string | null;
  onClose: () => void;
};


const Sidebar = ({ selectedPointId, onClose }: SidebarProps) => {
  const [isCollapsed, setIsCollapsed] = useState(false);
  const [selectedClient, setSelectedClient] = useState<{ id: string; label: string } | null>(null);
  const [startNode, setStartNode] = useState<{ id: string; label: string } | null>(null);
  const [endNode, setEndNode] = useState<{ id: string; label: string } | null>(null);
  const [capacity, setCapacity] = useState<{ id: string; label: string } | null>(null);

  const toggleSidebar = () => {
    setIsCollapsed(prev => !prev);
  };

  const fetchClients = useClientDictionary();
  const fetchNodes = useNodeDictionary();
  const fetchCapacity = useCapacityDictionary()

  const { showNotification } = useNotification();


  const handleBuildRoute = async () => {
    if (!selectedClient || !startNode || !endNode || !capacity) {
      showNotification("error", "Ошибка при построении", "Заполните все поля перед построением маршрута");
      return;
    }

    const request: CreateRouteRequest = {
      clientId: selectedClient.id,
      startingPoint: startNode.id,
      destinationPoint: endNode.id,
      capacity: parseFloat(capacity.label),
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

  return (
    <div className={`${styles.sidebar} ${isCollapsed ? styles.collapsed : ''}`}>
      <button className={styles.toggleButton} onClick={toggleSidebar}>
        {isCollapsed ? '⮞' : '⮜'}
      </button>
      {!isCollapsed && (
        <div className={styles.selectBlock}>
          {selectedPointId ? (
            <>
              <div className={styles.detailsHeader}>
                <button onClick={onClose}>❌</button>
                <span>Детали точки: {selectedPointId}</span>

              </div>
              {/* Здесь будет потом fetch по selectedPointId */}
              <div>Здесь будет информация о точке</div>
            </>
          ) : (
            <>
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

              <div className={styles.buttons}>
                <CustomButtonColor label={"Построить"} onClick={handleBuildRoute} />
                <CustomButtonNonColor label={"Отменить"} onClick={() => {}} />
              </div>
            </>
          )}
        </div>
      )}
    </div>
  );
};



export default Sidebar;