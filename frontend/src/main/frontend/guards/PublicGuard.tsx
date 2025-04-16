import { Navigate, Outlet } from 'react-router-dom';
import { RoutePath } from 'Frontend/routes';

const PublicGuard = () => {
  const isAuthenticated = !!localStorage.getItem('token');
  return !isAuthenticated ? <Outlet /> : <Navigate to={RoutePath.LOGIN} replace />;
};

export default PublicGuard;