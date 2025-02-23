
import styles from "Frontend/components/Authorization/RegistrationButton/RegistrationButton.module.css"
import { Link } from 'react-router-dom';
import React from 'react';

const RegistrationButton = () => {
  return (
    // <div className={styles.registrationDiv}>
    //   <Link to="">Зарегистрироваться</Link>
    // </div>

    <div className={styles.registrationDiv}>
      <button className={styles.registrationButton}>
        Зарегистрироваться
      </button>
    </div>
  )
}

export default RegistrationButton;