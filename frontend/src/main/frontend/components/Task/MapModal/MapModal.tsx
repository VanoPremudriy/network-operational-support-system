import React, { useState } from 'react';
import styles from './MapModal.module.css';
import { Map } from 'Frontend/components/Map/Map';
import { useMapData } from 'Frontend/hooks/useMapData';
import { useRouteDataByRouteId } from 'Frontend/services/RouteService';
import Sidebar from 'Frontend/components/Sidebar/Sidebar';

interface MapModalProps {
  routeId: string;
  onClose: () => void;
}

const MapModal: React.FC<MapModalProps> = ({ routeId, onClose }) => {
  const data = useMapData();
  const routeData = useRouteDataByRouteId(routeId);
  const [selectedPointId, setSelectedPointId] = useState<string | null>(null);

  if (!data || !routeData) {
    return (
      <div className={styles.modalOverlay}>
        <div className={styles.modalContent}>
          <button onClick={onClose} className={styles.closeButton}>Закрыть</button>
          <p>Загрузка карты маршрута...</p>
        </div>
      </div>
    );
  }

  return (
    <div className={styles.modalOverlay}>
      <div className={styles.modalContent}>
        <button onClick={onClose} className={styles.closeButton}>×</button>
        <Sidebar selectedPointId={selectedPointId} onClose={() => setSelectedPointId(null)} isInfo={true}/>
        <Map
          data={data}
          points={routeData.points}
          edges={routeData.edges}
          onPointClick={setSelectedPointId}
        />
      </div>
    </div>
  );
};

export default MapModal;
