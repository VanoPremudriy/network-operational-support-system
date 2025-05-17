import styles from 'Frontend/components/Sidebar/Sidebar.module.css';
import React, { useState } from 'react';
import SelectButton from 'Frontend/components/Buttons/SelectButton/SelectButton';
import CustomButtonColor from 'Frontend/components/Buttons/CustomButtonColor/CustomButtonColor';
import CustomButtonNonColor from 'Frontend/components/Buttons/CustomButtonNonColor/CustomButtonNonColor';
import { useCapacityDictionary, useClientDictionary, useNodeDictionary } from 'Frontend/services/DictionaryService';

type SidebarProps = {
  selectedPointId: string | null;
  onClose: () => void;
};


const Sidebar = ({ selectedPointId, onClose }: SidebarProps) => {
  const [isCollapsed, setIsCollapsed] = useState(false);

  const toggleSidebar = () => {
    setIsCollapsed(prev => !prev);
  };

  const fetchClients = useClientDictionary();
  const fetchNodes = useNodeDictionary();
  const fetchCapacity = useCapacityDictionary()

  const handleSelectClient = (client: { id: string; label: string }) => {
    console.log('Выбран клиент:', client);
  };

  const handleSelectStartNode = (node: {id: string; label: string}) => {
    console.log('Точка начала выбрана:', node);
  }

  const handleSelectEndNode = (node: {id: string; label: string}) => {
    console.log('Точка назначения выбрана:', node);
  }

  const handleSelectCapacity = (capacity: {id: string; label: string}) => {
    console.log('Выбрана скорость:', capacity);
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
                label="Точка начала"
                fetchOptions={async (query) => {
                  const raw = await fetchNodes(query);
                  return raw.map((c) => ({ id: c.id, label: c.name }));
                }}
                onSelect={handleSelectStartNode}
              />

              <SelectButton
                label="Точка назначения"
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
                <CustomButtonColor label={"Построить"} onClick={() => {}} />
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