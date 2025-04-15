import React from "react";
import { Link, useNavigate } from 'react-router-dom';
import styles from "./Authorization.module.css"
import PasswordInput from 'Frontend/components/Authorization/PasswordInput/PasswordInput';
import RegistrationButton from 'Frontend/components/Authorization/RegistrationButton/RegistrationButton';
import logo from "/public/authorization/logo.jpg";
import {getAuthToken} from "Frontend/services/AuthorizationService"

const Authorization = ({ setAuthorized }: { setAuthorized?: (value: boolean) => void }) => {
  return (
    <div className={styles.div}>
      <div className={styles.logo}>
        <img src={logo} alt={"#"} height={300} width={300}/>
      </div>
      {header()}
      {login()}
      <PasswordInput/>
      {loginButton(setAuthorized)}
      <RegistrationButton/>
    </div>
  )
}

const header = () => {
  return (
    <div className={styles.headerDiv}>
      <h3 className={styles.header}>Войти</h3>
    </div>
  )
}

const login = () => {
  return (
    <div className={styles.loginDiv}>
      <h5>Логин</h5>
      <input type={'text'} name={'Логин'} className={styles.loginInput}/>
    </div>
  )
}

// const loginButton = (setAuthorized: (value: boolean) => void) => {
//   return (
//     <div className={styles.loginButtonDiv}>
//       <button className={styles.loginButton} onClick={async () => {
//         // const serverResponse = await HelloEndpoint.auth();
//         // if (serverResponse.token) {
//         //   localStorage.setItem("token", serverResponse.token);
//         // } else {
//         //   console.error("Ошибка: токен отсутствует!");
//         // }
//         setAuthorized(true)
//       }}>
//         Войти
//       </button>
//     </div>
//   )
// }

const loginButton = (setAuthorized?: (value: boolean) => void) => {
  const navigate = useNavigate();

  return (
    <div className={styles.loginButtonDiv}>
      <button className={styles.loginButton} onClick={async () => {
        const token = await getAuthToken();
        if (token) {
          setAuthorized?.(true);
          navigate("/");
        }
      }}>
        Войти
      </button>
    </div>
  );
};

export default Authorization;