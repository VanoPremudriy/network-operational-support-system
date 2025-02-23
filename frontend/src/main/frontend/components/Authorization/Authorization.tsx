import React from "react";
import { Link } from 'react-router-dom';
import styles from "./Authorization.module.css"
import PasswordInput from 'Frontend/components/Authorization/PasswordInput/PasswordInput';
import RegistrationButton from 'Frontend/components/Authorization/RegistrationButton/RegistrationButton';
import logo from "/public/authorization/logo.jpg";

const Authorization = () => {
  return (
    <div className={styles.div}>
      <div className={styles.logo}>
        <img src={logo} alt={"#"} height={300} width={300}/>
      </div>
      {header()}
      {login()}
      <PasswordInput/>
      {loginButton()}
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

const loginButton = () => {
  return (
    <div className={styles.loginButtonDiv}>
      <button className={styles.loginButton}>
        Войти
      </button>
    </div>
  )
}

export default Authorization;