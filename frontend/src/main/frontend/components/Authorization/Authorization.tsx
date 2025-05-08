import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import styles from "./Authorization.module.css"
import logo from "/public/authorization/logo.jpg";
import {getAuthToken} from "Frontend/services/AuthorizationService"
import { RoutePath } from 'Frontend/routes';
import CustomInput from 'Frontend/components/CustomInput/CustomInput';
import CustomButtonColor from 'Frontend/components/CustomButton/CustomButtonColor';
import CustomButtonNonColor from 'Frontend/components/CustomButton/CustomButtonNonColor';

const Authorization = ({ setAuthorized }: { setAuthorized?: (value: boolean) => void }) => {
  const navigate = useNavigate();
  const [errors, setErrors] = useState<{ [key: string]: string }>({});

  const [formData, setFormData] = useState({
    login: "",
    password: "",
  });

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const {name, value} = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  }

  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault();

    const { success, errors } = await getAuthToken(formData);
    if (success) {
      setAuthorized?.(true);
      navigate(RoutePath.ROOT);
    } else {
      setErrors(errors || {});
    }
  };

  return (
    <form onSubmit={handleLogin} className={styles.form}>
    <div className={styles.div}>
      <div className={styles.logo}>
        <img src={logo} alt={"Логотип"} height={300} width={300}/>
      </div>

      <div className={styles.headerDiv}>
        <h3 className={styles.header}>Войти</h3>
      </div>

      <div className={styles.inputs}>
        <CustomInput label="Логин" name="login" value={formData.login} onChange={handleChange} />
        <div className={`${styles.error} ${errors.login ? styles.visible : ''}`}>
          {errors.login || ''}
        </div>

        <CustomInput label="Пароль" name="password" value={formData.password} onChange={handleChange} type="password" />
        <div className={`${styles.error} ${errors.password ? styles.visible : ''}`}>
          {errors.password || ''}
        </div>
      </div>

      <div className={styles.buttons}>
        <CustomButtonColor label={"Войти"} onClick={() => {}}/>
        <CustomButtonNonColor label={"Зарегистрироваться"} onClick={() => navigate(RoutePath.REGISTER)}/>
      </div>

    </div>
    </form>
  );
};
export default Authorization;