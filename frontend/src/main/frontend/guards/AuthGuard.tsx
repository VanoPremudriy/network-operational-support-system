import { Navigate, Outlet } from 'react-router-dom';
import { RoutePath } from 'Frontend/routes';
import { useAuth } from 'Frontend/services/AuthorizationService';

const AuthGuard = () => {
  const isAuthenticated = useAuth();

  if (isAuthenticated === null) {
    return <div>Загрузка...</div>; // или спиннер
  }

  return isAuthenticated ? <Outlet /> : <Navigate to={RoutePath.LOGIN} replace />;
};

export default AuthGuard;