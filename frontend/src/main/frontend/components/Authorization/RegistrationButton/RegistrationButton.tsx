
import styles from "Frontend/components/Authorization/RegistrationButton/RegistrationButton.module.css"
import { BrowserRouter as Router, Routes, Route, useNavigate } from "react-router-dom";
import React from 'react';

const RegistrationButton = () => {
  const navigate = useNavigate();

  return (
    <div className={styles.registrationDiv}>
      <button className={styles.registrationButton} onClick={() => navigate("/registration")}>
        Зарегистрироваться
      </button>
    </div>
  )
}

export default RegistrationButton;