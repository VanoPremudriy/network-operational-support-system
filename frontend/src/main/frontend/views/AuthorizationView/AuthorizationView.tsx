import Authorization from 'Frontend/components/Authorization/Authorization';
import styles from './AuthorizationView.module.css';
import Registration from 'Frontend/components/Registration/Registration';
import { Route, Routes } from 'react-router-dom';

interface AuthorizationViewProps {
  setAuthorized?: (value: boolean) => void;
}

const AuthorizationView = ({ setAuthorized }: AuthorizationViewProps) => {
  return (
    <div className={styles.body}>
      <div className={styles.div}>
        <Routes>
          <Route path="/" element={<Authorization setAuthorized={setAuthorized} />} />
          <Route path="/registration" element={<Registration />} />
        </Routes>
      </div>
    </div>
  );
};
export default AuthorizationView;