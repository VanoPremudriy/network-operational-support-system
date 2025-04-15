import Sidebar from 'Frontend/components/Sidebar/Sidebar';
import styles from 'Frontend/views/MapView/MapView.module.css'
import RouteMap from 'Frontend/components/Map/RouteMap';

const MapView = () => {
  return (
    <div className={styles.body}>
        <Sidebar/>
        <RouteMap/>
    </div>
  );
}

export default MapView;