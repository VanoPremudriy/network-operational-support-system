import Header from 'Frontend/components/Header/Header';
import Sidebar from 'Frontend/components/Sidebar/Sidebar';
import styles from 'Frontend/views/MainView/MainView.module.css'
import MapView from 'Frontend/views/MapView/MapView';
import { Outlet } from 'react-router-dom';
import { NotificationProvider } from 'Frontend/components/Notifications/NotificationContext';

const MainView = () => {
  return (
    <NotificationProvider>
      <div className={styles.body}>
        <Header />
        <div className={styles.mainBody}>
          <Outlet />
        </div>
      </div>
    </NotificationProvider>
  );
};

export default MainView;