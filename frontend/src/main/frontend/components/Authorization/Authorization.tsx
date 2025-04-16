import React from "react";
import { Link, useNavigate } from 'react-router-dom';
import styles from "./Authorization.module.css"
import PasswordInput from 'Frontend/components/Authorization/PasswordInput/PasswordInput';
import RegistrationButton from 'Frontend/components/Authorization/RegistrationButton/RegistrationButton';
import logo from "/public/authorization/logo.jpg";
import {getAuthToken} from "Frontend/services/AuthorizationService"
import { RoutePath } from 'Frontend/routes';

const Authorization = ({ setAuthorized }: { setAuthorized?: (value: boolean) => void }) => {
  const navigate = useNavigate();

  const handleLogin = async () => {
    const token = await getAuthToken();
    if (token) {
      setAuthorized?.(true);
      navigate(RoutePath.ROOT);
    }
  };

  return (
    <div className={styles.div}>
      <div className={styles.logo}>
        <img src={logo} alt={"Логотип"} height={300} width={300}/>
      </div>

      <div className={styles.headerDiv}>
        <h3 className={styles.header}>Войти</h3>
      </div>

      <div className={styles.loginDiv}>
        <h5>Логин</h5>
        <input type={'text'} name={'Логин'} className={styles.loginInput}/>
      </div>

      <PasswordInput/>

      <div className={styles.loginButtonDiv}>
        <button className={styles.loginButton} onClick={handleLogin}>
          Войти
        </button>
      </div>

      <RegistrationButton/>
    </div>
  );
};
export default Authorization;