import { Navigate, Outlet } from 'react-router-dom';
import { RoutePath } from 'Frontend/routes';
import { useAuth } from 'Frontend/services/AuthorizationService';

const PublicGuard = () => {
  const isAuthenticated = useAuth();

  if (isAuthenticated === null) {
    return <div>Загрузка...</div>; // или спиннер
  }

  return !isAuthenticated ? <Outlet /> : <Navigate to={RoutePath.ROOT} replace />;
};

export default PublicGuard;