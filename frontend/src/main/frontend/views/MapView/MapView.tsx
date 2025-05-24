import Sidebar from 'Frontend/components/Sidebar/Sidebar';
import styles from 'Frontend/views/MapView/MapView.module.css'
import RouteMap from 'Frontend/components/Map/RouteMap';
import { useState } from 'react';

const MapView = () => {
  const [selectedPointId, setSelectedPointId] = useState<string | null>(null);

  return (
    <div className={styles.body}>
        <Sidebar selectedPointId={selectedPointId} onClose={() => setSelectedPointId(null)} isInfo={false}/>
        <RouteMap onPointClick={setSelectedPointId}/>
    </div>
  );
}

export default MapView;