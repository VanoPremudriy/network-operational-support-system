
import styles from "Frontend/components/Authorization/RegistrationButton/RegistrationButton.module.css"
import {useNavigate } from "react-router-dom";
import React from 'react';
import { RoutePath } from 'Frontend/routes';

const RegistrationButton = () => {
  const navigate = useNavigate();

  return (
    <div className={styles.registrationDiv}>
      <button className={styles.registrationButton} onClick={() => navigate(RoutePath.REGISTER)}>
        Зарегистрироваться
      </button>
    </div>
  )
}

export default RegistrationButton;