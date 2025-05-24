import React from 'react';
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
        <Sidebar selectedPointId={null} onClose={() => {}} isInfo={true}/>
        <Map
          data={data}
          points={routeData.points}
          edges={routeData.edges}
          onPointClick={() => {}}
        />
      </div>
    </div>
  );
};

export default MapModal;
